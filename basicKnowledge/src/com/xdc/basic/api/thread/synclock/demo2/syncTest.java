package com.xdc.basic.api.thread.synclock.demo2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class syncTest
{

    public static void main(String[] args)
    {
        ExecutorService pool = Executors.newCachedThreadPool();

        final Person person = new Person();

        pool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    person.listenMusic();
                }
            }
        });

        pool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    person.study();
                }
            }
        });

        pool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    person.sleep();
                }
            }
        });

        pool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    person.playFootball();
                }
            }
        });

        pool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    person.playBasketball();
                }
            }
        });
    }

}
