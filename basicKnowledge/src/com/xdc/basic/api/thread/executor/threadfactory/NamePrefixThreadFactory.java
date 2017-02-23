package com.xdc.basic.api.thread.executor.threadfactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 参考自: java.util.concurrent.Executors.DefaultThreadFactory
 */
public class NamePrefixThreadFactory implements ThreadFactory
{
    // 未捕获的异常处理类：当run方法中出现异常且未捕获而导致线程死亡时，进行兜底处理，处理方式就是简单的打印日志，便于定位问题。
    private static final ThreadUncaughtExceptionHandler threadUncaughtExceptionHandler = new ThreadUncaughtExceptionHandler();

    // 记录线程的个数
    private static final AtomicInteger                  poolNumber                     = new AtomicInteger(1);

    // 线程组
    private final ThreadGroup                           group;

    // 记录线程池中线程的个数
    private final AtomicInteger                         threadNumber                   = new AtomicInteger(1);

    // 线程名称前缀
    private final String                                namePrefix;

    public NamePrefixThreadFactory(String name)
    {
        namePrefix = name + "-" + poolNumber.getAndIncrement() + "-thread-";

        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r)
    {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);

        t.setUncaughtExceptionHandler(threadUncaughtExceptionHandler);

        if (t.isDaemon())
        {
            t.setDaemon(false);
        }

        if (t.getPriority() != Thread.NORM_PRIORITY)
        {
            t.setPriority(Thread.NORM_PRIORITY);
        }

        return t;
    }
}
