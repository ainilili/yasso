package org.nico.yasso.pipeline.impl;

import org.nico.yasso.consts.BuildState;
import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.utils.CommandUtils;
import org.nico.yasso.utils.StringUtils;

public class GitCheckoutPipeline extends AbstractPipeline{

    protected final static String DEFAULT_BRANCH = "master";
    
    @Override
    public BuildState pipeline(YassoJob job) {
        String branch = job.getGit().getBranch();
        String jobspace = job.getJobspace();
        
        if(StringUtils.isBlank(branch)) {
            branch = DEFAULT_BRANCH;
            job.getGit().setBranch(branch);;
        }
        
        if(CommandUtils.execute("git checkout " + branch, jobspace).isSuccessed()) {
            return then(job);
        }
        return BuildState.PREPARE;
    }

}
