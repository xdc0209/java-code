package com.xdc.basic.api.reflection.proxy.comparison.dynamicproxycglib;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

class BusinessImplCglibProxy implements MethodInterceptor
{
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable
    {
        doBefore();
        Object result = proxy.invokeSuper(obj, args);
        doAfter();
        return result;
    }

    private void doBefore()
    {
        System.out.println("前置处理！");
    }

    private void doAfter()
    {
        System.out.println("后置处理！");
    }

    public static Object factory(Class<?> clazz)
    {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        // 回调方法
        enhancer.setCallback(new BusinessImplCglibProxy());
        // 创建代理对象
        return enhancer.create();
    }
}
