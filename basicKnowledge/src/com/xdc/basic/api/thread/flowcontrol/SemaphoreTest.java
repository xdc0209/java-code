package com.xdc.basic.api.thread.flowcontrol;

import java.util.concurrent.Semaphore;

public class SemaphoreTest
{
    private static Semaphore semaphore = new Semaphore(5, true);

    public static void test()
    {
        try
        {
            // 申请一个信号量。
            semaphore.acquire();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "进来了。");

        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        // 释放一个信号量。
        semaphore.release();
        System.out.println(Thread.currentThread().getName() + "走了。");
    }

    public static void main(String[] args)
    {
        for (int i = 0; i < 100; i++)
        {
            Thread t = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    test();
                }
            });

            t.start();
        }
    }
}
