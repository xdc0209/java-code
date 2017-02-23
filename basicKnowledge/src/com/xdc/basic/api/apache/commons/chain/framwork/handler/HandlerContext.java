package com.xdc.basic.api.apache.commons.chain.framwork.handler;

import org.apache.commons.chain.impl.ContextBase;

import com.xdc.basic.api.apache.commons.chain.framwork.message.Request;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;

public class HandlerContext extends ContextBase
{
    private static final long serialVersionUID = -1497653023541268951L;

    private Request           request;

    private Response          response;

    public HandlerContext(Request request)
    {
        super();
        this.request = request;
    }

    public HandlerContext(Request request, Response response)
    {
        super();
        this.request = request;
        this.response = response;
    }

    public Request getRequest()
    {
        return request;
    }

    public void setRequest(Request request)
    {
        this.request = request;
    }

    public Response getResponse()
    {
        return response;
    }

    public void setResponse(Response response)
    {
        this.response = response;
    }
}
