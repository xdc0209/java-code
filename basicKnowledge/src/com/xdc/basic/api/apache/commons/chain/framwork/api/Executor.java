package com.xdc.basic.api.apache.commons.chain.framwork.api;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.api.apache.commons.chain.framwork.message.Request;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;
import com.xdc.basic.api.apache.commons.chain.framwork.queue.RequestQueue;

public class Executor
{
    private static final long TIME_OUT_MS               = 50 * 1000;

    private static final long EXECUTE_TIME_OUT_ERR_CODE = 888000;

    public Response execute(Request request)
    {
        if (request == null)
        {
            return null;
        }

        ExecuteResultHandler resultHandler = new ExecuteResultHandler();
        execute(request, resultHandler);

        resultHandler.waitFor(TIME_OUT_MS);

        Response response = null;
        if (!resultHandler.hasResult())
        {
            response = new Response();
            response.setCode(EXECUTE_TIME_OUT_ERR_CODE);
            return response;
        }

        response = resultHandler.getResponse();
        return response;
    }

    public void execute(Request request, ExecuteResultHandler resultHandler)
    {
        if (request == null || resultHandler == null)
        {
            return;
        }

        if (StringUtils.isEmpty(request.getRequestId()))
        {
            request.setRequestId(UUID.randomUUID().toString());
        }

        ExecuteResultHandlerHolder.getInstance().put(request.getRequestId(), resultHandler);
        RequestQueue.getInstance().put(request);
    }
}
