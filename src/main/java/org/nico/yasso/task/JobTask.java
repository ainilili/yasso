package org.nico.yasso.task;

import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobTask implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        AbstractPipeline.handle(((YassoJob) context.getJobDetail().getJobDataMap().get("job")));
    }

}
