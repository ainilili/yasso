package org.nico.yasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.nico.yasso.consts.OSType;
import org.nico.yasso.observer.SimpleJobsObserver;
import org.nico.yasso.pipeline.jobs.YassoJob;
import org.nico.yasso.task.TaskManager;
import org.nico.yasso.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class Yasso {

    private String yassoHome;
    
    private String jobsHome;
    
    private String workspace;
    
    private OSType osType; 
    
    private Set<YassoJob> jobs; 
    
    private TaskManager taskManager;
    
    private volatile static Yasso yasso;
    
    private static Yaml yaml = new Yaml();
    
    private final static Logger LOGGER = LoggerFactory.getLogger(Yasso.class);
    
    private Yasso() { }
    
    public static Yasso getInstance(String conf) throws IOException {
        if(yasso == null) {
            synchronized (Yasso.class) {
                if(yasso == null) {
                    initialize(conf);
                }
            }
        }
        return yasso;
    }
    
    public static Yasso getYasso(){
        return yasso;
    }
    
    private static void initialize(String conf) throws IOException {
        String yassoHome = System.getProperty("user.dir");
        String osName = System.getProperty("os.name");  
        
        
        File yassoConf = new File(yassoHome + "\\" + conf);
        yasso = yaml.loadAs(new FileInputStream(yassoConf), Yasso.class);
        yasso.setYassoHome(yassoHome);
        yasso.setOsType(osName.startsWith("Windows") ? OSType.WINDOWS : OSType.LINUX);
        yasso.setJobs(new LinkedHashSet<YassoJob>());
        yasso.setTaskManager(new TaskManager());
        
        new SimpleJobsObserver().observe(yasso.getJobsHome());
    }
    
    public static void loadJob(String jobConfName) throws FileNotFoundException {
        if(yasso == null) {
            throw new NullPointerException("Yasso need initialize !");
        }
        String name = FileUtils.parseName(jobConfName);
        
        File jobConf = new File(yasso.getJobsHome() + "\\" + jobConfName);
        YassoJob job = yaml.loadAs(new FileInputStream(jobConf), YassoJob.class);
        job.setName(name);
        job.init();
        
        yasso.getJobs().add(job);
        yasso.getTaskManager().remove(job);
        yasso.getTaskManager().create(job);
        
        LOGGER.info("Create job：" + job);
    }
    
    public static void removeJob(String jobConfName) {
        if(yasso == null) {
            throw new NullPointerException("Yasso need initialize !");
        }
        String name = FileUtils.parseName(jobConfName);
        YassoJob tempJob = new YassoJob();
        tempJob.setName(name);
        yasso.getJobs().remove(tempJob);
        yasso.getTaskManager().remove(tempJob);
        
        LOGGER.info("Remove job：" + tempJob);
    }
    
    public String getYassoHome() {
        return yassoHome;
    }

    public void setYassoHome(String yassoHome) {
        this.yassoHome = yassoHome;
    }

    public String getJobsHome() {
        return jobsHome;
    }

    public void setJobsHome(String jobsHome) {
        this.jobsHome = jobsHome;
    }

    public OSType getOsType() {
        return osType;
    }

    public void setOsType(OSType osType) {
        this.osType = osType;
    }

    public Set<YassoJob> getJobs() {
        return jobs;
    }

    public void setJobs(Set<YassoJob> jobs) {
        this.jobs = jobs;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
    
}
