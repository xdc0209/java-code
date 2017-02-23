package com.xdc.basic.api.reflection.proxy.dynamicproxy.demo;

import java.lang.reflect.Proxy;

public class DynamicProxyTest
{
    public static void main(String[] args)
    {
        HelloImpl helloImpl = new HelloImpl();
        LogHandler handler = new LogHandler(helloImpl);

        // 这里把handler与impl新生成的代理类相关联
        Hello hello = (Hello) Proxy.newProxyInstance(helloImpl.getClass().getClassLoader(),
                helloImpl.getClass().getInterfaces(), handler);

        // 这里无论访问哪个方法，都是会把请求转发到handler.invoke
        hello.sayHello("Denny");
        hello.sayHi("Tom");
    }
}
