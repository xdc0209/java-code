package com.xdc.basic.api.jvm.test.stub;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeapDmupTestStub
{
    private static final Logger logger = LoggerFactory.getLogger(HeapDmupTestStub.class);

    public void init()
    {
        logger.info("Init HeapDmupTestStub start.");

        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    logger.error("HeapDmupTestStub start.");

                    Thread.sleep(1000 * 60 * 2);

                    StringBuilder sb = new StringBuilder();
                    for (long i = 0; i < 100000l; i++)
                    {
                        sb.append("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                    }

                    List<String> strings = new ArrayList<String>();
                    for (long i = 0; i < Long.MAX_VALUE; i++)
                    {
                        strings.add(sb.toString() + "-" + i);
                    }

                    logger.error("HeapDmupTestStub finish.");
                }
                catch (Exception e)
                {
                    logger.error("HeapDmupTestStub failed.", e);
                }
            }
        });
        t.start();

        logger.info("Init HeapDmupTestStub finish.");
    }

}