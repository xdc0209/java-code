package com.xdc.basic.example.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * http://www.cnblogs.com/jersey/archive/2011/03/30/2000231.html
 * 
 * @author xdc
 * 
 */
public class ThreadPoolTest
{
    public static void main(String[] args)
    {
        // 创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newFixedThreadPool(3);

        // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        Thread t3 = new MyThread();
        Thread t4 = new MyThread();
        Thread t5 = new MyThread();

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

class MyThread extends Thread
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
