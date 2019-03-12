package org.nico.yasso.observer;

import java.io.IOException;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

import org.nico.yasso.Yasso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJobsObserver extends JobsObserver{

    private volatile int modifyCount = 0;

    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleJobsObserver.class);
    
    @Override
    protected void event(WatchEvent<?> event) throws IOException {
        String jobConfName = event.context().toString();
        
        if(event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)) {
            LOGGER.info("create：{}", jobConfName);
            Yasso.loadJob(jobConfName);
        }else if(event.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
            modifyCount += event.count();
            if(modifyCount % 2 == 0) {
                LOGGER.info("modify：{}", jobConfName);
                Yasso.loadJob(jobConfName);
            }
        }else if(event.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)) {
            LOGGER.info("remove：{}", jobConfName);
            Yasso.removeJob(jobConfName);
        }
    }

}
