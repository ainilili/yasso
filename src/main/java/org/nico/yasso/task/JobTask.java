package org.nico.yasso.task;

import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.pipeline.jobs.YassoJob;

public class JobTask implements Runnable{

    private YassoJob job;
    
    public JobTask(YassoJob job) {
        this.job = job;
    }

    @Override
    public void run() {
        AbstractPipeline.handle(job);
    }
    
    public void close() {
        
    }

}
