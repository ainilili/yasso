package org.nico.yasso.task;

import org.nico.yasso.entity.YassoJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobTask implements Job{

    private final static Logger LOGGER = LoggerFactory.getLogger(JobTask.class);
    
    @Override
    public void execute(JobExecutionContext context)  {
        try {
            YassoJob job = ((YassoJob) context.getJobDetail().getJobDataMap().get("job"));
            job.getPlugins().handle(job);
        }catch(Exception e) {
            LOGGER.error("Task Error：" + e.getMessage());
            e.printStackTrace();
        }
        
    }

}
