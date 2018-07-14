package com.xdc.basic.api.thread;

/**
 * 摘自：http://caoyaojun1988-163-com.iteye.com/blog/1697395
 * 
 * 死锁
 * 
 */
public class DeadLockTest2
{
    private static Object lock1 = new int[1];
    private static Object lock2 = new int[1];

    public class Thread1 extends Thread
    {
        @Override
        public void run()
        {
            System.out.println("thread 1 start.");
            synchronized (lock1)
            {
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                System.out.println("thread 1 get lock 1 need lock 2.");
                synchronized (lock2)
                {
                    System.out.println("thread 1 get lock 1 and lock 2.");
                }
            }
            System.out.println("thread 1 end.");
        }
    }

    public class Thread2 extends Thread
    {
        @Override
        public void run()
        {
            System.out.println("thread 2 start.");
            synchronized (lock2)
            {
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                System.out.println("thread 2 get lock 2 need lock 1.");
                synchronized (lock1)
                {
                    System.out.println("thread 2 get lock 2 and lock 1.");
                }
            }
            System.out.println("thread 2 end.");
        }
    }

    public static void main(String[] args)
    {
        Thread1 thread1 = new DeadLockTest2().new Thread1();
        Thread2 thread2 = new DeadLockTest2().new Thread2();
        thread1.start();
        thread2.start();
    }
}
