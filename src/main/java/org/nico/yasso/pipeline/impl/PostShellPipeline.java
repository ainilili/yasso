package org.nico.yasso.pipeline.impl;

import org.nico.yasso.Yasso;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.pipeline.jobs.YassoJob;
import org.nico.yasso.utils.CommandUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostShellPipeline extends AbstractPipeline{

    private final static Logger LOGGER = LoggerFactory.getLogger(PostShellPipeline.class);
    
    @Override
    public void pipeline(YassoJob job) {
        
        String gitName = job.getProjectName();
        String workspace = Yasso.getYasso().getWorkspace();
        
        CommandUtils.execute(job.getPostShell(), workspace + "\\" + gitName);
        LOGGER.info("Build finished");
        
    }

}
