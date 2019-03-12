package org.nico.yasso.pipeline.impl;

import org.nico.yasso.Yasso;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.pipeline.jobs.YassoJob;
import org.nico.yasso.utils.CommandUtils;
import org.nico.yasso.utils.CommandUtils.Result;

public class PostShellPipeline extends AbstractPipeline{

    @Override
    public void pipeline(YassoJob job) {
        
        String gitName = job.getProjectName();
        String workspace = Yasso.getYasso().getWorkspace();
        
        Result result = CommandUtils.execute(job.getPostShell(), workspace + "\\" + gitName);
    }

}
