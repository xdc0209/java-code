package com.xdc.basic.api.btrace;

import java.util.Random;

/**
 * 简单的java程序，供Btrace监控
 */
public class HelloWorld
{
    public static void main(String[] args) throws Exception
    {
        while (true)
        {
            Random random = new Random();
            execute(random.nextInt(4000));
        }
    }

    public static void execute(int sleepTime)
    {
        System.out.println("Start to sleep, sleep time is=>" + sleepTime);
        try
        {
            Thread.sleep(sleepTime);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("End to sleep, sleep time is=>" + sleepTime);
    }
}
