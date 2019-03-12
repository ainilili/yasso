package org.nico.yasso.watch;

import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

public class SimpleJobsObserver extends JobsObserver{

    private volatile int modifyCount = 0;
    
    @Override
    protected void event(WatchEvent<?> event) {
        if(event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)) {
            System.out.println("create");
        }else if(event.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
            modifyCount += event.count();
            if(modifyCount % 2 == 0) {
                System.out.println("modify：" + event.count());
            }
        }else if(event.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)) {
            System.out.println("delete");
        }
    }

}
