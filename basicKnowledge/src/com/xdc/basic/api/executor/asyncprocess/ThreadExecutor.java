package com.xdc.basic.api.executor.asyncprocess;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutor
{
    private ExecutorService             threadPool;
    private static final ThreadExecutor instance = new ThreadExecutor();

    private ThreadExecutor()
    {
        threadPool = Executors.newFixedThreadPool(20, new ExecutorThreadFactory());
    }

    public static ThreadExecutor getInstance()
    {
        return instance;
    }

    public void asyncExec(Runnable task)
    {
        threadPool.execute(task);
    }

    public static void sleepSecond(long second)
    {
        try
        {
            Thread.sleep(second);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}