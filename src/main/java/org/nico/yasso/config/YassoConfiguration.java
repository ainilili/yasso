package org.nico.yasso.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.nico.yasso.consts.OSType;
import org.nico.yasso.pipeline.jobs.YassoJob;
import org.yaml.snakeyaml.Yaml;

public class YassoConfiguration {

    private String yassoHome;
    
    private String jobsHome;
    
    private OSType osType; 
    
    private List<YassoJob> jobs; 
    
    private volatile static YassoConfiguration configuration;
    
    private YassoConfiguration() { }
    
    public static YassoConfiguration getInstance(String conf) throws FileNotFoundException {
        if(configuration == null) {
            synchronized (YassoConfiguration.class) {
                if(configuration == null) {
                    initialize(conf);
                }
            }
        }
        return configuration;
    }
    
    private static void initialize(String conf) throws FileNotFoundException {
        String yassoHome = System.getProperty("user.dir");
        String osName = System.getProperty("os.name");  
        
        Yaml yaml = new Yaml();
        File yassoConf = new File(yassoHome + "\\" + conf);
        configuration = yaml.loadAs(new FileInputStream(yassoConf), YassoConfiguration.class);
        configuration.setYassoHome(yassoHome);
        configuration.setOsType(osName.startsWith("Windows") ? OSType.WINDOWS : OSType.LINUX);
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

    public List<YassoJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<YassoJob> jobs) {
        this.jobs = jobs;
    }

    public OSType getOsType() {
        return osType;
    }

    public void setOsType(OSType osType) {
        this.osType = osType;
    }
    
}
