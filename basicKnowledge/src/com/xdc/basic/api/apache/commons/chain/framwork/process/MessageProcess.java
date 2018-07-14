package com.xdc.basic.api.apache.commons.chain.framwork.process;

import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MessageProcess implements Runnable
{
    private static Logger log       = LoggerFactory.getLogger(MessageProcess.class);

    private boolean       isRunning = true;

    public boolean isRunning()
    {
        return this.isRunning;
    }

    public void stop()
    {
        this.isRunning = false;
    }

    @Override
    public void run()
    {
        while (isRunning())
        {
            try
            {
                handle();
            }
            catch (Exception e)
            {
                log.error("Handle message fail.", e);
            }
        }
    }

    public abstract void handle();

    public abstract ThreadFactory getThreadFactory();
}
