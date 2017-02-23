package com.xdc.basic.api.thread.lockfree.counter;

public class CounterSync
{
    private volatile int count = 0;

    public synchronized void increment()
    {
        count++;
    }

    public int getCount()
    {
        return count;
    }
}
