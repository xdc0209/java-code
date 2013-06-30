package com.xdc.basic.apidemo.concurrent.blockingqueue;

import java.util.concurrent.BlockingQueue;

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