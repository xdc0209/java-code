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
        ExecutorService pool = Executors.newCachedThreadPool();

        final BankService bankService = new BankService();

        List<Future<?>> futures = new ArrayList<Future<?>>();
        for (int i = 0; i < 1000; i++)
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
