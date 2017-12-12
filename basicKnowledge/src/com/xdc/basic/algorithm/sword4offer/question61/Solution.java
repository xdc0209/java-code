package com.xdc.basic.algorithm.sword4offer.question61;

import java.util.Arrays;

public class Solution
{
    /**
     * 面试题61：扑克牌中的顺子。
     * 
     * 大小王为0
     * A为1
     * 2~10为2~10
     * J为11
     * Q为12
     * K为13
     */
    public static boolean isContinuous(int[] numbers)
    {
        if (numbers == null || numbers.length == 0)
        {
            return false;
        }

        Arrays.sort(numbers);

        // 计算癞子数量。
        int zeroCount = 0;
        for (int i = 0; i < numbers.length && numbers[i] == 0; i++)
        {
            zeroCount++;
        }

        int intervalCount = 0;
        for (int i = zeroCount; i < numbers.length - 1; i++)
        {
            // 对子，直接返回。
            if (numbers[i + 1] == numbers[i])
            {
                return false;
            }

            // 计算间隔数量。
            intervalCount += (numbers[i + 1] - numbers[i] - 1);
        }

        return zeroCount >= intervalCount;
    }

    public static void main(String[] args)
    {
        System.out.println(isContinuous(newArray(0, 1, 2, 4, 5, 6)));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
