package org.nico.yasso.pipeline;

import java.util.LinkedList;

import org.nico.yasso.pipeline.jobs.YassoJob;

public abstract class AbstractPipeline {

    public static LinkedList<AbstractPipeline> pipelines = new LinkedList<AbstractPipeline>();
    
    static {
        
    }
    
    public static void handle(YassoJob job) {
        if(! pipelines.isEmpty()) {
            pipelines.stream().forEach(pipeline -> {
                pipeline.pipeline(job);
            });
        }
    }
    
    public abstract void pipeline(YassoJob job);
}
