package com.xdc.basic.algorithm.basic.sort.exchange.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort
{
    public static void sort(int[] n)
    {
        for (int i = n.length - 1; i >= 0; i--)
        {
            boolean existExchange = false;

            for (int j = 0; j <= i - 1; j++)
            {
                if (n[j] > n[j + 1])
                {
                    swap(n, j, j + 1);
                    existExchange = true;
                }
            }

            if (!existExchange)
            {
                break;
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
        BubbleSort.sort(n);
        System.out.println(Arrays.toString(n));
    }
}
