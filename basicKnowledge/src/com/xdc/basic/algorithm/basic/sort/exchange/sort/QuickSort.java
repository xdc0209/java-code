package com.xdc.basic.algorithm.basic.sort.exchange.sort;

import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSort
{
    public static void sort(int[] n)
    {
        sort(n, 0, n.length - 1);
    }

    private static void sort(int[] n, int low, int high)
    {
        // 长度大于1
        if (low < high)
        {
            // 数组n一分为二
            int partition = partition(n, low, high);

            // 将低子数组递归排序
            sort(n, low, partition - 1);

            // 将高子数组递归排序
            sort(n, partition + 1, high);
        }
    }

    private static int partition(int[] n, int low, int high)
    {
        // 记录枢轴
        int pivot = n[low];

        // 从表的两端交替的向中间扫描
        while (low < high)
        {
            // 将比枢轴记录小的记录移到低端
            while (low < high && n[high] >= pivot)
            {
                high--;
            }
            n[low] = n[high];

            // 将比枢轴记录大的记录移到高端
            while (low < high && n[low] <= pivot)
            {
                low++;
            }
            n[high] = n[low];
        }

        // 枢轴记录到位
        n[low] = pivot;

        // 返回枢轴位置
        return low;
    }

    public static void main(String[] args)
    {
        int[] n = new int[] { 6, 5, 3, 1, 8, 7, 2, 4 };
        QuickSort.sort(n);
        System.out.println(Arrays.toString(n));
    }
}
