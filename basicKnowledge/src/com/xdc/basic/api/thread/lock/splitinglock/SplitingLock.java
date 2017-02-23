package com.xdc.basic.api.thread.lock.splitinglock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 分拆锁, 根据资源id，通过维护相应的锁，实现更细化的控制，资源之间不会干扰
 * 
 * @author xdc
 * 
 */
public class SplitingLock<T>
{
    private Map<T, CountLock> locks = new HashMap<T, CountLock>();

    void lock(T key)
    {
        CountLock lock = null;
        synchronized (locks)
        {
            lock = locks.get(key);
            if (lock == null)
            {
                lock = new CountLock();
                locks.put(key, lock);
            }
            lock.increaseCount();
        }

        lock.getLock().lock();
    }

    void unlock(T key)
    {
        CountLock lock = locks.get(key);
        if (lock == null)
        {
            return;
        }
        lock.getLock().unlock();

        synchronized (locks)
        {
            lock.decreaseCount();
            if (lock.getCount() == 0)
            {
                locks.remove(key);
            }
        }
    }

    class CountLock
    {
        private Lock         lock  = new ReentrantLock();

        private volatile int count = 0;

        public Lock getLock()
        {
            return lock;
        }

        public int getCount()
        {
            return count;
        }

        void increaseCount()
        {
            this.count++;
        }

        void decreaseCount()
        {
            this.count--;
        }
    }
}
