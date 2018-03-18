package com.xdc.basic.api.thread.interrupt;

public class InterruptTest
{
    public static void main(String[] args)
    {
        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("执行run方法");
                try
                {
                    Thread.sleep(10000);
                    System.out.println("线程完成休眠");
                }
                catch (InterruptedException e)
                {
                    System.out.println("休眠被打断");
                    return; // 返回到程序的调用处
                }
                System.out.println("线程正常终止");
            }
        };

        t.start();

        try
        {
            Thread.sleep(2000L);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        t.interrupt(); // 2s后中断线程
    }
}
