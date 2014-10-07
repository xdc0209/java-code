package com.xdc.basic.api.reflection.proxy.comparison.dynamicproxyjdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class BusinessImplJdkProxy implements InvocationHandler
{
    private Object targetObject;

    public BusinessImplJdkProxy()
    {
        super();
    }

    public BusinessImplJdkProxy(Object obj)
    {
        super();
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
        System.out.println("前置处理！");
    }

    private void doAfter()
    {
        System.out.println("后置处理！");
    }

    public static Object factory(Object obj)
    {
        Class<?> clazz = obj.getClass();
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] interfaces = clazz.getInterfaces();

        // 取得代理对象, 要绑定到接口(这是jdk动态代理的一个缺陷，cglib动态代理弥补了这一缺陷) 
        return Proxy.newProxyInstance(classLoader, interfaces, new BusinessImplJdkProxy(obj));
    }
}