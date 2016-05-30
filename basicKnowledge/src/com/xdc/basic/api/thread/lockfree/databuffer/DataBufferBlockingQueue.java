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
    // BlockingQueue提供的常用方法：
    // 操作..可能报异常..返回布尔值..可能阻塞..设定等待时间
    // 入队..add(e).....offer(e)...put(e)...offer(e, timeout, unit)
    // 出队..remove()...poll().....take()...poll(timeout, unit)
    // 查看..element()..peek().....无.......无
    //
    // 从上表可以很明显看出每个方法的作用，这个不用多说。强调下：
    // add(e) remove() element() 方法不会阻塞线程。当不满足约束条件时，会抛出IllegalStateException 异常。例如：当队列被元素填满后，再调用add(e)，则会抛出异常。
    // offer(e) poll() peek() 方法即不会阻塞线程，也不会抛出异常。例如：当队列被元素填满后，再调用offer(e)，则不会插入元素，函数返回false。
    // 要想要实现阻塞功能，需要调用put(e) take() 方法。当不满足约束条件时，会阻塞线程。

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