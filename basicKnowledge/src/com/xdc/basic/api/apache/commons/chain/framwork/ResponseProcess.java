package com.xdc.basic.api.apache.commons.chain.framwork;

import java.util.concurrent.ThreadFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;
import com.xdc.basic.api.apache.commons.chain.framwork.process.ProcessResultHandler;
import com.xdc.basic.api.apache.commons.chain.framwork.process.ProcessResultHandlerCache;
import com.xdc.basic.api.apache.commons.chain.framwork.queue.ResponseQueue;

public class ResponseProcess extends AbstractMessageProcess
{
    private static Log log = LogFactory.getLog(ResponseProcess.class);

    @Override
    public void handle()
    {
        Response response = ResponseQueue.getInstance().take();
        ProcessResultHandler processResultHandler = ProcessResultHandlerCache.get(response.getRequestId());

        if (processResultHandler == null)
        {
            log.info("Found no ProcessResultHandler for response: " + response);
            System.out.println("Found no ProcessResultHandler for response: " + response);
            return;
        }

        processResultHandler.setResponse(response);
        processResultHandler.hadResult();
    }

    @Override
    public ThreadFactory getThreadFactory()
    {
        return new ThreadFactory()
        {
            @Override
            public Thread newThread(Runnable r)
            {
                Thread t = new Thread(r);
                t.setName("ResponseProcess");
                return t;
            }
        };
    }
}
