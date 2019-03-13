package org.nico.yasso.pipeline.impl;

import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.utils.CommandUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostShellPipeline extends AbstractPipeline{

    private final static Logger LOGGER = LoggerFactory.getLogger(PostShellPipeline.class);
    
    @Override
    public void pipeline(YassoJob job) {
        
        String gitName = job.getName();
        String jobspace = job.getJobspace();
        
        CommandUtils.execute(job.getBuild().getPost(), jobspace);
    }

}
