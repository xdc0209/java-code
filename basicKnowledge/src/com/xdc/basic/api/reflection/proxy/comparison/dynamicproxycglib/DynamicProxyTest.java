package com.xdc.basic.api.reflection.proxy.comparison.dynamicproxycglib;

public class DynamicProxyTest
{
    public static void main(String[] args)
    {
        // 不依赖接口，也无需创建BusinessFooImpl实例，cglib自动创建BusinessFooImpl的子类的实例
        BusinessFooImpl bf = (BusinessFooImpl) BusinessImplCglibProxy.factory(BusinessFooImpl.class);
        bf.foo();
        System.out.println();

        BusinessBarImpl bb = (BusinessBarImpl) BusinessImplCglibProxy.factory(BusinessBarImpl.class);
        String message = bb.bar("Hello,World");
        System.out.println(message);
    }
}
