package com.xdc.basic.api.jvm.test.stub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeadlockTestStub
{
    private static final Logger logger = LoggerFactory.getLogger(DeadlockTestStub.class);

    private static Object       lock1  = new int[1];
    private static Object       lock2  = new int[1];

    public class Thread1 extends Thread
    {
        @Override
        public void run()
        {
            logger.info("thread 1 start.");
            synchronized (lock1)
            {
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                logger.info("thread 1 get lock 1 need lock 2.");
                synchronized (lock2)
                {
                    logger.info("thread 1 get lock 1 and lock 2.");
                }
            }
            logger.info("thread 1 end.");
        }
    }

    public class Thread2 extends Thread
    {
        @Override
        public void run()
        {
            logger.info("thread 2 start.");
            synchronized (lock2)
            {
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                logger.info("thread 2 get lock 2 need lock 1.");
                synchronized (lock1)
                {
                    logger.info("thread 2 get lock 2 and lock 1.");
                }
            }
            logger.info("thread 2 end.");
        }
    }

    public void init()
    {
        logger.info("Init DeadlockTestStub start.");

        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    logger.info("DeadlockTestStub start.");

                    Thread.sleep(1000 * 60 * 2);

                    Thread1 thread1 = new DeadlockTestStub().new Thread1();
                    Thread2 thread2 = new DeadlockTestStub().new Thread2();
                    thread1.start();
                    thread2.start();

                    logger.info("DeadlockTestStub finish.");
                }
                catch (Exception e)
                {
                    logger.error("DeadlockTestStub failed.", e);
                }
            }
        });
        t.start();

        logger.info("Init DeadlockTestStub finish.");
    }
}
