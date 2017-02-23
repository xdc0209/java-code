package com.xdc.basic.api.reflection.proxy.dynamicproxy.aop.demo2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxyFactory implements InvocationHandler
{
    private Object targetObject;

    public Object createProxyInstance(Object targetObject)
    {
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        BankServiceImpl bankServiceImpl = (BankServiceImpl) targetObject;
        Object result = null;
        if (bankServiceImpl.getUser() != null)
        {
            // 用户名不为空，说明有权限
            result = method.invoke(targetObject, args);
        }
        else
        {
            System.out.println("用户不能为空.");
        }
        return result;
    }
}