package com.xdc.basic.api.rmi.sbus.client;

import java.lang.reflect.Proxy;

import com.xdc.basic.api.rmi.sbus.transport.MessageClient;

public class SbusProxyManager
{
    public static <T> T getSbusProxy(Class<T> api, MessageClient messageClient)
    {
        SbusClient sbusClient = new SbusClient(messageClient);

        @SuppressWarnings("unchecked")
        T proxy = (T) Proxy.newProxyInstance(api.getClassLoader(), new Class<?>[] { api }, new SbusProxy(sbusClient));
        return proxy;
    }
}