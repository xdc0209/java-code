package com.xdc.basic.algorithm.sword4offer.question10;

import java.util.Date;

public class ALG
{
    /**
     * 斐波那契数列(递归)
     */
    public static long alg1(long n)
    {
        if (n <= 0)
        {
            return 0;
        }

        if (n == 1)
        {
            return 1;
        }

        return alg1(n - 1) + alg1(n - 2);
    }

    /**
     * 斐波那契数列(非递归)
     */
    public static long alg2(long n)
    {
        if (n <= 0)
        {
            return 0;
        }

        if (n == 1)
        {
            return 1;
        }

        long fibonacciNMinusTwo = 0;
        long fibonacciNMinusOne = 1;
        long fibonacciN = 0;
        for (long i = 2; i <= n; i++)
        {
            fibonacciN = fibonacciNMinusOne + fibonacciNMinusTwo;
            fibonacciNMinusTwo = fibonacciNMinusOne;
            fibonacciNMinusOne = fibonacciN;
        }

        return fibonacciN;
    }

    public static void main(String[] args)
    {
        System.out.println(new Date());
        System.out.println(alg1(47)); // 效率低下：(1)递归调用 (2)大量重复运算
        System.out.println(new Date());
        System.out.println(alg2(47));
        System.out.println(new Date());
    }
}
