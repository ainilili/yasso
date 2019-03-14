package org.nico.yasso.observer;

import java.nio.file.WatchEvent;

import org.nico.yasso.Yasso;
import org.nico.yasso.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJobsObserver extends JobsObserver{


    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleJobsObserver.class);

    @Override
    protected void createEvent(WatchEvent<?> event) throws Exception {
        String fileName = FileUtils.parseFileName(event.context().toString());
        LOGGER.info("Job config monitor system [CREATION] event triggered -> {}", fileName);
        
        Yasso.loadJob(fileName);
    }

    @Override
    protected void modifyEvent(WatchEvent<?> event) throws Exception {
        String fileName = FileUtils.parseFileName(event.context().toString());
        LOGGER.info("Job config monitor system [MODIFY] event triggered -> {}", fileName);
        
        Yasso.loadJob(fileName);
    }

    @Override
    protected void deleteEvent(WatchEvent<?> event) throws Exception {
        String fileName = FileUtils.parseFileName(event.context().toString());
        LOGGER.info("Job config monitor system [DELETE] event triggered -> {}", fileName);
        
        Yasso.removeJob(fileName);
    }
    

}
