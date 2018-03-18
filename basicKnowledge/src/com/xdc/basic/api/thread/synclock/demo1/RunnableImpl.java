package com.xdc.basic.api.thread.synclock.demo1;

class RunnableImpl implements Runnable
{
    // 火车票张数
    int i = 100000;

    @Override
    public void run()
    {
        while (true)
        {
            synchronized (this)
            {
                // 如果张数小于等于0，就没有了，不买了
                if (i <= 0)
                {
                    break;
                }
                System.out.println(Thread.currentThread().getName() + "买到了一张火车票，编号：" + i);
                i--;
                Thread.yield();
            }
        }
    }
}
