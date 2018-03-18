package com.xdc.basic.book.effectivejava.Chapter10.Item66.fixedstopthread1;

// Properly synchronized cooperative thread termination - Page 261

import java.util.concurrent.TimeUnit;

public class StopThread
{
    private static boolean stopRequested;

    private static synchronized void requestStop()
    {
        stopRequested = true;
    }

    private static synchronized boolean stopRequested()
    {
        return stopRequested;
    }

    public static void main(String[] args) throws InterruptedException
    {
        Thread backgroundThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                @SuppressWarnings("unused")
                int i = 0;
                while (!stopRequested())
                {
                    i++;
                }
            }
        });
        backgroundThread.start();

        TimeUnit.SECONDS.sleep(1);
        requestStop();
    }
}
