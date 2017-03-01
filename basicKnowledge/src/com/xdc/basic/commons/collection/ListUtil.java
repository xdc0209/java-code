package com.xdc.basic.commons.collection;

import java.util.List;

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
}