package org.nico.yasso.pipeline.impl;

import org.nico.yasso.Yasso;
import org.nico.yasso.consts.BuildState;
import org.nico.yasso.entity.YassoJob;
import org.nico.yasso.pipeline.AbstractPipeline;
import org.nico.yasso.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScriptPipeline extends AbstractPipeline{

    private final static Logger LOGGER = LoggerFactory.getLogger(ScriptPipeline.class);
    
    @Override
    public BuildState pipeline(YassoJob job) {
        if(! job.isScriptBuildFlag()) {
            job.setScriptBuildFlag(true);
            
            String jobspace = job.getJobspace();
            boolean isUnix = Yasso.getYasso().isUnix();
            
            String suffix = isUnix ? ".sh" : ".bat";
            String preScriptFilePath = FileUtils.combination(jobspace, "pre" + suffix);
            String postScriptFilePath = FileUtils.combination(jobspace, "post" + suffix);
            
            String preScriptContent = job.getBuild().getPre();
            String postScriptContent = job.getBuild().getPost();
            
            String preScript = isUnix ? "./pre.sh" : "cmd /c .\\pre.bat";
            String postScript = isUnix ? "./post.sh" : "cmd /c .\\post.bat";
            
            FileUtils.createFileAndWrite(preScriptFilePath, preScriptContent);
            LOGGER.info("Rebuild pre script: {}", preScriptFilePath);
            
            FileUtils.createFileAndWrite(postScriptFilePath, postScriptContent);
            LOGGER.info("Rebuild post script: {}", postScriptFilePath);
            
            job.getBuild().setPre(preScript);
            job.getBuild().setPost(postScript);
        }
        
        
        return then(job);
    }

}
