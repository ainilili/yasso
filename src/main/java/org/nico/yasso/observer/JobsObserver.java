package org.nico.yasso.observer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.nico.yasso.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class JobsObserver {

    private volatile int modifyCount = 0;

    private final static Logger LOGGER = LoggerFactory.getLogger(JobsObserver.class);
    
    protected final static ThreadPoolExecutor OBSERVER_SERVICE = new ThreadPoolExecutor(10, 50, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    public void observe(String dir) throws IOException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(dir);
        path.register(watchService, new WatchEvent.Kind[]{
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE});

        OBSERVER_SERVICE.execute(() -> {
            while(true){  
                WatchKey watchKey = null;
                try {
                    watchKey = watchService.take();  
                    List<WatchEvent<?>> watchEvents = watchKey.pollEvents();  

                    watchEvents.stream().filter(event -> FileUtils.isYaml(event.context().toString())).forEach(event -> {
                        try {
                            if(event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)) {
                                createEvent(event);
                            }else if(event.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
                                modifyCount += event.count();
                                if(modifyCount % 2 == 0) {
                                    modifyEvent(event);
                                }
                            }else if(event.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)) {
                                deleteEvent(event);
                            }  
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage());
                        }
                    });
                    watchKey.reset();  
                }catch(Exception e) {
                    LOGGER.error(e.getMessage());
                }finally {
                    if(watchKey != null && ! watchKey.isValid()) break;
                }
            }  
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                watchService.close();
            } catch (Exception e) {}
        }));
    }

    protected abstract void createEvent(WatchEvent<?> event) throws Exception;

    protected abstract void modifyEvent(WatchEvent<?> event) throws Exception;

    protected abstract void deleteEvent(WatchEvent<?> event) throws Exception;
}
