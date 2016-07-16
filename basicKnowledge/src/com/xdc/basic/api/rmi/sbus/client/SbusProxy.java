package com.xdc.basic.api.rmi.sbus.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;

import com.xdc.basic.api.rmi.sbus.message.SbusInvokeInfo;
import com.xdc.basic.api.rmi.sbus.message.SbusInvokeResult;

public class SbusProxy implements InvocationHandler
{
    private SbusClient sbusClient = null;

    public SbusProxy(SbusClient sbusClient)
    {
        super();
        this.sbusClient = sbusClient;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        SbusInvokeInfo invokeInfo = new SbusInvokeInfo(method, args);

        SbusInvokeResult invokeResult = sbusClient.invoke(invokeInfo);

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
