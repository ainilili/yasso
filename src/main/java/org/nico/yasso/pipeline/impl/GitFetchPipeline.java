package org.nico.yasso.pipeline.impl;

import org.nico.yasso.consts.BuildState;
import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.utils.CommandUtils;

public class GitFetchPipeline extends AbstractPipeline{

    @Override
    public BuildState pipeline(YassoJob job) {
        
        String jobspace = job.getJobspace();

        if(CommandUtils.execute("git fetch", jobspace).isSuccessed()) {
            return then(job);    
        }
        return BuildState.PREPARE;
    }

}
