package com.xdc.basic.algorithm.sword4offer.question66;

import java.util.Arrays;

public class Solution
{
    /**
     * 面试题66：构建乘积数组
     */
    public static int[] multiply(int[] A)
    {
        if (A == null || A.length < 2)
        {
            throw new RuntimeException("Invalid input!");
        }

        int[] B = new int[A.length];

        // 计算下三角连乘。
        B[0] = 1;
        for (int i = 1; i < B.length; i++)
        {
            B[i] = B[i - 1] * A[i - 1];
        }

        // 计算上三角。
        int temp = 1;
        for (int i = B.length - 2; i >= 0; i--)
        {
            temp *= A[i + 1];
            B[i] *= temp;
        }

        return B;
    }

    public static void main(String[] args)
    {
        System.out.println(Arrays.toString(multiply(newArray(9, 11, 8))));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
