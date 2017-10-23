package com.xdc.basic.api.reflection.proxy.dynamicproxy.aop.demo2;

public class AOPTest
{
    public static void main(String[] args)
    {
        // 使用Proxy类实现：
        // 1. 拦截所有业务方法
        // 2. 判断用户是否有权限，有权限就允许他执行业务方法，没有权限不允许他执行业务方法（是否有权限是根据user是否为null作为判断依据）
        JDKProxyFactory factory = new JDKProxyFactory();

        BankService service = (BankService) factory.createProxyInstance(new BankServiceImpl());
        service.payMoneyInto(100);

        BankService servicewithUser = (BankService) factory.createProxyInstance(new BankServiceImpl("xudachao"));
        servicewithUser.payMoneyInto(100);
    }
}
