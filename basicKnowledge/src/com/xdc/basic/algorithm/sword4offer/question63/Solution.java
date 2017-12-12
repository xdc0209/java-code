package com.xdc.basic.algorithm.sword4offer.question63;

public class Solution
{
    public static int MaxDiff(int[] numbers)
    {
        if (numbers == null || numbers.length < 2)
        {
            return 0;
        }

        int min = 0;
        int buy = 0;
        int sell = 1;

        for (int i = 2; i < numbers.length; ++i)
        {
            // min表示前i-1天股票最便宜的那天。
            if (numbers[i - 1] < numbers[min])
            {
                min = i - 1;
            }

            if (numbers[i] - numbers[min] > numbers[sell] - numbers[buy])
            {
                buy = min;
                sell = i;
            }
        }

        System.out.printf("buy:  numbers[%s]=%s\n", buy, numbers[buy]);
        System.out.printf("sell: numbers[%s]=%s\n", sell, numbers[sell]);
        return numbers[sell] - numbers[buy];
    }

    public static void main(String[] args)
    {
        System.out.println(MaxDiff(newArray(9, 11, 8, 18, 5, 7, 12, 16, 4, 14)));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
