package com.xdc.basic.api.reflection.proxy.dynamicproxy.aop.demo1;

public class HelloEnglish implements Hello
{
    public void sayHello(String name)
    {
        System.out.println("Hello, " + name);
    }

    public void sayHi(String name)
    {
        System.out.println("hi, " + name);
    }
}
