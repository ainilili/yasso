package org.nico.yasso.watch.test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.junit.Test;

public class WatchTest {

    @Test
    public void test() throws IOException, InterruptedException {
        
        new Thread() {
            @Override
            public void run() {
                String fileDirectory = "D:\\yasso";
                WatchService service = null;
                try {
                    service = FileSystems.getDefault().newWatchService();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                
                try {
                    Paths.get(fileDirectory)
                    .register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
                            StandardWatchEventKinds.ENTRY_MODIFY);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                while (true) {
                    WatchKey key = null;
                    try {
                        //获取可用key.没有可用的就wait
                        key = service.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (WatchEvent<?> event : key.pollEvents()) {
                       System.out.println(event.context() + "文件:" + event.kind() + "次数: " + event.count());
                    }
                    //重置，这一步很重要，否则当前的key就不再会获取将来发生的事件
                    boolean valid = key.reset();
                    //失效状态，退出监听
                    if (!valid) {
                        break;
                    }
                }
            }
        }.start();
        
        Thread.sleep(Long.MAX_VALUE);
    }
}
    
