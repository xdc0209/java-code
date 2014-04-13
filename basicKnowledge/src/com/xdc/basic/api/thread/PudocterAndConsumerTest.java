package com.xdc.basic.api.thread;

class CubbyHole
{
    private Integer data = null;

    public synchronized int get()
    {
        if (data == null)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        notifyAll();
        System.out.println("Consumer " + "got: " + data);

        Integer result = data;
        data = null;

        return result;
    }

    public synchronized void put(int value)
    {
        if (data != null)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        data = value;
        notifyAll();

        System.out.println("Producer " + "put: " + data);
    }
}

class Producer extends Thread
{
    private CubbyHole cubbyhole;

    public Producer(CubbyHole c)
    {
        cubbyhole = c;
    }

    public void run()
    {
        for (int i = 0; i < 10; i++)
        {
            cubbyhole.put(i);
            try
            {
                sleep((int) Math.random() * 1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread
{
    private CubbyHole cubbyhole;

    public Consumer(CubbyHole c)
    {
        cubbyhole = c;
    }

    public void run()
    {
        for (int i = 0; i < 10; i++)
        {
            cubbyhole.get();
        }
    }
}

public class PudocterAndConsumerTest
{
    public static void main(String[] args)
    {
        CubbyHole h = new CubbyHole();
        Producer producer = new Producer(h);
        Consumer consumer = new Consumer(h);
        producer.start();
        consumer.start();
    }
}
