package com.xdc.basic.algorithm.sword4offer.question21;

import java.util.Arrays;
import java.util.Comparator;

public class Solution
{
    /**
     * 面试题21：调整数组顺序使奇数位于偶数前面。
     * 问题描述：输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。特别注意是数组元素的奇偶，不是数组索引的奇偶。
     * 解题思路：此题实质上可理解为数组的排序，只不过整数的大小关系比较特殊，比较规则定义为奇数比偶数小就可以了。因为题目要求奇奇偶偶的相对位置不变，所以要使用稳定的排序算法。
     * 具体实现：在com.xdc.basic.algorithm.basic.sort中选取一种稳定的排序算法，修改比较的地方就可以了，比较简单，就不不实现了，直接使用java自带的排序算法。
     */
    public static void reOrderArray(int[] ints)
    {
        Integer[] integers = new Integer[ints.length];
        for (int i = 0; i < ints.length; i++)
        {
            integers[i] = ints[i];
        }

        Arrays.sort(integers, new Comparator<Integer>()
        {
            /**
             * 实现两个整数的特殊比较，规则是奇数比偶数小。
             */
            @Override
            public int compare(Integer o1, Integer o2)
            {
                if ((o1 & 1) == 1 && (o2 & 1) == 0) // 第一个是奇数，第二个是偶数，则比较结果是小于，返回-1。
                {
                    return -1;
                }
                else if ((o1 & 1) == 0 && (o2 & 1) == 1) // 第一个是偶数，第二个是奇数，则比较结果是大于，返回1。
                {
                    return 1;
                }
                else // 同奇或同偶，则比较结果是等于，返回0。
                {
                    return 0;
                }
            }
        });

        for (int i = 0; i < integers.length; i++)
        {
            ints[i] = integers[i];
        }
    }

    public static void main(String[] args)
    {
        testReOrderArray();
        testReOrderArray(1);
        testReOrderArray(1, 2);
        testReOrderArray(1, 2, 3);
        testReOrderArray(1, 2, 3, 4);
        testReOrderArray(1, 2, 3, 4, 5);
        testReOrderArray(1, 2, 3, 4, 5, 6);
        testReOrderArray(1, 2, 3, 4, 5, 6, 7);
        testReOrderArray(1, 2, 3, 4, 5, 6, 7, 8);
        testReOrderArray(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    public static void testReOrderArray(int... array)
    {
        System.out.println(array.length);
        System.out.println(Arrays.toString(array));

        reOrderArray(array);
        System.out.println(Arrays.toString(array));

        System.out.println();
    }
}
