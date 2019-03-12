package org.nico.yasso.watch;

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

import com.sun.nio.file.SensitivityWatchEventModifier;

public abstract class JobsObserver {

    protected static ThreadPoolExecutor taskService = new ThreadPoolExecutor(10, 50, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    public void observe(String dir) throws IOException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path p = Paths.get(dir);
        p.register(watchService, new WatchEvent.Kind[]
                {StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE}, SensitivityWatchEventModifier.HIGH);

        taskService.execute(() -> {
            while(true){  
                WatchKey watchKey = null;
                try {
                    watchKey = watchService.take();  
                    List<WatchEvent<?>> watchEvents = watchKey.pollEvents();  
                    for(WatchEvent<?> event : watchEvents){  
                        event(event);
                    }  
                    watchKey.reset();  
                }catch(Exception e) {
                    e.printStackTrace();
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

    protected abstract void event(WatchEvent<?> event);
}
