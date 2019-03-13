package org.nico.yasso.pipeline.impl;

import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.utils.CommandUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreShellPipeline extends AbstractPipeline{

    private final static Logger LOGGER = LoggerFactory.getLogger(PreShellPipeline.class);
    
    @Override
    public void pipeline(YassoJob job) {
        
        String jobspace = job.getJobspace();

        String[] scripts = job.getBuild().getPre().split("\r\n|\n");
        
        CommandUtils.execute(scripts, jobspace);
        LOGGER.info("Pre script execute successful !!");
        
        then(job);
    }

}
