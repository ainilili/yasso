package org.nico.yasso.pipeline.impl;

import org.nico.yasso.consts.BuildState;
import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.utils.CommandUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreShellPipeline extends AbstractPipeline{

    private final static Logger LOGGER = LoggerFactory.getLogger(PreShellPipeline.class);
    
    @Override
    public BuildState pipeline(YassoJob job) {
        
        String jobspace = job.getJobspace();

        String scripts = job.getBuild().getPre();
        
        if(CommandUtils.execute(scripts, jobspace).isSuccessed()) {
            LOGGER.info("Pre script execute successful !!");
            return then(job);
        }else {
            LOGGER.info("Pre script execute failure !!");
            return BuildState.FAILURE;
        }
        
    }

}
