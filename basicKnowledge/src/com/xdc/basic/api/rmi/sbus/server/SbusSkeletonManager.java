package com.xdc.basic.api.rmi.sbus.server;

import java.util.HashMap;
import java.util.Map;

public class SbusSkeletonManager
{
    private static Map<Class<?>, SbusSkeleton<?>> map = new HashMap<Class<?>, SbusSkeleton<?>>();

    public static <T> void registerSbusSkeleton(Class<T> api, T t)
    {
        SbusSkeleton<T> sbusSkeleton = new SbusSkeleton<T>(t);
        map.put(api, sbusSkeleton);
    }

    public static <T> void unregisterSbusSkeleton(Class<T> api)
    {
        map.remove(api);
    }

    @SuppressWarnings("unchecked")
    public static <T> SbusSkeleton<T> getSbusSkeleton(Class<T> api)
    {
        return (SbusSkeleton<T>) map.get(api);
    }
}
