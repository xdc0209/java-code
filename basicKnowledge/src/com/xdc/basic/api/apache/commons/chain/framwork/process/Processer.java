package com.xdc.basic.api.apache.commons.chain.framwork.process;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.apache.commons.chain.framwork.message.Request;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;
import com.xdc.basic.api.apache.commons.chain.framwork.queue.RequestQueue;

public class Processer
{
    public Response process(Request request)
    {
        if (request == null)
        {
            return null;
        }

        ProcessResultHandler resultHandler = new ProcessResultHandler();
        process(request, resultHandler);

        resultHandler.waitFor();
        return resultHandler.getResponse();
    }

    public void process(Request request, ProcessResultHandler resultHandler)
    {
        if (request == null || resultHandler == null)
        {
            return;
        }

        if (StringUtils.isEmpty(request.getRequestId()))
        {
            request.setRequestId(UUID.randomUUID().toString());
        }

        ProcessResultHandlerCache.put(request.getRequestId(), resultHandler);
        RequestQueue.getInstance().put(request);
    }
}
