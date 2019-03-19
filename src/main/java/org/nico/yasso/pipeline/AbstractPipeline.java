package org.nico.yasso.pipeline;

import org.nico.yasso.consts.BuildState;
import org.nico.yasso.entity.YassoJob;

public abstract class AbstractPipeline {

    protected AbstractPipeline next;
    
    public abstract BuildState pipeline(YassoJob job);
    
    protected BuildState then(YassoJob job) {
        if(next != null) return next.pipeline(job);
        return BuildState.SUCCESS;
    }
    
    public AbstractPipeline getNext() {
        return next;
    }

    public void setNext(AbstractPipeline next) {
        this.next = next;
    }

}
