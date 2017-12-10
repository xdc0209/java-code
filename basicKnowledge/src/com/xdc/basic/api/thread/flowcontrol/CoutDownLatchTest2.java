package com.xdc.basic.api.thread.flowcontrol;

import java.util.concurrent.CountDownLatch;

/**
 * 倒数闸门, 当你启动很多线程，你需要这些线程等到通知后才真正开始，CountDownLatch也许是一个很好的选择。
 * 
 * @author xdc
 * 
 */
public class CoutDownLatchTest2
{
    public static void main(String[] args) throws InterruptedException
    {
        final CountDownLatch startLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; ++i)
        {
            Thread thread = new Thread("worker thread " + i)
            {
                @Override
                public void run()
                {
                    try
                    {
                        System.out.println("子线程开始等待主线程命令：" + Thread.currentThread().getName());
                        startLatch.await();
                        System.out.println("子线程完成等待主线程命令：" + Thread.currentThread().getName());
                    }
                    catch (InterruptedException e)
                    {
                        return;
                    }
                    // do xxxx
                }
            };
            thread.start();
        }

        // do xxx
        System.out.println("主线程马上其他线程可以继续执行了。");
        startLatch.countDown();
    }
}
