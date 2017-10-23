package com.xdc.basic.api.thread.lockfree.counter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 这是由硬件提供原子操作指令实现的。在非激烈竞争的情况下，开销更小，速度更快。Java.util.concurrent中实现的原子操作类包括：
 * AtomicBoolean、AtomicInteger、AtomicLong、AtomicReference
 * 
 * @author xdc
 * 
 */
public class CounterAtomicInteger
{
    private AtomicInteger count = new AtomicInteger();

    public void increment()
    {
        count.incrementAndGet();
    }

    public int getCount()
    {
        return count.get();
    }
}
