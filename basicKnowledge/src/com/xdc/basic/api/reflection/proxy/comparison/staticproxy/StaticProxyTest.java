package com.xdc.basic.api.reflection.proxy.comparison.staticproxy;

public class StaticProxyTest
{
    public static void main(String[] args)
    {
        BusinessImpl businessImpl = new BusinessImpl();
        BusinessImplProxy businessImplProxy = new BusinessImplProxy(businessImpl);
        businessImplProxy.doAction();
        businessImplProxy.doAction2();
    }
}
