package com.xdc.basic.api.hibernate.orm3.spring.framwaork.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeanFactory
{
    private static List<String>       springConfigPaths = new ArrayList<String>();

    private static ApplicationContext applicationContext;

    public static void init()
    {
        String[] springConfigPathArray = springConfigPaths.toArray(new String[springConfigPaths.size()]);
        applicationContext = new ClassPathXmlApplicationContext(springConfigPathArray);
    }

    public static void addSpringConfigPath(String springConfigPath)
    {
        springConfigPaths.add(springConfigPath);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name, Class<T> clazz)
    {
        if (applicationContext == null)
        {
            SpringBeanFactory.init();
        }

        return (T) applicationContext.getBean(name);
    }
}