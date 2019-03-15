package org.nico.yasso.pipeline.impl;

import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.utils.CommandUtils;
import org.nico.yasso.utils.FileUtils;

public class GitClonePipeline extends AbstractPipeline{

    @Override
    public void pipeline(YassoJob job) {
        String gitUrl = job.getGit().getUrl();
        String gitName = job.getGit().getName();
        String workspace = job.getWorkspace();
        
        if(! FileUtils.containsFile(workspace, gitName)) {
            CommandUtils.execute("git clone " + gitUrl, workspace);
        }
        
        if(FileUtils.containsFile(workspace, gitName)) {
            then(job);   
        }
    }

}
