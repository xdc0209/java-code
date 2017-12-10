package com.xdc.basic.api.apache.commons.chain.framwork.process;

import java.util.concurrent.ThreadFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class MessageProcess implements Runnable
{
    private static Log log       = LogFactory.getLog(MessageProcess.class);

    private boolean    isRunning = true;

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
                log.error(e);
            }
        }
    }

    public abstract void handle();

    public abstract ThreadFactory getThreadFactory();
}
