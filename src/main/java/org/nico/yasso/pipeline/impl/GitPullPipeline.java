package org.nico.yasso.pipeline.impl;

import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.utils.CommandUtils;
import org.nico.yasso.utils.CommandUtils.Result;

public class GitPullPipeline extends AbstractPipeline{

    @Override
    public void pipeline(YassoJob job) {

        String jobspace = job.getJobspace();

        Result result = CommandUtils.execute("git pull --progress -v --no-rebase origin " + job.getGit().getBranch(), jobspace);
        if(! result.getSuccessMsg().startsWith("Already")) {
            then(job);
        }
    }

}
