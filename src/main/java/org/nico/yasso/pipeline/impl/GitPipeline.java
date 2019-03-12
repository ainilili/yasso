package org.nico.yasso.pipeline.impl;

import org.nico.yasso.Yasso;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.pipeline.jobs.YassoJob;
import org.nico.yasso.utils.CommandUtils;
import org.nico.yasso.utils.CommandUtils.Result;
import org.nico.yasso.utils.FileUtils;
import org.nico.yasso.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitPipeline extends AbstractPipeline{

    private final static Logger LOGGER = LoggerFactory.getLogger(GitPipeline.class);
    
    @Override
    public void pipeline(YassoJob job) {
        
        String gitUrl = job.getGitUrl();
        if(StringUtils.isNotBlank(job.getGitUser())
                && StringUtils.isNotBlank(job.getGitPwd())) {
            String sign = job.getGitUser() + ":" + job.getGitPwd() + "@";
            
            int httpFlag = gitUrl.indexOf("://");
            httpFlag += 3;
            gitUrl = gitUrl.substring(0, httpFlag) + sign + gitUrl.substring(httpFlag);
        }
        
        String gitName = gitUrl.substring(gitUrl.lastIndexOf("/") + 1, gitUrl.lastIndexOf("."));
        String workspace = Yasso.getYasso().getWorkspace();
        
        if(! FileUtils.containsFile(workspace, gitName)) {
            Result result = CommandUtils.execute("git clone " + gitUrl, Yasso.getYasso().getWorkspace());
            LOGGER.info("Git clone：{}", result);
        }
        
        Result result = CommandUtils.execute("git pull", Yasso.getYasso().getWorkspace() + "\\" + gitName);
        LOGGER.info("Git pull：{}", result);
        
        if(result.getSuccessMsg().contains("Already up to date")) {
            LOGGER.info("Already up to date");
        }else {
            
        }
    }

}
