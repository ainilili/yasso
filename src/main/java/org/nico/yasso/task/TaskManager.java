package org.nico.yasso.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.nico.yasso.pipeline.jobs.YassoJob;

public class TaskManager {

    protected final static ThreadPoolExecutor TASK_SERVICE = new ThreadPoolExecutor(10, 50, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    private Map<String, JobTask> taskMap;

    public TaskManager() {
        taskMap = new ConcurrentHashMap<String, JobTask>();
    }
    
    public void create(YassoJob job) {
        JobTask task = new JobTask(job);
        taskMap.put(job.getName(), task);
        
        TASK_SERVICE.execute(task);
    }
    
    public void remove(YassoJob job) {
        JobTask task = taskMap.remove(job.getName());
        if(task != null) {
            task.close();
        }
    }

}
