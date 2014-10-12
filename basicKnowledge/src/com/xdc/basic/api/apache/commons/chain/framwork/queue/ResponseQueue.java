package com.xdc.basic.api.apache.commons.chain.framwork.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;

public class ResponseQueue implements MessageQueue<Response>
{
    private static final BlockingQueue<Response> QUEUE    = new ArrayBlockingQueue<Response>(100, true);

    private static final ResponseQueue           instance = new ResponseQueue();

    private ResponseQueue()
    {
    }

    public static ResponseQueue getInstance()
    {
        return instance;
    }

    @Override
    public void put(Response response)
    {
        try
        {
            QUEUE.put(response);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Response take()
    {
        Response response = null;
        try
        {
            response = QUEUE.take();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return response;
    }
}
