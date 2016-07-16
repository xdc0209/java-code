package com.xdc.basic.api.spring;

public class MySecondBean
{
    private static MySecondBean instance  = new MySecondBean();

    private String              property3 = null;

    private String              property4 = null;

    public static MySecondBean getInstance()
    {
        System.out.println("MySecondBean.getInstance()");
        return instance;
    }

    private MySecondBean()
    {
        System.out.println("MySecondBean.MySecondBean()");
    }

    public String getProperty3()
    {
        System.out.println("MySecondBean.getProperty3()");
        return property3;
    }

    public void setProperty3(String property3)
    {
        System.out.println("MySecondBean.setProperty3(" + property3 + ")");
        this.property3 = property3;
    }

    public String getProperty4()
    {
        System.out.println("MySecondBean.getProperty4()");
        return property4;
    }

    public void setProperty4(String property4)
    {
        System.out.println("MySecondBean.setProperty4(" + property4 + ")");
        this.property4 = property4;
    }

    public void init()
    {
        System.out.println("MySecondBean.init()");
    }

    public void destroy()
    {
        System.out.println("MySecondBean.destroy()");
    }
}
