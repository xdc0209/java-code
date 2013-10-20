package com.xdc.basic.api.concurrent.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * http://www.fengfly.com/plus/view-191064-1.html
 * 
 * @author xdc
 * 
 */
public class BlockingQueueTest
{
    public static void main(String[] args)
    {
        BlockingQueue<String> dataQueue = new ArrayBlockingQueue<>(1, true);

        ExecutorService pool = Executors.newCachedThreadPool();
        pool.submit(new Producer(dataQueue));
        pool.submit(new Consumer(dataQueue));
    }
}
