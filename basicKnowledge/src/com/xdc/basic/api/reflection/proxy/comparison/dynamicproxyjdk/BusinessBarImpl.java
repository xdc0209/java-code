package com.xdc.basic.api.reflection.proxy.comparison.dynamicproxyjdk;

public class BusinessBarImpl implements BusinessBar
{
    @Override
    public String bar(String message)
    {
        System.out.println("BusinessBarImpl.bar()");
        return message;
    }
}
