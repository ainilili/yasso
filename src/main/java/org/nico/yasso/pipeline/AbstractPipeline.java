package org.nico.yasso.pipeline;

import org.nico.yasso.entity.YassoJob;

public abstract class AbstractPipeline {

    protected AbstractPipeline next;
    
    public abstract void pipeline(YassoJob job);
    
    protected void then(YassoJob job) {
        if(next != null) next.pipeline(job);
    }
    
    public AbstractPipeline getNext() {
        return next;
    }

    public void setNext(AbstractPipeline next) {
        this.next = next;
    }

}
