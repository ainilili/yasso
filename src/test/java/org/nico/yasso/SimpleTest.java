package org.nico.yasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;
import org.nico.yasso.entity.YassoJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
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
