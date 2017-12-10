package com.xdc.basic.api.thread.executor;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.xdc.basic.api.DateUitl;

/**
 * http://ketqi.blog.51cto.com/1130608/687681
 */
public class ScheduledThreadPoolExecutorTest
{
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static void main(String[] args)
    {
        // 注意：下面的两个方法，在执行过程中出现任何异常，且没有捕获，都会导致当前及后续任务终止。
        // exec.scheduleAtFixedRate(command, initialDelay, period, unit)
        // exec.scheduleWithFixedDelay(command, initialDelay, delay, unit)
        // 可以在java doc中看到如下说明：
        // If any execution of the task encounters an exception, subsequent executions are suppressed.
        // 如果任务的任何一个执行遇到异常，则后续执行都会被取消。

        // 参数corePoolSize代表线程池中线程个数，即使它们空闲
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);

        long initialDelay = 0;
        long period = 5000;
        scheduleAtFixedRate(exec, initialDelay, period);
        scheduleWithFixedDelay(exec, initialDelay, period);

        long delay = 10000;
        schedule(exec, delay);

        cancelSchedule(exec, initialDelay, period);

        try
        {
            Thread.sleep(50000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        exec.shutdown();
        System.out.println("定时器关闭！");
    }

    private static void cancelSchedule(ScheduledExecutorService exec, long initialDelay, long period)
    {
        ScheduledFuture<?> scheduledFuture = exec.scheduleAtFixedRate(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("cancelSchedule: " + DateUitl.format(new Date(), DATE_FORMAT));
            }
        }, initialDelay, period, TimeUnit.MILLISECONDS);

        try
        {
            Thread.sleep(20000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        scheduledFuture.cancel(false);
        System.out
                .println("cancelSchedule: the schedule has been canceled!" + DateUitl.format(new Date(), DATE_FORMAT));
    }

    /**
     * @param exec
     * @param initialDelay
     * @param period
     */
    private static void scheduleAtFixedRate(ScheduledExecutorService exec, long initialDelay, long period)
    {
        /**
         * 每隔一段时间打印系统时间，互不影响的<br/>
         * 创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期；<br/>
         * 也就是将在 initialDelay 后开始执行，然后在initialDelay+period 后执行，<br/>
         * 接着在 initialDelay + 2 * period 后执行，依此类推。
         */
        exec.scheduleAtFixedRate(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("scheduleAtFixedRate: " + DateUitl.format(new Date(), DATE_FORMAT));
            }
        }, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * @param exec
     * @param initialDelay
     * @param period
     */
    private static void scheduleWithFixedDelay(ScheduledExecutorService exec, long initialDelay, long period)
    {
        /**
         * 创建并执行一个在给定初始延迟后首次启用的定期操作，<br/>
         * 随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。
         */
        exec.scheduleWithFixedDelay(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("scheduleWithFixedDelay-begin: " + DateUitl.format(new Date(), DATE_FORMAT));
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("scheduleWithFixedDelay-end: " + DateUitl.format(new Date(), DATE_FORMAT));
            }
        }, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * @param exec
     * @param delay
     */
    private static void schedule(ScheduledExecutorService exec, long delay)
    {
        /**
         * 创建并执行在给定延迟后启用的一次性操作。
         */
        exec.schedule(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("The thread can only run once!");
            }
        }, delay, TimeUnit.MILLISECONDS);
    }
}
