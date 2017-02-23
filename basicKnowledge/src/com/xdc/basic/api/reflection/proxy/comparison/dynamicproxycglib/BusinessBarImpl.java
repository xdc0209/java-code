package com.xdc.basic.api.reflection.proxy.comparison.dynamicproxycglib;

public class BusinessBarImpl implements BusinessBar
{
    public String bar(String message)
    {
        System.out.println("BusinessBarImpl.bar()");
        return message;
    }
}
