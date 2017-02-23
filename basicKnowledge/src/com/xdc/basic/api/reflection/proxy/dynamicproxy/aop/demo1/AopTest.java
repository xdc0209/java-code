package com.xdc.basic.api.reflection.proxy.dynamicproxy.aop.demo1;

public class AopTest
{
    public static void main(String[] args)
    {
        Before before = new Before()
        {
            public void before()
            {
                System.out.println("...before...");
            }
        };

        After after = new After()
        {
            public void after()
            {
                System.out.println("...after...");
            }
        };

        Hello hello = null;

        // 普通方法执行
        System.out.println("-------------普通执行-------------");
        hello = new HelloEnglish();
        hello.sayHello("bao110908");
        hello.sayHi("bao110908");
        System.out.println();

        // 切入方法执行前（前置增强）
        System.out.println("-------------前置增强-------------");
        hello = HelloAopManager.getHelloProxy(new HelloEnglish(), before);
        hello.sayHello("bao110908");
        hello.sayHi("bao110908"); // sayHi 方法没有标注 @Enhancement 所以不会进行代码切入
        System.out.println();

        // 切入方法执行后（后置增强）
        System.out.println("-------------后置增强-------------");
        hello = HelloAopManager.getHelloProxy(new HelloEnglish(), after);
        hello.sayHello("bao110908");
        hello.sayHi("bao110908");
        System.out.println();

        // 切入方法执行前和执行后（环绕增强）
        System.out.println("-------------环绕增强-------------");
        hello = HelloAopManager.getHelloProxy(new HelloEnglish(), before, after);
        hello.sayHello("bao110908");
        hello.sayHi("bao110908");
        System.out.println();
    }
}
