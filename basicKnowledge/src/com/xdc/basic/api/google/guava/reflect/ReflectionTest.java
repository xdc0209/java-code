package com.xdc.basic.api.google.guava.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.google.common.reflect.Reflection;

public class ReflectionTest
{
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        InvocationHandler invocationHandler = null;

        // JDK:
        Foo foo1 = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(), new Class<?>[] { Foo.class },
                invocationHandler);

        // Guava:
        Foo foo2 = Reflection.newProxy(Foo.class, invocationHandler);
    }
}

class Foo
{
}
