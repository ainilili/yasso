package org.nico.yasso.pipeline.impl;

import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.utils.CommandUtils;
import org.nico.yasso.utils.CommandUtils.Result;
import org.nico.yasso.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitPipeline extends AbstractPipeline{

    private final static Logger LOGGER = LoggerFactory.getLogger(GitPipeline.class);
    
    @Override
    public void pipeline(YassoJob job) {
        
        String gitUrl = job.getGit().getUrl();
        String gitName = job.getGit().getName();
        String workspace = job.getWorkspace();
        String jobspace = job.getJobspace();
        
        if(! FileUtils.containsFile(workspace, gitName)) {
            CommandUtils.execute("git clone " + gitUrl, workspace);
            then(job);
        }else {
            Result result = CommandUtils.execute("git pull", jobspace);
            if(! result.getSuccessMsg().startsWith("Already")) {
                then(job);
            }
        }
    }

}
