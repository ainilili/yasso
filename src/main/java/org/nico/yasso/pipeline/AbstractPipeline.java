package org.nico.yasso.pipeline;

import org.nico.yasso.pipeline.jobs.YassoJob;

public abstract class AbstractPipeline {

    public abstract void pipeline(YassoJob job);
}
