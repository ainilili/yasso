package org.nico.yasso;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.exceptions.MissingException;
import org.nico.yasso.observer.SimpleJobsObserver;
import org.nico.yasso.task.TaskManager;
import org.nico.yasso.utils.FileUtils;
import org.nico.yasso.utils.StringUtils;
import org.nico.yasso.utils.YamlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Yasso {

    private String yassoHome;

    private String confspace;

    private String workspace;

    private boolean isUnix;

    private Set<YassoJob> jobs; 

    private TaskManager taskManager;

    private volatile static Yasso yasso;

    private final static Logger LOGGER = LoggerFactory.getLogger(Yasso.class);

    private Yasso() { }

    public static Yasso getInstance(String conf) throws IOException, MissingException {
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

    private static void initialize(String conf) throws IOException, MissingException {
        String yassoHome = System.getProperty("user.dir");
        String os = System.getProperty("os.name");  

        yasso = YamlUtils.loadAs(FileUtils.combination(yassoHome, conf), Yasso.class);
        
        if(yasso == null) {
            throw new MissingException("Missing profile.");
        }
        String workspace = yasso.getWorkspace();
        String confspace = yasso.getConfspace();
        
        if(StringUtils.isNotBlank(workspace)) {
            yasso.setWorkspace(FileUtils.isRelative(workspace) ? FileUtils.combination(yassoHome, workspace) : workspace);
        }else {
            throw new MissingException("Missing [workspace] configuration parameter.");
        }

        if(StringUtils.isNotBlank(confspace)) {
            yasso.setConfspace(FileUtils.isRelative(confspace) ? FileUtils.combination(yassoHome, confspace) : confspace);
        }else {
            throw new MissingException("Missing [confspace] configuration parameter.");
        }
        
        yasso.setYassoHome(yassoHome);
        yasso.setJobs(new LinkedHashSet<YassoJob>());
        yasso.setTaskManager(new TaskManager());
        yasso.setUnix(! os.toLowerCase().startsWith("win"));
        LOGGER.info("Yasso load with {}", yasso);
        
        FileUtils.createDirIfAbsent(yasso.getWorkspace());
        FileUtils.createDirIfAbsent(yasso.getConfspace());
        
        File confspaceDir = new File(confspace);
        Arrays.asList(confspaceDir.list()).stream().filter(FileUtils::isYaml).forEach(name -> {
            try {
                LOGGER.info("Load the job configuration file {}", name);
                loadJob(name);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        });
        
        new SimpleJobsObserver().observe(yasso.getConfspace());
    }

    public static void loadJob(String jobConfName) throws IOException {
        if(yasso == null) {
            throw new NullPointerException("Yasso need initialize !");
        }
        String name = FileUtils.parseFileName(jobConfName);

        YassoJob job = YamlUtils.loadAs(FileUtils.combination(yasso.getConfspace(), jobConfName), YassoJob.class);
        if(job == null) {
            LOGGER.info("Create job {}, waiting for perfect configuration.", name);
        }else if(job.getBuild() == null) {
            LOGGER.info("Create job {}, waiting for perfect configuration {}", name, "build{cron, pre, post}");
        }else if(job.getGit() == null) {
            LOGGER.info("Create job {}, waiting for perfect configuration {}", name, "get{url, [user], [pwd]}");
        }else {
            job.setName(name);
            job.initialize();

            yasso.getJobs().add(job);
            yasso.getTaskManager().remove(name);
            yasso.getTaskManager().create(job);
            LOGGER.info("Create job {} successful !!", name);
        }
    }

    public static void removeJob(String jobConfName) {
        if(yasso == null) {
            throw new NullPointerException("Yasso need initialize !");
        }
        
        String name = FileUtils.parseFileName(jobConfName);
        
        yasso.getJobs().remove(new YassoJob(name));
        yasso.getTaskManager().remove(name);
        LOGGER.info("Remove job：" + name);
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
