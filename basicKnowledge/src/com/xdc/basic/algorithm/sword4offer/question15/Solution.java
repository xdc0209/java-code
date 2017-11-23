package com.xdc.basic.algorithm.sword4offer.question15;

public class Solution
{
    public static int numberOfOne1(int n)
    {
        int count = 0;
        for (int b = 1; b != 0; b = b << 1)
        {
            if ((n & b) != 0)
            {
                count++;
            }
        }

        return count;
    }

    public static int numberOfOne2(int n)
    {
        int count = 0;
        while (n != 0)
        {
            count++;
            n = n & (n - 1); // 把一个整数减去1，再和原整数做与运算，会把该整数最右边的1变成0。
        }

        return count;
    }

    public static void main(String[] args)
    {
        for (int i = 0; i < 100; i++)
        {
            System.out.println(i + "：");

            int numberOfOne1 = numberOfOne1(i);
            System.out.println(numberOfOne1);

            int numberOfOne2 = numberOfOne2(i);
            System.out.println(numberOfOne2);

            System.out.println();
        }
    }
}
