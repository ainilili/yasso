package org.nico.yasso.observer;

import java.io.IOException;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

import org.nico.yasso.Yasso;
import org.nico.yasso.utils.FileUtils;
import org.nico.yasso.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJobsObserver extends JobsObserver{


    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleJobsObserver.class);

    @Override
    protected void createEvent(WatchEvent<?> event) throws Exception {
        Yasso.loadJob(event.context().toString());
    }

    @Override
    protected void modifyEvent(WatchEvent<?> event) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void deleteEvent(WatchEvent<?> event) throws Exception {
        // TODO Auto-generated method stub
        
    }
    

}
