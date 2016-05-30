package com.xdc.basic.api.apache.commons.chain.framwork.process;

import java.util.concurrent.ThreadFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xdc.basic.api.apache.commons.chain.framwork.api.ExecuteResultHandler;
import com.xdc.basic.api.apache.commons.chain.framwork.api.ExecuteResultHandlerHolder;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;
import com.xdc.basic.api.apache.commons.chain.framwork.queue.ResponseQueue;

public class ResponseProcess extends MessageProcess
{
    private static Log log = LogFactory.getLog(ResponseProcess.class);

    @Override
    public void handle()
    {
        Response response = ResponseQueue.getInstance().take();
        ExecuteResultHandler executeResultHandler = ExecuteResultHandlerHolder.getInstance()
                .get(response.getRequestId());

        if (executeResultHandler == null)
        {
            log.info("Found no ProcessResultHandler for response: " + response);
            return;
        }

        executeResultHandler.onProcessComplete(response);
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
                t.setName("ResponseProcess-");
                return t;
            }
        };
    }
}
