package com.xdc.basic.example.executor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author xdc
 * 
 */
public class ScheduledThreadPoolExecutorTest
{
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args)
    {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        long initialDelay = 1000;
        long period = 5000;

        scheduleAtFixedRateNormal(exec, initialDelay, period);
        scheduleAtFixedRateException(exec, initialDelay, period);
        scheduleAtFixedRateCatchException(exec, initialDelay, period);

        scheduleWithFixedDelay(exec, initialDelay, period);

        long delay = 5000;
        schedule(exec, delay);

        try
        {
            Thread.sleep(20000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        exec.shutdown();
    }

    /**
     * @param exec
     * @param initialDelay
     * @param period
     */
    private static void scheduleAtFixedRateNormal(ScheduledExecutorService exec, long initialDelay, long period)
    {
        /**
         * 每隔一段时间打印系统时间，互不影响的<br/>
         * 创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期；<br/>
         * 也就是将在 initialDelay 后开始执行，然后在initialDelay+period 后执行，<br/>
         * 接着在 initialDelay + 2 * period 后执行，依此类推。
         */
        exec.scheduleAtFixedRate(new Runnable()
        {
            public void run()
            {
                System.out.println(format.format(new Date()));
            }
        }, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * @param exec
     * @param initialDelay
     * @param period
     */
    private static void scheduleAtFixedRateException(ScheduledExecutorService exec, long initialDelay, long period)
    {
        //开始执行后就触发异常,next周期将不会运行
        exec.scheduleAtFixedRate(new Runnable()
        {
            public void run()
            {
                System.out.println("RuntimeException no catch,next time can't run");
                throw new RuntimeException();
            }
        }, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * @param exec
     * @param initialDelay
     * @param period
     */
    private static void scheduleAtFixedRateCatchException(ScheduledExecutorService exec, long initialDelay, long period)
    {
        //虽然抛出了运行异常,当被拦截了,next周期继续运行
        exec.scheduleAtFixedRate(new Runnable()
        {
            public void run()
            {
                try
                {
                    throw new RuntimeException();
                }
                catch (Exception e)
                {
                    System.out.println("RuntimeException catched,can run next");
                }
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
            public void run()
            {
                System.out.println("scheduleWithFixedDelay:begin," + format.format(new Date()));
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("scheduleWithFixedDelay:end," + format.format(new Date()));
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
            public void run()
            {
                System.out.println("The thread can only run once!");
            }
        }, delay, TimeUnit.MILLISECONDS);
    }
}
