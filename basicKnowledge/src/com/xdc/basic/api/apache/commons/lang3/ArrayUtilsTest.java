package com.xdc.basic.api.apache.commons.lang3;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 待整理
 * 
 * @author xdc
 * 
 */
public class ArrayUtilsTest
{
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        // 在ArrayUtils中public static final String[] EMPTY_STRING_ARRAY = new String[0];
        String[] EMPTY_STRING_ARRAY = ArrayUtils.EMPTY_STRING_ARRAY;
        String[] strs = null;
        String[] strs2 = new String[10];

        // Checks if an array of primitive ints is empty or null.
        System.out.println(ArrayUtils.isEmpty(EMPTY_STRING_ARRAY)); // true
        System.out.println(ArrayUtils.isEmpty(strs)); // true
        System.out.println(ArrayUtils.isEmpty(strs2)); // false

        strs = ArrayUtils.nullToEmpty(strs);

        // Compares two arrays, using equals(), handling multi-dimensional arrays correctly.
        // Multi-dimensional primitive arrays are also handled correctly by this method.
        ArrayUtils.isEquals(strs, EMPTY_STRING_ARRAY);

        int[] array = new int[5];
        // 基本数组 --> 对象数组
        Integer[] array2 = ArrayUtils.toObject(array);
        // 对象数组 --> 基本数组
        int[] array3 = ArrayUtils.toPrimitive(array2);
    }
}
