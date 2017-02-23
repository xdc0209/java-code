package com.xdc.basic.api.apache.commons.chain.framwork.api;

import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;

public class ExecuteResultHandler
{
    private static final int  SLEEP_TIME_MS = 50;

    private volatile boolean  hasResult     = false;

    private volatile Response response      = null;

    public void onProcessComplete(Response response)
    {
        this.response = response;
        this.hasResult = true;
    }

    public boolean hasResult()
    {
        return hasResult;
    }

    public Response getResponse()
    {
        if (!hasResult)
        {
            throw new IllegalStateException("The process has not exited yet therefore no result is available ...");
        }

        return response;
    }

    public void waitFor()
    {
        while (!hasResult())
        {
            try
            {
                Thread.sleep(SLEEP_TIME_MS);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void waitFor(long timeout)
    {
        long until = System.currentTimeMillis() + timeout;

        while (!hasResult() && (System.currentTimeMillis() < until))
        {
            try
            {
                Thread.sleep(SLEEP_TIME_MS);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
