package com.xdc.basic.api.thread.basic.implrunable;

class RunnableImpl implements Runnable
{
    int i = 100;

    @Override
    public void run()
    {
        while (true)
        {
            System.out.println(Thread.currentThread().getName() + "-->" + i);
            i--;
            Thread.yield();
            if (i < 0)
            {
                break;
            }
        }
    }
}
