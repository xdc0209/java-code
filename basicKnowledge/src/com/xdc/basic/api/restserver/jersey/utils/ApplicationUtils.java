package com.xdc.basic.api.restserver.jersey.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import org.apache.commons.lang3.StringUtils;

public class ApplicationUtils
{
    public static List<String> getPaths(Application application)
    {
        List<String> paths = new ArrayList<>();

        Set<Class<?>> clazzes = application.getClasses();
        for (Class<?> clazz : clazzes)
        {
            paths.addAll(getPaths(clazz));
        }

        Set<Object> singletons = application.getSingletons();
        for (Object object : singletons)
        {
            paths.addAll(getPaths(object.getClass()));
        }

        return paths;
    }

    public static List<String> getPaths(Class<?> clazz)
    {
        List<String> paths = new ArrayList<String>();

        Path classPathAnnotation = clazz.getAnnotation(Path.class);
        // 类上必须有Path注解，否则整个类不会被认为是rest资源，即使方法上有Path注解
        if (classPathAnnotation == null)
        {
            return paths;
        }

        String classPath = classPathAnnotation.value();

        for (Method m : clazz.getDeclaredMethods())
        {
            String methodType = StringUtils.EMPTY;

            GET get = m.getAnnotation(GET.class);
            POST post = m.getAnnotation(POST.class);
            PUT put = m.getAnnotation(PUT.class);
            DELETE delete = m.getAnnotation(DELETE.class);
            if (get != null)
            {
                methodType = "GET ";
            }
            if (post != null)
            {
                methodType = "POST ";
            }
            if (put != null)
            {
                methodType = "PUT ";
            }
            if (delete != null)
            {
                methodType = "DELETE ";
            }

            // 方法类型为空，说明此方法不是资源的操作方法
            if (StringUtils.isEmpty(methodType))
            {
                continue;
            }

            String methodPath = StringUtils.EMPTY;

            Path methodPathAnnotation = clazz.getAnnotation(Path.class);
            if (methodPathAnnotation != null)
            {
                methodPath = methodPathAnnotation.value();
            }

            String fullPath = String.format("%-8s/%s/%s", methodType, classPath, methodPath);
            fullPath = StringUtils.replace(fullPath, "//", "/");
            fullPath = StringUtils.removeEnd(fullPath, "/");

            paths.add(fullPath);
        }

        return paths;
    }
}
