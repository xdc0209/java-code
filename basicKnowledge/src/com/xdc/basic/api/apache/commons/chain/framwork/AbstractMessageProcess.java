package com.xdc.basic.api.apache.commons.chain.framwork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractMessageProcess implements Runnable
{
    private static Log log       = LogFactory.getLog(AbstractMessageProcess.class);

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
}