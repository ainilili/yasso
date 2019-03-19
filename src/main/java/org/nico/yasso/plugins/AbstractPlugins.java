package org.nico.yasso.plugins;

import java.util.LinkedList;

import org.nico.yasso.entity.Git;
import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.utils.FileUtils;
import org.nico.yasso.utils.StringUtils;

public abstract class AbstractPlugins{

    protected LinkedList<AbstractPipeline> pipelines = new LinkedList<AbstractPipeline>();

    public abstract void initialize();

    public abstract void check(YassoJob job);

    public void handle(YassoJob job) {
        if(! pipelines.isEmpty()) {
            pipelines.getFirst().pipeline(job);
        }
    }
    
    protected void add(AbstractPipeline pipeline) {
        if(! pipelines.isEmpty()) {
            pipelines.getLast().setNext(pipeline);
        }
        pipelines.addLast(pipeline);
    }
    
    protected void initializeGit(YassoJob job) {
        Git git = job.getGit();
        
        String gitUser = git.getUser();
        String gitPwd = git.getPwd();
        String gitUrl = git.getUrl();
        String projectName = null;
        
        if(StringUtils.isNotBlank(gitUser)
                && StringUtils.isNotBlank(gitPwd)) {
            String sign = gitUser + ":" + gitPwd + "@";
            int httpFlag = gitUrl.indexOf("://");
            httpFlag += 3;
            gitUrl = gitUrl.substring(0, httpFlag) + sign + gitUrl.substring(httpFlag);
        }

        projectName = gitUrl.substring(gitUrl.lastIndexOf("/") + 1, gitUrl.lastIndexOf("."));
        
        git.setName(projectName);
        git.setUrl(gitUrl);
        
        job.setJobspace(FileUtils.combination(job.getWorkspace(), projectName));
    }

    




}
