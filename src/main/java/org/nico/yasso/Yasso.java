package org.nico.yasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.observer.SimpleJobsObserver;
import org.nico.yasso.task.TaskManager;
import org.nico.yasso.utils.FileUtils;
import org.nico.yasso.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class Yasso {

    private String yassoHome;

    private String confspace;

    private String workspace;

    private boolean isUnix;

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
        String os = System.getProperty("os.name");  

        FileInputStream yassoInputStream = null;
        try {
            File yassoConf = new File(FileUtils.combination(yassoHome, conf));
            yassoInputStream = new FileInputStream(yassoConf);
            
            yasso = yaml.loadAs(yassoInputStream, Yasso.class);
            yasso.setYassoHome(yassoHome);
            yasso.setJobs(new LinkedHashSet<YassoJob>());
            yasso.setTaskManager(new TaskManager());

            if(! os.toLowerCase().startsWith("win")){  
                yasso.setUnix(true);
            } 

            String workspace = yasso.getWorkspace();
            String confspace = yasso.getConfspace();

            if(StringUtils.isNotBlank(workspace)) {
                yasso.setWorkspace(FileUtils.isRelative(workspace) ? FileUtils.combination(yassoHome, workspace) : workspace);
            }
            if(StringUtils.isNotBlank(confspace)) {
                yasso.setConfspace(FileUtils.isRelative(confspace) ? FileUtils.combination(yassoHome, confspace) : confspace);
            }

            LOGGER.info("Yasso load with {}", yasso);
            FileUtils.createDirIfAbsent(yasso.getWorkspace());
            FileUtils.createDirIfAbsent(yasso.getConfspace());

            new SimpleJobsObserver().observe(yasso.getConfspace());
        }finally {
            if(yassoInputStream != null) yassoInputStream.close();
        }
    }

    public static void loadJob(String jobConfName) throws FileNotFoundException {
        if(yasso == null) {
            throw new NullPointerException("Yasso need initialize !");
        }
        FileInputStream confInputStream = null;
        try {
            String name = FileUtils.parseName(jobConfName);
            
            File jobConf = new File(FileUtils.combination(yasso.getConfspace(), jobConfName));
            
            confInputStream = new FileInputStream(jobConf);
            YassoJob job = yaml.loadAs(confInputStream, YassoJob.class);
            if(job != null) {
                if(job.getBuild() == null) {
                    LOGGER.info("Create job {}, waiting for perfect configuration {}", name, "build{cron, pre, post}");
                }else if(job.getGit() == null) {
                    LOGGER.info("Create job {}, waiting for perfect configuration {}", name, "get{url, [user], [pwd]}");
                }else {
                    job.setName(name);
                    job.initialize();

                    yasso.getJobs().add(job);
                    yasso.getTaskManager().remove(job);
                    yasso.getTaskManager().create(job);
                    LOGGER.info("Create job {} successful !!", name);
                }
            }else {
                LOGGER.info("Create job {}, waiting for perfect configuration.", name);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(confInputStream != null) confInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

    public String getConfspace() {
        return confspace;
    }

    public void setConfspace(String confspace) {
        this.confspace = confspace;
    }

    public Set<YassoJob> getJobs() {
        return jobs;
    }

    public void setJobs(Set<YassoJob> jobs) {
        this.jobs = jobs;
    }

    public boolean isUnix() {
        return isUnix;
    }

    public void setUnix(boolean isUnix) {
        this.isUnix = isUnix;
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

    @Override
    public String toString() {
        return "Yasso [yassoHome=" + yassoHome + ", confspace=" + confspace + ", workspace=" + workspace + "]";
    }

}
