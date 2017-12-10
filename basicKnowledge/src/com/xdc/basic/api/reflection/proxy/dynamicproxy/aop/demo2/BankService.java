package com.xdc.basic.api.reflection.proxy.dynamicproxy.aop.demo2;

public interface BankService
{
    /**
     * 存钱
     * 
     * @param money
     */
    void payMoneyInto(int money);

    /**
     * 取钱
     * 
     * @param money
     */
    void drawMoneyOut(int money);
}
