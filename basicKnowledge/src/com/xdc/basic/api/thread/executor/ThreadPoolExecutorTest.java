package com.xdc.basic.api.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * http://www.cnblogs.com/jersey/archive/2011/03/30/2000231.html
 * 
 * @author xdc
 * 
 */
public class ThreadPoolExecutorTest
{
    public static void main(String[] args) throws InterruptedException
    {
        // 创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newFixedThreadPool(3);

        // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t1 = new MyThread("t1");
        Thread t2 = new MyThread("t2");
        Thread t3 = new MyThread("t3");
        Thread t4 = new MyThread("t4");
        Thread t5 = new MyThread("t5");

        // 将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);

        // 关闭线程池，异步操作，不会等待任务执行完成，不会阻塞。只是设置线程池的标志位为停止状态，此时不在接受新的任务。
        pool.shutdown();
        System.out.println("Pool state is setted to stop.");

        System.out.println("Wait until tasks are executed...");
        pool.awaitTermination(60, TimeUnit.MINUTES);
        System.out.println("All tasks are executed...");
    }
}

class MyThread extends Thread
{
    String name;

    public MyThread(String name)
    {
        super();
        this.name = name;
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println(String.format("[name:%s, threadName:%s] 执行开始.", name, Thread.currentThread().getName()));
            Thread.sleep(1000);
            System.out.println(String.format("[name:%s, threadName:%s] 执行结束.", name, Thread.currentThread().getName()));
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
