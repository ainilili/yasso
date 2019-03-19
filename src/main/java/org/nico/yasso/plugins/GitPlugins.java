package org.nico.yasso.plugins;

import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.impl.GitCheckoutPipeline;
import org.nico.yasso.pipeline.impl.GitClonePipeline;
import org.nico.yasso.pipeline.impl.GitFetchPipeline;
import org.nico.yasso.pipeline.impl.GitPullPipeline;
import org.nico.yasso.pipeline.impl.MailPipeline;
import org.nico.yasso.pipeline.impl.PostShellPipeline;
import org.nico.yasso.pipeline.impl.PreShellPipeline;
import org.nico.yasso.pipeline.impl.ScriptPipeline;

public class GitPlugins extends AbstractPlugins{

    @Override
    public void initialize() {
        add(new GitClonePipeline());
        add(new GitFetchPipeline());
        add(new GitCheckoutPipeline());
        add(new GitPullPipeline());
        add(new ScriptPipeline());
        add(new PreShellPipeline());
        add(new PostShellPipeline());
        add(new MailPipeline());
    }

    @Override
    public void check(YassoJob job) {
        initializeGit(job);
    }

}
