package com.xdc.basic.api.apache.commons.chain.framwork.handler.instance;

import com.xdc.basic.api.apache.commons.chain.framwork.handler.Handler;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Request;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;
import com.xdc.basic.api.apache.commons.chain.framwork.message.instance.CustomizeInstanceResponse;

public class CustomizeInstanceHandler extends Handler
{
    @Override
    protected Response handler(Request request)
    {
        System.out.println("CustomizeInstanceHandler.handler()");
        return new CustomizeInstanceResponse();
    }
}
