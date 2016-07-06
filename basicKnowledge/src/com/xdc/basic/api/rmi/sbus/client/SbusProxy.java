package com.xdc.basic.api.rmi.sbus.client;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;

import com.xdc.basic.api.rmi.sbus.core.InvokeInfo;
import com.xdc.basic.api.rmi.sbus.core.InvokeResult;
import com.xdc.basic.skills.GetPath;

class SbusProxy implements InvocationHandler
{
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        InvokeInfo invokeInfo = new InvokeInfo(method, args);

        String request = GetPath.connect(GetPath.getRelativePath(), "/../request.txt");
        byte[] invokeInfoBytes = SerializationUtils.serialize(invokeInfo);
        FileUtils.writeByteArrayToFile(new File(request), invokeInfoBytes);

        String response = GetPath.connect(GetPath.getRelativePath(), "/../response.txt");
        byte[] invokeResultBytes = null;
        for (int i = 0; i < 100; i++)
        {
            Thread.sleep(1000);
            invokeResultBytes = FileUtils.readFileToByteArray(new File(response));
            if (invokeResultBytes != null)
            {
                break;
            }
        }

        InvokeResult invokeResult = (InvokeResult) SerializationUtils.deserialize(invokeResultBytes);

        if (invokeResult.getThrowable() != null)
        {
            if (invokeResult.getThrowable() instanceof Throwable)
            {
                StackTraceElement[] serverStackTrace = invokeResult.getThrowable().getStackTrace();
                StackTraceElement[] clientStackTrace = new Throwable().getStackTrace();
                StackTraceElement[] stackTrace = ArrayUtils.addAll(serverStackTrace, clientStackTrace);
                invokeResult.getThrowable().setStackTrace(stackTrace);

                throw invokeResult.getThrowable();
            }
        }

        return invokeResult.getResult();
    }
}
