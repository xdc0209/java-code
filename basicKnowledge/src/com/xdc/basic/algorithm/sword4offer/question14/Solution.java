package com.xdc.basic.algorithm.sword4offer.question14;

public class Solution
{
    /**
     * 面试题14：剪绳子 (动态规划)
     */
    public static int maxProductAfterCutting1(int length)
    {
        if (length < 2)
        {
            return 0;
        }

        if (length == 2)
        {
            return 1;
        }

        if (length == 3)
        {
            return 2;
        }

        int[] products = new int[length + 1];
        products[0] = 0;
        products[1] = 1;
        products[2] = 2;
        products[3] = 3;

        for (int i = 4; i <= length; i++)
        {
            int max = -1;
            for (int j = 1; j <= i / 2; j++)
            {
                int product = products[j] * products[i - j];
                if (product > max)
                {
                    max = product;
                }
            }

            products[i] = max;
        }

        return products[length];
    }

    /**
     * 面试题14：剪绳子 (贪婪算法)
     * 贪婪策略：如果绳子的长度大于等于5，则每次都剪出一段长度为3的绳子。如果剩下的绳子的长度仍然大于等于5，则接着剪出一段长度为3的绳子。接下来重复这个步骤，直到剩下的绳子的长度小于5。
     */
    public static int maxProductAfterCutting2(int length)
    {
        if (length < 2)
        {
            return 0;
        }

        if (length == 2)
        {
            return 1;
        }

        if (length == 3)
        {
            return 2;
        }

        if (length == 4)
        {
            return 4;
        }

        int timesOfThree = (length - 5) / 3 + 1;
        int remain = length - timesOfThree * 3;

        return (int) Math.pow(3, timesOfThree) * remain;
    }

    public static void main(String[] args)
    {
        for (int i = 0; i < 50; i++)
        {
            int maxProductAfterCutting1 = maxProductAfterCutting1(i);
            System.out.println(maxProductAfterCutting1);

            int maxProductAfterCutting2 = maxProductAfterCutting2(i);
            System.out.println(maxProductAfterCutting2);

            System.out.println();
        }
    }
}
