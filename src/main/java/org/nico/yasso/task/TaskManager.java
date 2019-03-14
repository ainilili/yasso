package org.nico.yasso.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.nico.yasso.entity.YassoJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskManager {

    private Scheduler scheduler;
    
    private Map<String, JobDetail> taskMap;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(TaskManager.class);

    public TaskManager() {
        taskMap = new ConcurrentHashMap<String, JobDetail>();
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void create(YassoJob job) {
        JobDataMap jobData = new JobDataMap();
        jobData.put("job", job);
        JobDetail jobDetail = JobBuilder.newJob(JobTask.class)
                            .withIdentity("JobDetail-" + job.getName())
                            .setJobData(jobData)
                            .build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                            .withIdentity("cronTrigger-" + job.getName())
                            .withSchedule(CronScheduleBuilder.cronSchedule(job.getBuild().getCron()))
                            .build();
        
        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
            LOGGER.info("Task schedule job [{}] with cron {}", job.getName(), job.getBuild().getCron());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        taskMap.put(job.getName(), jobDetail);

    }

    public void remove(String name) {
        JobDetail jobDetail = taskMap.remove(name);
        if(jobDetail != null) {
            try {
                scheduler.deleteJob(jobDetail.getKey());
                LOGGER.info("Task remove job [{}]", name);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }

}
