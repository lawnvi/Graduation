package com.buct.graduation.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {
    private static ExecutorService threadPool = newCachedThreadPool();

    public static ExecutorService getThreadPool(){
        return threadPool;
    }

    /**
     * 线程池
     * @return
     */
    private static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,    //核心大小=0，最大数量不限，存活时间为60s（若长时间没有任务则该线程池为空）,使用SynchronousQueue作为workeQueue
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }
}
