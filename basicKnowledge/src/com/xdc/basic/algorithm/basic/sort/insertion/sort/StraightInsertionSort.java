package com.xdc.basic.algorithm.basic.sort.insertion.sort;

import java.util.Arrays;

/**
 * 直接插入排序，时间复杂度为O(N^2)
 */
public class StraightInsertionSort
{
    public static void sort(int[] n)
    {
        // 第一个元素已经是有序的集合了，从第二个元素开始处理
        for (int i = 1; i < n.length; i++)
        {
            // 记录待插入的元素
            int t = n[i];

            // 将大于待插入的元素的所有元素整体后移一个单位
            int j = i - 1;
            for (; j >= 0 && t < n[j]; j--)
            {
                n[j + 1] = n[j];
            }

            // 插入元素
            n[j + 1] = t;
        }
    }

    public static void main(String[] args)
    {
        int[] n = new int[] { 6, 5, 3, 1, 8, 7, 2, 4 };
        StraightInsertionSort.sort(n);
        System.out.println(Arrays.toString(n));
    }
}
