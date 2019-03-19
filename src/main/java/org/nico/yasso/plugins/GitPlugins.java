package org.nico.yasso.plugins;

import org.nico.yasso.consts.BuildState;
import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.impl.GitCheckoutPipeline;
import org.nico.yasso.pipeline.impl.GitClonePipeline;
import org.nico.yasso.pipeline.impl.GitFetchPipeline;
import org.nico.yasso.pipeline.impl.GitPullPipeline;
import org.nico.yasso.pipeline.impl.MailPipeline;
import org.nico.yasso.pipeline.impl.PostShellPipeline;
import org.nico.yasso.pipeline.impl.PreShellPipeline;
import org.nico.yasso.pipeline.impl.ScriptPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitPlugins extends AbstractPlugins{

    private MailPipeline mailPipeline;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(GitPlugins.class);
    
    @Override
    public void initialize() {
        add(new GitClonePipeline());
        add(new GitFetchPipeline());
        add(new GitCheckoutPipeline());
        add(new GitPullPipeline());
        add(new ScriptPipeline());
        add(new PreShellPipeline());
        add(new PostShellPipeline());
        mailPipeline = new MailPipeline();
    }

    @Override
    public void check(YassoJob job) {
        initializeGit(job);
    }
    
    @Override
    public void handle(YassoJob job) {
        BuildState buildState = BuildState.NULL;
        if(! pipelines.isEmpty()) {
            buildState = pipelines.getFirst().pipeline(job);
            
            job.getContext().put("buildState", buildState);
            LOGGER.info("Build state：{}", buildState);
        }
        
        //Tail
        if(BuildState.SUCCESS == buildState) {
            mailPipeline.pipeline(job);
        }
    }

}
