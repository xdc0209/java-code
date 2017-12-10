package com.xdc.basic.api.apache.commons.chain.framwork.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.xdc.basic.api.apache.commons.chain.framwork.message.Request;

public class RequestQueue implements MessageQueue<Request>
{
    private static final BlockingQueue<Request> QUEUE    = new ArrayBlockingQueue<Request>(100, true);

    private static final RequestQueue           instance = new RequestQueue();

    private RequestQueue()
    {
    }

    public static RequestQueue getInstance()
    {
        return instance;
    }

    @Override
    public void put(Request request)
    {
        try
        {
            QUEUE.put(request);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Request take()
    {
        Request request = null;
        try
        {
            request = QUEUE.take();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return request;
    }
}
