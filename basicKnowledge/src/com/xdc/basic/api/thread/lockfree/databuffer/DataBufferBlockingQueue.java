package com.xdc.basic.api.thread.lockfree.databuffer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * http://www.fengfly.com/plus/view-191064-1.html
 * 
 * @author xdc
 * 
 */
public class DataBufferBlockingQueue
{
    public static void main(String[] args)
    {
        BlockingQueue<String> dataQueue = new ArrayBlockingQueue<String>(1, true);

        ExecutorService pool = Executors.newCachedThreadPool();
        pool.submit(new Producer(dataQueue));
        pool.submit(new Consumer(dataQueue));

        // 执行后关闭线程池
        pool.shutdown();
    }
}

class Producer implements Runnable
{
    private BlockingQueue<String> dataQueue;

    public Producer(BlockingQueue<String> dataQueue)
    {
        this.dataQueue = dataQueue;
    }

    public void run()
    {
        try
        {
            for (int i = 0; i < 10; i++)
            {
                String data = RandomStringUtils.randomAlphanumeric(6);
                dataQueue.put(data);
                System.out.println(data + " is putted!");
            }

            dataQueue.put("END");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable
{
    private BlockingQueue<String> dataQueue;

    public Consumer(BlockingQueue<String> dataQueue)
    {
        this.dataQueue = dataQueue;
    }

    public void run()
    {
        String msg = null;
        try
        {
            while (!((msg = dataQueue.take()).equals("END")))
                System.out.println(msg + " is token!");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}