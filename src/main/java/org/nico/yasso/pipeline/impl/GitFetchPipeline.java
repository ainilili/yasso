package org.nico.yasso.pipeline.impl;

import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.utils.CommandUtils;

public class GitFetchPipeline extends AbstractPipeline{

    @Override
    public void pipeline(YassoJob job) {
        
        String jobspace = job.getJobspace();

        CommandUtils.execute("git fetch", jobspace);
        
        then(job);
    }

}
