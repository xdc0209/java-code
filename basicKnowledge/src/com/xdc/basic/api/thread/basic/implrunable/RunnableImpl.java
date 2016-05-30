package com.xdc.basic.api.thread.basic.implrunable;

class RunnableImpl implements Runnable
{
    int i = 100;

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

    // 这个应该是规范的写法，如果不这么写，出错很难发现
    // public void run()
    // {
    // try
    // {
    // // dosomething
    // }
    // catch (Throwable e)
    // {
    // // log and throw
    // throw e;
    // }
    // finally
    // {
    // // do some cleaning, close io and so on
    // }
    // }
}
