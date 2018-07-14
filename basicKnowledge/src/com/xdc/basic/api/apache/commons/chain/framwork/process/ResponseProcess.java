package com.xdc.basic.api.apache.commons.chain.framwork.process;

import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdc.basic.api.apache.commons.chain.framwork.api.ExecuteResultHandler;
import com.xdc.basic.api.apache.commons.chain.framwork.api.ExecuteResultHandlerHolder;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;
import com.xdc.basic.api.apache.commons.chain.framwork.queue.ResponseQueue;

public class ResponseProcess extends MessageProcess
{
    private static Logger log = LoggerFactory.getLogger(ResponseProcess.class);

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
