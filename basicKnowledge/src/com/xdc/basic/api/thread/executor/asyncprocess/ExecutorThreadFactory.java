package com.xdc.basic.api.thread.executor.asyncprocess;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class ExecutorThreadFactory implements ThreadFactory
{
    private static final String THREAD_NAME_PREFIX = "MyThread-";

    private AtomicLong          seq                = new AtomicLong(0);

    @Override
    public Thread newThread(Runnable r)
    {
        Thread t = new Thread(r);
        t.setName(THREAD_NAME_PREFIX + seq.getAndIncrement());
        return t;
    }
}
