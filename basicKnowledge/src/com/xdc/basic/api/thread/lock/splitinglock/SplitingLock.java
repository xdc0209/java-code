package com.xdc.basic.api.thread.lock.splitinglock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 分拆锁
 * 
 * @author xdc
 * 
 */
public class SplitingLock<T>
{
    private ConcurrentMap<T, CountLock> locks = new ConcurrentHashMap<T, CountLock>();

    void lock(T key)
    {
        CountLock lock = getLock(key);
        lock.lock();
    }

    void unlock(T key)
    {
        CountLock lock = getLock(key);
        lock.unlock();
    }

    private CountLock getLock(T key)
    {
        CountLock lock = locks.get(key);
        if (lock == null)
        {
            locks.putIfAbsent(key, new CountLock());
            lock = locks.get(key);
        }

        return lock;
    }

    class CountLock
    {
        private Lock         lock  = new ReentrantLock();
        private volatile int count = 0;

        void lock()
        {
            count++;
            lock.lock();
        }

        void unlock()
        {
            lock.unlock();
            count--;
        }

        @Override
        public String toString()
        {
            return "CountLock [count=" + count + "]";
        }
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
