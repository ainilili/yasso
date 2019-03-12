package org.nico.yasso.pipeline.impl;

import org.nico.yasso.Yasso;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.pipeline.jobs.YassoJob;
import org.nico.yasso.utils.CommandUtils;
import org.nico.yasso.utils.CommandUtils.Result;
import org.nico.yasso.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitPipeline extends AbstractPipeline{

    private final static Logger LOGGER = LoggerFactory.getLogger(GitPipeline.class);
    
    @Override
    public void pipeline(YassoJob job) {
        
        String gitUrl = job.getGitUrl();
        String gitName = job.getProjectName();
        String workspace = Yasso.getYasso().getWorkspace();
        
        if(! FileUtils.containsFile(workspace, gitName)) {
            CommandUtils.execute("git clone " + gitUrl, workspace);
        }
        
        Result result = CommandUtils.execute("git pull", workspace + "\\" + gitName);
        
        if(! result.getSuccessMsg().contains("Already up to date")) {
            then(job);
        }
    }

}
