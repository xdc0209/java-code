package com.xdc.basic.api.apache.commons.chain.framwork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;
import com.xdc.basic.api.apache.commons.chain.framwork.queue.ResponseQueue;

public class ResposeProcess extends AbstractMessageProcess
{
    private static Log log = LogFactory.getLog(ResposeProcess.class);

    @Override
    public void handle()
    {
        Response response = ResponseQueue.getInstance().take();

        log.info(response);
        System.out.println(response);
    }
}
