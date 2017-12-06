package com.xdc.basic.algorithm.sword4offer.question47;

import java.util.Arrays;

public class Solution
{
    public static int getMaxValue(int[][] values)
    {
        if (values == null || values.length == 0 || values[0].length == 0)
        {
            return 0;
        }

        int maxValues[][] = Arrays.copyOf(values, values.length);

        for (int i = 0; i < values.length; i++)
        {
            for (int j = 0; j < values[i].length; j++)
            {
                int maxValue = 0;

                if (i - 1 >= 0 && j < values[i - 1].length && maxValues[i - 1][j] > maxValue)
                {
                    maxValue = maxValues[i - 1][j];
                }

                if (j - 1 >= 0 && maxValues[i][j - 1] > maxValue)
                {
                    maxValue = maxValues[i][j - 1];
                }

                maxValues[i][j] = values[i][j] + maxValue;
            }
        }

        return maxValues[values.length - 1][values[values.length - 1].length - 1];
    }

    public static void main(String[] args)
    {
        int[][] values = new int[][] { { 1, 10, 3, 8 }, { 12, 2, 9, 6 }, { 5, 7, 4, 11 }, { 3, 7, 16, 5 } };
        System.out.println(getMaxValue(values));
    }
}
