package com.xdc.basic.api.thread.flowcontrol;

import java.util.concurrent.CountDownLatch;

/**
 * 当你启动了一个线程，你需要等它执行结束，此时，CountDownLatch也许是一个很好的选择。
 * 
 * @author xdc
 * 
 */
public class CoutDownLatchTest1
{
    public static void main(String[] args) throws InterruptedException
    {
        final int COUNT = 10;
        final CountDownLatch completeLatch = new CountDownLatch(COUNT);
        for (int i = 0; i < COUNT; ++i)
        {
            Thread thread = new Thread("worker thread " + i)
            {
                public void run()
                {
                    System.out.println("子线程开始执行：" + Thread.currentThread().getName());
                    // do something
                    System.out.println("子线程完成执行：" + Thread.currentThread().getName());

                    // 计数器减1
                    completeLatch.countDown();
                }
            };
            thread.start();
        }

        System.out.println("主线程开始等待。。");
        // 计数减为0时，继续向下执行
        completeLatch.await();
        System.out.println("主线程完成等待。。");
    }
}
