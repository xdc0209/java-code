package com.xdc.basic.algorithm.basic.sort.selection.sort;

import java.util.Arrays;

import com.xdc.basic.algorithm.basic.sort.insertion.sort.StraightInsertionSort;

/**
 * 简单选择排序
 */
public class SimpleSelectionSort
{
    public static void sort(int[] n)
    {
        for (int i = 0; i < n.length; i++)
        {
            // 初始化最小元素的坐标
            int min = i;
            for (int j = i + 1; j < n.length; j++)
            {
                if (n[j] < n[min])
                {
                    // 更新最小元素的坐标
                    min = j;
                }
            }

            if (min != i)
            {
                swap(n, i, min);
            }
        }
    }

    private static void swap(int[] n, int i, int j)
    {
        int temp = n[i];
        n[i] = n[j];
        n[j] = temp;
    }

    public static void main(String[] args)
    {
        int[] n = new int[] { 6, 5, 3, 1, 8, 7, 2, 4 };
        StraightInsertionSort.sort(n);
        System.out.println(Arrays.toString(n));
    }
}
