package com.xdc.basic.api.concurrent.blockingqueue;

import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang3.RandomStringUtils;

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