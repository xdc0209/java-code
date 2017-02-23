package com.xdc.basic.api.reflection.proxy.dynamicproxy.aop.demo1;

public class HelloChinese implements Hello
{
    public void sayHello(String name)
    {
        System.out.println(name + "，您好");
    }

    public void sayHi(String name)
    {
        System.out.println("哈啰，" + name);
    }
}
