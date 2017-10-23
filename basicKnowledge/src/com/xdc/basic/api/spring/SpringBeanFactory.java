package com.xdc.basic.api.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeanFactory
{
    private static List<String>               springConfigPaths  = new ArrayList<String>();

    private static AbstractApplicationContext applicationContext = null;

    public static void addSpringConfigPath(String springConfigPath)
    {
        springConfigPaths.add(springConfigPath);
    }

    public static void init()
    {
        if (applicationContext == null)
        {
            String[] springConfigPathArray = springConfigPaths.toArray(new String[springConfigPaths.size()]);
            applicationContext = new ClassPathXmlApplicationContext(springConfigPathArray);
            applicationContext.registerShutdownHook();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name, Class<T> clazz)
    {
        if (applicationContext == null)
        {
            SpringBeanFactory.init();
        }

        // 两种写法，都可以
        // return applicationContext.getBean(name, clazz);

        return (T) applicationContext.getBean(name);
    }
}
