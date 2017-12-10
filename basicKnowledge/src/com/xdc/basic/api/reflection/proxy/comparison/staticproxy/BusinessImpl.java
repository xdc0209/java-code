package com.xdc.basic.api.reflection.proxy.comparison.staticproxy;

public class BusinessImpl implements Business
{
    @Override
    public void doAction()
    {
        System.out.println("do action.");
    }

    @Override
    public void doAction2()
    {
        System.out.println("do action2.");
    }
}
