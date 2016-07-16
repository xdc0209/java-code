package com.xdc.basic.api.spring;

public class MyFirstBean
{
    private static MyFirstBean instance  = new MyFirstBean();

    private String             property1 = null;

    private String             property2 = null;

    public static MyFirstBean getInstance()
    {
        System.out.println("MyFirstBean.getInstance()");
        return instance;
    }

    private MyFirstBean()
    {
        System.out.println("MyFirstBean.MyFirstBean()");
    }

    public String getProperty1()
    {
        System.out.println("MyFirstBean.getProperty1()");
        return property1;
    }

    public void setProperty1(String property1)
    {
        System.out.println("MyFirstBean.setProperty1(" + property1 + ")");
        this.property1 = property1;
    }

    public String getProperty2()
    {
        System.out.println("MyFirstBean.getProperty2()");
        return property2;
    }

    public void setProperty2(String property2)
    {
        System.out.println("MyFirstBean.setProperty2(" + property2 + ")");
        this.property2 = property2;
    }

    public void init()
    {
        System.out.println("MyFirstBean.init()");
    }

    public void destroy()
    {
        System.out.println("MyFirstBean.destroy()");
    }
}
