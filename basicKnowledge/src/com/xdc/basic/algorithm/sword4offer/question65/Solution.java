package com.xdc.basic.algorithm.sword4offer.question65;

public class Solution
{
    /**
     * 面试题65：不用加减乘除做加法。
     * 第一步：两个数异或：相当于每一位相加，而不考虑进位，所以异或又称之为不进位加法。
     * 第二步：两个数相与，并左移一位，相当于求得进位。
     * 第三步：将上述两步的结果相加。
     */
    public static int Add(int num1, int num2)
    {
        int sum, carry;
        do
        {
            sum = num1 ^ num2;
            carry = (num1 & num2) << 1;

            num1 = sum;
            num2 = carry;
        } while (num2 != 0);

        return sum;
    }

    public static void main(String[] args)
    {
        System.out.println(Add(5, 3));
    }
}
