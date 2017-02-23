package com.xdc.basic.tools.restframework.caller;

import java.util.HashMap;
import java.util.Map;

import com.xdc.basic.tools.restframework.core.CallProxy;

public class CallerFactory
{
    private static final Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();

    static
    {
        try
        {
            registerCaller(IpCaller.class);
            registerCaller(MusicCaller.class);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    private static void registerCaller(Class<?> clazz)
    {
        map.put(clazz, CallProxy.factory(clazz));
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCaller(Class<T> clazz)
    {
        return (T) map.get(clazz);
    }
}
