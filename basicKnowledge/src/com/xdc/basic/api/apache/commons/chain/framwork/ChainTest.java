package com.xdc.basic.api.apache.commons.chain.framwork;

import java.util.concurrent.Executors;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.xdc.basic.api.apache.commons.chain.framwork.api.ExecuteResultHandler;
import com.xdc.basic.api.apache.commons.chain.framwork.api.Executor;
import com.xdc.basic.api.apache.commons.chain.framwork.message.Response;
import com.xdc.basic.api.apache.commons.chain.framwork.message.instance.CreateInstanceRequest;
import com.xdc.basic.api.apache.commons.chain.framwork.message.instance.DeleteInstanceRequest;
import com.xdc.basic.api.apache.commons.chain.framwork.process.RequestProcess;
import com.xdc.basic.api.apache.commons.chain.framwork.process.ResponseProcess;

public class ChainTest
{
    public static void main(String[] args)
    {
        RequestProcess requestProcess = new RequestProcess();
        ResponseProcess responseProcess = new ResponseProcess();

        Executors.newSingleThreadExecutor(requestProcess.getThreadFactory()).execute(requestProcess);
        Executors.newSingleThreadExecutor(responseProcess.getThreadFactory()).execute(responseProcess);

        // 同步调用
        Executor executor = new Executor();
        Response createInstanceResponse = executor.execute(new CreateInstanceRequest());
        System.out.println(ToStringBuilder.reflectionToString(createInstanceResponse));

        // 异步调用
        ExecuteResultHandler resultHandler = new ExecuteResultHandler();
        executor.execute(new DeleteInstanceRequest(), resultHandler);
        resultHandler.waitFor(500 * 1000);
        Response deleteInstanceResponse = null;
        if (!resultHandler.hasResult())
        {
            final long EXECUTE_TIME_OUT_ERR_CODE = 888000;
            deleteInstanceResponse = new Response();
            deleteInstanceResponse.setCode(EXECUTE_TIME_OUT_ERR_CODE);
        }
        else
        {
            deleteInstanceResponse = resultHandler.getResponse();
        }
        System.out.println(ToStringBuilder.reflectionToString(deleteInstanceResponse));
    }
}
