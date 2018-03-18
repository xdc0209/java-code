package com.xdc.basic.commons;

public class ClassLoaderUtil
{
    public static Class<?> loadClass(String className) throws ClassNotFoundException
    {
        ClassLoader loader = getContextClassLoader();

        if (loader == null)
        {
            return Class.forName(className);
        }
        else
        {
            return Class.forName(className, false, loader);
        }
    }

    private static ClassLoader getContextClassLoader()
    {
        return Thread.currentThread().getContextClassLoader();
    }
}
