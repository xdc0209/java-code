package com.xdc.basic.api.apache.commons.chain.framwork.handler.cluser;

import com.xdc.basic.api.apache.commons.chain.framwork.handler.Handler;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Request;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;
import com.xdc.basic.api.apache.commons.chain.framwork.message.cluser.DeleteClusterResponse;

public class DeleteClusterHandler extends Handler
{
    @Override
    protected Response handler(Request request)
    {
        System.out.println("DeleteClusterHandler.handler()");
        return new DeleteClusterResponse();
    }
}
