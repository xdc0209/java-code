package com.xdc.basic.algorithm.basic.sort;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MergingSort
{
    public static void sort(int[] n)
    {
        sort(n, 0, n.length - 1);
    }

    /**
     * 归并排序子数组n[low,high]
     * 
     * @param n
     *            数组
     * @param low
     *            起始元素位置(包含此元素)
     * @param high
     *            终止元素位置(包含此元素)
     */
    private static void sort(int[] n, int low, int high)
    {
        if (low < high)
        {
            int middle = (low + high) / 2;
            sort(n, low, middle);
            sort(n, middle + 1, high);
            merge(n, low, middle, high);
        }
    }

    /**
     * 将有序的n[low,middle]和n[middle+1,middle]合并为有序的n[low,high]
     * 
     * @param n
     *            数组
     * @param low
     *            数组起始位置
     * @param middle
     *            数组中间位置
     * @param high
     *            数组终止位置
     */
    private static void merge(int[] n, int low, int middle, int high)
    {
        // 临时结果存储数组
        int[] t = new int[high - low + 1];

        int i = low;
        int j = middle + 1;
        int k = 0;

        // 将子数组n[low,middle]和n[middle+1,high]的元素由小到大地并入临时数组t
        while (i <= middle && j <= high)
        {
            if (n[i] <= n[j])
            {
                t[k] = n[i];
                i++;
                k++;
            }
            else
            {
                t[k] = n[j];
                j++;
                k++;
            }
        }

        // 将子数组n[low,middle]剩余的元素并入临时数组t
        while (i <= middle)
        {
            t[k] = n[i];
            i++;
            k++;
        }

        // 将子数组n[middle+1,high]剩余的元素并入临时数组t
        while (j <= high)
        {
            t[k] = n[j];
            j++;
            k++;
        }

        // 临时数组t写回数组n
        for (int p = low, q = 0; p <= high && q <= t.length - 1; p++, q++)
        {
            n[p] = t[q];
        }
    }

    public static void main(String[] args)
    {
        int[] n = new int[] { 1, 7, 4, 2, 6 };
        MergingSort.sort(n);
        System.out.println(Arrays.toString(n));
    }
}
