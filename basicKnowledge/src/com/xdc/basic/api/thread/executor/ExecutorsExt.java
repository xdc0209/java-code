package com.xdc.basic.api.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsExt
{
    // 线程池所有的构造方法最终都是调用此方法构造线程池：
    // java.util.concurrent.ThreadPoolExecutor.ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)
    // 参数解释：
    // corePoolSize 池中所保存的线程数，包括空闲线程。
    // maximumPoolSize 池中允许的最大线程数。
    // keepAliveTime 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
    // unit 参数的时间单位。
    // workQueue 执行前用于保持任务的队列。此队列仅由保持execute方法提交的Runnable任务。
    // handler 由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序。

    public static ExecutorService newCachedThreadPool(int corePoolSize, int maximumPoolSize)
    {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    public static ExecutorService newCachedThreadPool(int corePoolSize, int maximumPoolSize,
            ThreadFactory threadFactory)
    {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(), threadFactory);
    }
}
