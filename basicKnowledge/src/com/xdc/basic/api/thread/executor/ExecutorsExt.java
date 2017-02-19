package com.xdc.basic.api.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsExt
{
    public static ExecutorService newCachedThreadPool(int corePoolSize, int maximumPoolSize)
    {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }

    public static ExecutorService newCachedThreadPool(int corePoolSize, int maximumPoolSize,
            ThreadFactory threadFactory)
    {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), threadFactory);
    }
}
