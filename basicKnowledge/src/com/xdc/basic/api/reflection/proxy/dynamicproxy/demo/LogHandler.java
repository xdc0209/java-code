package com.xdc.basic.api.reflection.proxy.dynamicproxy.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogHandler implements InvocationHandler
{
    private Object targetObject;

    public LogHandler(Object obj)
    {
        this.targetObject = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        doBefore();
        Object result = method.invoke(targetObject, args);
        doAfter();
        return result;
    }

    private void doBefore()
    {
        System.out.println("log: 要开始执行了！");
    }

    private void doAfter()
    {
        System.out.println("log: 已经执行完了！");
    }
}
