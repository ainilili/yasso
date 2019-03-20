package org.nico.yasso.pipeline.impl;

import org.nico.yasso.consts.BuildState;
import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.utils.CommandUtils;
import org.nico.yasso.utils.FileUtils;

public class GitClonePipeline extends AbstractPipeline{

    @Override
    public BuildState pipeline(YassoJob job) {
        String gitUrl = job.getGit().getUrl();
        String gitName = job.getGit().getName();
        String workspace = job.getWorkspace();
        
        job.getContext().put("build", false);
        
        if(! FileUtils.containsFile(workspace, gitName)) {
            CommandUtils.execute("git clone " + gitUrl, workspace);
            job.getContext().put("build", true);
        }
        
        if(FileUtils.containsFile(workspace, gitName)) {
            return then(job);
        }
        return BuildState.PREPARE;
    }

}
