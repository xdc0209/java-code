package com.xdc.basic.api.spring;

import com.xdc.basic.api.hibernate.orm3.spring.framwaork.core.SpringBeanFactory;
import com.xdc.basic.skills.GetPath;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            SpringBeanFactory.addSpringConfigPath(GetPath.connect(GetPath.getPackagePath(), "application-context.xml"));
            SpringBeanFactory.init();

            MyFirstBean myFirstBean = SpringBeanFactory.getBean("myFirstBean", MyFirstBean.class);
            myFirstBean.getProperty1();
            myFirstBean.getProperty2();

            System.out.println();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println();
    }
}
