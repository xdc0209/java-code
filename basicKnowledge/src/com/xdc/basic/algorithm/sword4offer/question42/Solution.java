package com.xdc.basic.algorithm.sword4offer.question42;

import java.util.Arrays;

public class Solution
{
    public static int FindGreatestSumOfSubArray(int[] array)
    {
        if (array == null || array.length == 0)
        {
            throw new RuntimeException("Invalid input!");
        }

        int curSum = array[0];

        int maxSum = array[0];
        int start = 0;
        int end = 0;

        for (int i = 0, j = 1; j < array.length; j++)
        {
            if (curSum > 0)
            {
                curSum = curSum + array[j];
            }
            else
            {
                curSum = array[j];
                i = j;
            }

            if (curSum > maxSum)
            {
                maxSum = curSum;
                start = i;
                end = j;
            }
        }

        System.out.printf("序列为: %s\n", Arrays.toString(array));
        System.out.printf("最大子段为(%d~%d): %s\n", start, end, Arrays.toString(Arrays.copyOfRange(array, start, end + 1)));
        System.out.printf("最大子段和为：%d\n", maxSum);
        System.out.println();

        return maxSum;
    }

    public static void main(String[] args)
    {
        FindGreatestSumOfSubArray(newArray(1, -2, 3, 10, -4, 7, 2, -5));
        FindGreatestSumOfSubArray(newArray(-3, -2, -4));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
