package com.xdc.basic.api.reflection.proxy.dynamicproxy.aop.demo2;

public class BankServiceImpl implements BankService
{
    private String user = null;

    public BankServiceImpl()
    {
    }

    public BankServiceImpl(String user)
    {
        this.user = user;
    }

    public String getUser()
    {
        return user;
    }

    @Override
    public void payMoneyInto(int money)
    {
        System.out.println("存入：" + money + "元.");
    }

    @Override
    public void drawMoneyOut(int money)
    {
        System.out.println("取出：" + money + "元.");
    }
}
