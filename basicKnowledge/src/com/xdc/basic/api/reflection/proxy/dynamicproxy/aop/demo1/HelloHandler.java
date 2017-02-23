package com.xdc.basic.api.reflection.proxy.dynamicproxy.aop.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HelloHandler implements InvocationHandler
{
    /**
     * 需要进行代理的实例
     */
    private Hello  hello  = null;

    /**
     * 前置增强
     */
    private Before before = null;

    /**
     * 后置增强
     */
    private After  after  = null;

    /**
     * InvocationHandler 接口的实现方法，进行动态代理
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        // 看看接口中方法是否标注了需要 Enhancement, 有的话需要增强
        boolean needEnhancement = method.isAnnotationPresent(Enhancement.class);
        if (!needEnhancement)
        {
            // 没有标注的话，按原方法执行
            return method.invoke(hello, args);
        }

        // 有标注的话，进行方法的前置和后置增强
        if (before != null)
        {
            before.before();
        }
        Object obj = method.invoke(hello, args);
        if (after != null)
        {
            after.after();
        }

        return obj;
    }

    /**
     * 将传入的 Hello 与 InvocationHandler 进行绑定，以获得代理类的实例
     * 
     * @param hello
     * @return
     */
    public Hello bind(Hello hello)
    {
        this.hello = hello;
        Hello helloProxy = (Hello) Proxy.newProxyInstance(hello.getClass().getClassLoader(),
                hello.getClass().getInterfaces(), this);
        return helloProxy;
    }

    public void setAfter(After after)
    {
        this.after = after;
    }

    public void setBefore(Before before)
    {
        this.before = before;
    }
}
