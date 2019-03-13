package org.nico.yasso.pipeline;

import java.util.LinkedList;

import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.impl.GitPipeline;
import org.nico.yasso.pipeline.impl.PostShellPipeline;
import org.nico.yasso.pipeline.impl.PreShellPipeline;
import org.nico.yasso.pipeline.impl.ScriptPipeline;

public abstract class AbstractPipeline {

    public static LinkedList<AbstractPipeline> pipelines = new LinkedList<AbstractPipeline>();
    
    protected AbstractPipeline next;
    
    static {
        add(new GitPipeline());
        add(new ScriptPipeline());
        add(new PreShellPipeline());
        add(new PostShellPipeline());
    }
    
    public static void add(AbstractPipeline pipeline) {
        if(! pipelines.isEmpty()) {
            pipelines.getLast().next = pipeline;
        }
        pipelines.addLast(pipeline);
    }
    
    public static void handle(YassoJob job) {
        if(! pipelines.isEmpty()) {
            pipelines.getFirst().pipeline(job);
        }
    }
    
    protected void then(YassoJob job) {
        if(next != null) next.pipeline(job);
    }
    
    public abstract void pipeline(YassoJob job);
}
