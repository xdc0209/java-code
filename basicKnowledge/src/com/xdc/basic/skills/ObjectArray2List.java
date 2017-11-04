package com.xdc.basic.skills;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 对象数组和链表的相互转换
 * 
 * @SEE org.eclipse.jetty.util.ArrayUtil.asMutableList(E[])
 */
public class ObjectArray2List
{
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        // 对象数组-->列表
        String[] strArray = new String[] { "z", "a", "C" };
        List<String> list = Arrays.asList(strArray); // 列表不支持增加、删除操作，支持修改操作，但是修改会反映到原始数组
        // 也可以直接这样写 List<String> list = Arrays.asList("z", "a", "C");
        List<String> list2 = new ArrayList<String>(Arrays.asList(strArray)); // 推荐，列表支持修改操作

        // 列表-->对象数组
        ArrayList<String> sl = new ArrayList<String>();
        String[] s = sl.toArray(new String[sl.size()]);
        System.out.println(sl.getClass().getCanonicalName());
        System.out.println(s.getClass().getCanonicalName());
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> arrayToList(T... array)
    {
        return new ArrayList<T>(Arrays.asList(array));
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] listToArray(List<T> list, Class<T> clazz)
    {
        T[] array = (T[]) Array.newInstance(clazz, list.size());
        return list.toArray(array);
    }
}
