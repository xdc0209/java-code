package com.xdc.basic.commons.collection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @see com.xdc.basic.skills.ObjectArray2List
 */
public class ListUtil
{
    public static <T> T getFirstElement(List<T> list)
    {
        if (list == null || list.size() < 1)
        {
            return null;
        }

        return list.get(0);
    }

    public static <T> T getSecondElement(List<T> list)
    {
        if (list == null || list.size() < 2)
        {
            return null;
        }

        return list.get(1);
    }

    public static <T> T getThirdElement(List<T> list)
    {
        if (list == null || list.size() < 3)
        {
            return null;
        }

        return list.get(2);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> arrayToList(T... array)
    {
        if (array == null || array.length == 0)
        {
            return new ArrayList<T>();
        }

        return new ArrayList<T>(Arrays.asList(array));
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] listToArray(List<T> list, Class<T> clazz)
    {
        if (list == null || list.size() == 0)
        {
            return (T[]) Array.newInstance(clazz, 0);
        }

        T[] array = (T[]) Array.newInstance(clazz, list.size());
        return list.toArray(array);
    }
}
