package com.xdc.basic.api.reflection.proxy.dynamicproxy.aop.demo1;

public interface Hello
{
    @Enhancement
    public void sayHello(String name);

    public void sayHi(String name);
}
