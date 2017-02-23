package com.xdc.basic.api.thread.lockfree.maxholder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * LockFree算法，不需要加锁。通常都是三个部分组成： ①循环 ②CAS (CompareAndSet) ③回退
 * 
 * @author xdc
 * 
 */
public class MaxHolderCAS
{
    private AtomicInteger max = new AtomicInteger();

    public void set(int value)
    {
        for (;;)
        {
            int current = max.get();
            if (value > current)
            {
                if (max.compareAndSet(current, value))
                {
                    break;
                }
                else
                {
                    continue;
                }
            }
            else
            {
                break;
            }
        }
    }

    public int getMax()
    {
        return max.get();
    }
}
