package com.xdc.basic.book.effectivejava.Chapter10.Item66.brokenstopthread;

// Broken! - How long would you expect this program to run? - Page 259

import java.util.concurrent.TimeUnit;

public class StopThread
{
    private static boolean stopRequested;

    public static void main(String[] args) throws InterruptedException
    {
        Thread backgroundThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                @SuppressWarnings("unused")
                int i = 0;
                while (!stopRequested)
                {
                    i++;
                }
            }
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
