package com.xdc.basic.api.apache.commons.chain.framwork;

import java.util.concurrent.Executors;

import com.xdc.basic.api.apache.commons.chain.framwork.message.instance.CreateInstanceRequest;
import com.xdc.basic.api.apache.commons.chain.framwork.message.instance.DeleteInstanceRequest;
import com.xdc.basic.api.apache.commons.chain.framwork.queue.RequestQueue;

public class Main
{
    public static void main(String[] args)
    {
        RequestProcess requestProcess = new RequestProcess();
        ResponseProcess responseProcess = new ResponseProcess();

        Executors.newSingleThreadExecutor(requestProcess.getThreadFactory()).execute(requestProcess);
        Executors.newSingleThreadExecutor(responseProcess.getThreadFactory()).execute(responseProcess);

        RequestQueue.getInstance().put(new CreateInstanceRequest());
        RequestQueue.getInstance().put(new DeleteInstanceRequest());
        System.out.println();
    }
}
