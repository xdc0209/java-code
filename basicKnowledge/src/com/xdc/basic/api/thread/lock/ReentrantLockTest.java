package com.xdc.basic.api.thread.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lockers 在多线程编程里面一个重要的概念是锁定，如果一个资源是多个线程共享的，为了保证数据的完整性，
 * 在进行事务性操作时需要将共享资源锁定，这样可以保证在做事务性操作时只有一个线程能对资源进行操作，
 * 从而保证数据的完整性。在5.0以前，锁定的功能是由Synchronized关键字来实现的。
 */
public class ReentrantLockTest
{
    public static class LockTest
    {
        Lock   lock     = new ReentrantLock();
        double value    = 0d;
        int    addtimes = 0;

        /**
         * 增加value的值，该方法的操作分为2步，而且相互依赖，必须实现在一个事务中
         * 所以该方法必须同步，以前的做法是在方法声明中使用Synchronized关键字。
         */
        public void addValue(double v)
        {
            lock.lock();// 取得锁
            try
            {
                System.out.println("ReentrantLockTest.LockTest.addValue()");
                Thread.sleep(1000);
                this.value += v;
                this.addtimes++;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                lock.unlock();// 释放锁
            }
        }

        public double getValue()
        {
            System.out.println("ReentrantLockTest.LockTest.getValue()");
            return this.value;
        }
    }

    public static void main(String[] args) throws Exception
    {
        final LockTest lockTest = new LockTest();

        // 新建任务1，调用lockTest的addValue方法
        Runnable task1 = new Runnable()
        {
            public void run()
            {
                lockTest.addValue(55.55);
            }
        };
        // 新建任务2，调用lockTest的getValue方法
        Runnable task2 = new Runnable()
        {
            public void run()
            {
                System.out.println("value: " + lockTest.getValue());
            }
        };

        ExecutorService cachedService = Executors.newCachedThreadPool();

        Future<?> future = null;
        // 同时执行任务1三次，由于addValue方法使用了锁机制，所以，实质上会顺序执行
        for (int i = 0; i < 3; i++)
        {
            future = cachedService.submit(task1);
        }
        // 等待最后一个任务1被执行完
        future.get();

        // 再执行任务2，输出结果
        future = cachedService.submit(task2);
        // 等待任务2执行完后，关闭任务执行服务
        future.get();

        cachedService.shutdownNow();
    }
}
