package com.xdc.basic.api.thread.synclock.demo2;

public class Person
{
    public void listenMusic()
    {
        System.out.println("--------------------------------- I'm listening music.");
        sleepHalfSecond();
    }

    public synchronized void study()
    {
        System.out.println("I will start to study.");
        sleep1Second();

        for (int i = 0; i < 5; i++)
        {
            System.out.println("+++++++++++++++++++++++++++++++++ I'm studying.");
            sleep1Second();
        }

        System.out.println("I have finished my study.");
        sleep1Second();
    }

    public synchronized void sleep()
    {
        System.out.println("I will start to sleep.");
        sleep1Second();

        for (int i = 0; i < 5; i++)
        {
            System.out.println("================================= I'm sleepping. zzZ...");
            sleep1Second();
        }

        System.out.println("I have finished my sleep.");
        sleep1Second();
    }

    public void playFootball()
    {
        System.out.println("I will start to play football.");
        sleep1Second();

        synchronized (this)
        {
            for (int i = 0; i < 5; i++)
            {
                System.out.println("********************************* I'm playing football.");
                sleep1Second();
            }
        }

        System.out.println("I have finished playing football.");
        sleep1Second();
    }

    public void playBasketball()
    {
        System.out.println("I will start to play basketball.");
        sleep1Second();

        synchronized (this)
        {
            for (int i = 0; i < 5; i++)
            {
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& I am playing basketball.");
                sleep1Second();
            }
        }

        System.out.println("I have finished playing basketball.");
        sleep1Second();
    }

    private void sleep1Second()
    {
        try
        {
            Thread.sleep(1000L);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void sleepHalfSecond()
    {
        try
        {
            Thread.sleep(500L);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    // Ps: synchronized void f() { /* body */ }
    // 和 void f() { synchronized(this) { /* body */ } }
    // 是完全等价的。
}
