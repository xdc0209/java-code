package com.xdc.basic.api.apache.commons.chain.framwork.handler;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.xdc.basic.api.apache.commons.chain.framwork.message.Request;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;

public abstract class Handler implements Command
{
    @Override
    public boolean execute(Context context) throws Exception
    {
        HandlerContext handlerContext = (HandlerContext) context;
        Request request = handlerContext.getRequest();

        Response response = handler(request);

        response.setRequestId(request.getRequestId());
        handlerContext.setResponse(response);
        return Command.CONTINUE_PROCESSING;
    }

    protected abstract Response handler(Request request);
}
