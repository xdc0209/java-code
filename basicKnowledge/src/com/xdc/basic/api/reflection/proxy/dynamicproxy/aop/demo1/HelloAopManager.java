package com.xdc.basic.api.reflection.proxy.dynamicproxy.aop.demo1;

public class HelloAopManager
{
    private HelloAopManager()
    {
    }

    public static Hello getHelloProxy(Hello hello, Before before)
    {
        return getHelloProxy(hello, before, null);
    }

    public static Hello getHelloProxy(Hello hello, After after)
    {
        return getHelloProxy(hello, null, after);
    }

    public static Hello getHelloProxy(Hello hello, Before before, After after)
    {
        HelloHandler handler = new HelloHandler();
        if (before != null)
        {
            handler.setBefore(before);
        }
        if (after != null)
        {
            handler.setAfter(after);
        }
        return handler.bind(hello);
    }
}
