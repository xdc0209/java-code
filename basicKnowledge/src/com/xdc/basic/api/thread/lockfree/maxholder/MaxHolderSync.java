package com.xdc.basic.api.thread.lockfree.maxholder;

public class MaxHolderSync
{
    private volatile int max = 0;

    public synchronized void set(int value)
    {
        if (value > max)
        {
            max = value;
        }
    }

    public int getMax()
    {
        return max;
    }
}
