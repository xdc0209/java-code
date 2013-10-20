package com.xdc.basic.api.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @author xdc
 * 
 */
public class ThreadFactoryTest
{
    public static void main(String[] args)
    {
        ThreadFactory threadFactory = new ThreadFactory()
        {
            private AtomicLong seq = new AtomicLong(0);

            @Override
            public Thread newThread(Runnable r)
            {
                Thread t = new Thread(r);
                t.setName("MyThread-" + seq.getAndIncrement());
                return t;
            }
        };

        // 创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newFixedThreadPool(3, threadFactory);

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

        // 关闭线程池
        pool.shutdown();
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
    }
}
