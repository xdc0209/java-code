package com.xdc.basic.commons.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
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
    // workQueue 执行前用于保持任务的队列。此队列仅由保持execute方法提交的Runnable任务。几种常见的策略：
    // -- Direct handoffs(传递手)：
    // ---- 直接传递给线程执行(SynchronousQueue)，它不会存储任务，而是直接交给线程执行。如果没有可用线程，那么会创建一个新线程去执行，执行完了销毁。这种线程池不会设置最大线程数以免任务丢弃。
    // -- Unbounded queues(无界队列)：
    // ---- 使用一个无界的队列来存放任务(LinkedBlockingQueue，默认容量是Integer.Integer.MAX_VALUE)，当新的任务被提交，线程数已经达到了coreSize，这是不会再产生任何新的线程，任务都会入队，直到coreThreads有可用的。
    // ---- 这种时候任务之间相互独立的场景，如web页面请求任务。
    // -- Bounded queues(有界队列)：
    // ---- 一个有界队列(ArrayBlockingQueue)通过合理设置maxnumPoolSize以防止资源被过度消耗，这种对任务的管理方式更难于调控。
    // ---- 队列的size与线程池maxSize之间需要相互协调妥协：
    // ---- 大队列和小线程池可以减少CPU\OS资源\上下文切换的消耗，但是会导致较低的吞吐量。 如果任务频繁的阻塞(比如密集的IO，IO成为瓶颈)，CPU也许导致大量空闲资源。
    // ---- 然而小队列大线程池，CPU可以充分利用，但是频繁的线程调度上下文切换同样会导致吞吐量下降。
    // threadFactory 定制线程池内线程的名称。
    // handler 由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序。

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

    public static ExecutorService newCachedThreadPool(int corePoolSize, int maximumPoolSize, int queueSize)
    {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(queueSize));
    }

    public static ExecutorService newCachedThreadPool(int corePoolSize, int maximumPoolSize, int queueSize,
            ThreadFactory threadFactory)
    {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(queueSize), threadFactory);
    }
}
