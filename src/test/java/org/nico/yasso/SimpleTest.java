package org.nico.yasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;
import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.utils.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.yaml.snakeyaml.Yaml;

public class SimpleTest {

    @Test
    public void test() {
        Set<YassoJob> sets = new LinkedHashSet<YassoJob>();
        YassoJob job = new YassoJob();
        job.setName("test");

        sets.add(job);
        System.out.println(sets.size());

        YassoJob job1 = new YassoJob();
        job1.setName("test");
        sets.remove(job1);
        System.out.println(sets.size());
    }

    @Test
    public void testQuartz() throws InterruptedException {
        try {
            JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob").build();

            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger").withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ?")).build();

            //创建schedule实例
            StdSchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler = factory.getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail,cronTrigger);


            new Thread() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(1000 * 5);
                        scheduler.deleteJob(jobDetail.getKey());
                    } catch (SchedulerException | InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }

        Thread.sleep(10000000);

    }

    public static class HelloJob implements Job{

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println(System.currentTimeMillis());
        }

    }

    @Test
    public void testUrl() {
        String gitUrl = "https://github.com/ainilili/xxx.git";
        String sign = "abc:efg@";

        int httpFlag = gitUrl.indexOf("://");
        httpFlag += 3;
        gitUrl = gitUrl.substring(0, httpFlag) + sign + gitUrl.substring(httpFlag);
        System.out.println(gitUrl);
        
        System.out.println(gitUrl.substring(gitUrl.lastIndexOf("/") + 1, gitUrl.lastIndexOf(".")));
    }
    
    @Test
    public void testYaml() throws FileNotFoundException {
        Yaml yaml = new Yaml();
        File f = new File("D:\\yasso\\snails.yml");
        YassoJob job = yaml.loadAs(new FileInputStream(f), YassoJob.class);
        
        String post = job.getBuild().getPost();
        System.out.println(post);
    }
}
