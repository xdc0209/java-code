package com.xdc.basic.api.apache.commons.chain.framwork.handler.cluser;

import com.xdc.basic.api.apache.commons.chain.framwork.handler.Handler;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Request;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;
import com.xdc.basic.api.apache.commons.chain.framwork.message.cluser.CreateClusterResponse;

public class CreateClusterHandler extends Handler
{
    @Override
    protected Response handler(Request request)
    {
        System.out.println("CreateClusterHandler.handler()");

        return new CreateClusterResponse();
    }
}
