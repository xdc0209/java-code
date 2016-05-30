package com.xdc.basic.skills;

public class ClassLoaderUtil
{
    public static Class<?> loadClass(String className) throws ClassNotFoundException
    {
        ClassLoader loader = getContextClassLoader();

        if (loader != null)
            return Class.forName(className, false, loader);
        else
            return Class.forName(className);
    }

    private static ClassLoader getContextClassLoader()
    {
        return Thread.currentThread().getContextClassLoader();
    }
}
