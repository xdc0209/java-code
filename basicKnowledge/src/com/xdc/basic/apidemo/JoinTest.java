package com.xdc.basic.apidemo;

import java.io.IOException;

public class JoinTest
{
    public static void main(String[] args)
    {
        // 启动一线程，等待控制台输入，使用join()方法来暂停当前线程，直到其他线程调用
        Thread t = new Thread()
        {
            public void run()
            {
                System.out.println("Reading");
                try
                {
                    System.in.read();
                }
                catch (IOException e)
                {
                    System.err.println(e);
                }
                System.out.println("Thread finished.");
            }
        };
        System.out.println("Starting");
        t.start();
        System.out.println("Joining");
        try
        {
            t.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("Who dares imterrupt my sleep?");
        }
        System.out.println("Main finished.");
    }
}
