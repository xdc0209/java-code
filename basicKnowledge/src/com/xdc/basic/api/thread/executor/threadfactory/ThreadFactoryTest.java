package com.xdc.basic.api.thread.executor.threadfactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author xdc
 * 
 */
public class ThreadFactoryTest
{
    public static void main(String[] args)
    {
        // ThreadFactory threadFactory = new ThreadFactory()
        // {
        // private AtomicLong seq = new AtomicLong(0);
        //
        // @Override
        // public Thread newThread(Runnable r)
        // {
        // Thread t = new Thread(r);
        // t.setName("MyThread-" + seq.getAndIncrement());
        // return t;
        // }
        // };

        // 创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newFixedThreadPool(3, new NamePrefixThreadFactory("Eat"));
        ExecutorService pool2 = Executors.newFixedThreadPool(3, new NamePrefixThreadFactory("Drink"));
        ExecutorService pool3 = Executors.newFixedThreadPool(3, new NamePrefixThreadFactory("play"));

        // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t1 = new MyThread2();
        Thread t2 = new MyThread2();
        Thread t3 = new MyThread2();
        Thread t4 = new MyThread2();
        Thread t5 = new MyThread2();

        // 将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);

        pool2.execute(t1);
        pool2.execute(t2);
        pool2.execute(t3);
        pool2.execute(t4);
        pool2.execute(t5);

        pool3.execute(t1);
        pool3.execute(t2);
        pool3.execute(t3);
        pool3.execute(t4);
        pool3.execute(t5);

        // 关闭线程池
        pool.shutdown();
        pool2.shutdown();
        pool3.shutdown();
    }
}

class MyThread2 extends Thread
{
    @Override
    public void run()
    {
        try
        {
            System.out.println(Thread.currentThread().getName() + "正在执行。。。");
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        throw new IllegalArgumentException(
                "It's a test for " + ThreadUncaughtExceptionHandler.class.getCanonicalName());
    }
}
