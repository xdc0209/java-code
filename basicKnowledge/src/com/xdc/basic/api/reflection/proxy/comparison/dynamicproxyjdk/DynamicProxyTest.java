package com.xdc.basic.api.reflection.proxy.comparison.dynamicproxyjdk;

public class DynamicProxyTest
{
    public static void main(String[] args)
    {
        BusinessFooImpl bfoo = new BusinessFooImpl();
        BusinessFoo bf = (BusinessFoo) BusinessImplJdkProxy.factory(bfoo);
        bf.foo();
        System.out.println();

        BusinessBarImpl bbar = new BusinessBarImpl();
        BusinessBar bb = (BusinessBar) BusinessImplJdkProxy.factory(bbar);
        String message = bb.bar("Hello,World");
        System.out.println(message);
    }
}
