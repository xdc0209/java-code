package com.xdc.basic.api.thread.flowcontrol;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环障碍(循环屏障)，可以用于性能测试
 * 
 * @author xdc
 * 
 */
class CyclicBarrierTest
{
    public static void main(String[] args)
    {
        int threadCount = 3;
        final int loopCount = 10;

        final CyclicBarrier barrier = new CyclicBarrier(threadCount, new Runnable()
        {
            @Override
            public void run()
            {
                // 等待所有子线程完成
                collectTestResult();
            }
        });

        for (int i = 0; i < threadCount; ++i)
        {
            Thread thread = new Thread("test-thread " + i)
            {
                @Override
                public void run()
                {
                    for (int j = 0; j < loopCount; ++j)
                    {
                        doTest();
                        try
                        {
                            // 通知barrier已经完成
                            System.out.println("子线程已完成:" + Thread.currentThread().getName());
                            barrier.await();
                        }
                        catch (InterruptedException e)
                        {
                            return;
                        }
                        catch (BrokenBarrierException e)
                        {
                            return;
                        }
                    }
                }
            };
            thread.start();
        }

    }

    private static void doTest()
    {
        System.out.println("PerformaceTest.doTest()");
        /* do xxx */
    }

    private static void collectTestResult()
    {
        System.out.println("PerformaceTest.collectTestResult()");
        /* do xxx */
    }
}
