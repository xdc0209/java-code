package com.xdc.basic.skills;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 基本数组与对象数组转换
 * 
 * @author xdc
 * 
 */
public class BasicArray2ObjectArray
{
    public static void main(String[] args)
    {
        int[] array = new int[] { 1, 2, 3 };

        // 基本数组 --> 对象数组
        Integer[] array2 = ArrayUtils.toObject(array);

        // 对象数组 --> 基本数组
        int[] array3 = ArrayUtils.toPrimitive(array2);

        System.out.println(array3.length);
    }
}
