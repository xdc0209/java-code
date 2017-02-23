package com.xdc.basic.api.hibernate.orm3.spring.framwaork.core;

import java.util.ArrayList;
import java.util.List;

public class DomainClasses
{
    private static List<Class<?>> clazzes = new ArrayList<Class<?>>();

    public static void addDomainClasses(Class<?> clazz)
    {
        clazzes.add(clazz);
    }

    public static List<Class<?>> getDomainClasses()
    {
        return clazzes;
    }
}
