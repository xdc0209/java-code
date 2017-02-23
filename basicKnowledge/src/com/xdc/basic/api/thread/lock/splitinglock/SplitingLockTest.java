package com.xdc.basic.api.thread.lock.splitinglock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SplitingLockTest
{
    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
        // 此处若改为使用Executors.newCachedThreadPool(),下面的其他代码保持不变，不到一会内存将被占满，机器卡死。
        // 这就能看出了两种方法的不同。
        ExecutorService pool = Executors.newFixedThreadPool(20);

        final BankService bankService = new BankService();

        List<Future<?>> futures = new ArrayList<Future<?>>();
        for (int i = 0; i < 100000; i++)
        {
            Future<?> future = pool.submit(new Runnable()
            {
                @Override
                public void run()
                {
                    bankService.deposit("xdc", 100);
                }
            });
            futures.add(future);

            future = pool.submit(new Runnable()
            {
                @Override
                public void run()
                {
                    bankService.withdrawing("xdc", 100);
                }
            });
            futures.add(future);

            future = pool.submit(new Runnable()
            {
                @Override
                public void run()
                {
                    bankService.deposit("gx", 100);
                }
            });
            futures.add(future);

            future = pool.submit(new Runnable()
            {
                @Override
                public void run()
                {
                    bankService.withdrawing("gx", 100);
                }
            });
            futures.add(future);
        }

        for (Future<?> future : futures)
        {
            future.get();
        }
        System.out.println("最终结果： " + bankService.getBank());

        pool.shutdown();
    }
}
