package com.xdc.basic.algorithm.sword4offer.question56;

public class Solution
{
    /**
     * 面试题56：数组中数字出现的次数 题目一：数组中只出现一次的两个数字。
     * num1,num2分别为长度为1的数组，将num1[0],num2[0]设置为返回结果。
     * 基本思想是将这个数组一分为二，而这个分法是有讲究的：必须将这两个不同的数字分到两个不同数组里，这可以根据这两个数中任意不同的位来划分。
     */
    public static void FindNumsAppearOnce(int[] array, int num1[], int num2[])
    {
        if (array == null || array.length < 2 || num1 == null || num1.length < 1 || num2 == null || num2.length < 1)
        {
            throw new RuntimeException("Invalid input!");
        }

        int num1XorNum2 = 0;
        for (int i = 0; i < array.length; i++)
        {
            num1XorNum2 ^= array[i];
        }

        // 方法一：找到两个只出现一次的数字的第一个位值不同的位置。
        int first1bit = 1;
        while ((num1XorNum2 & first1bit) == 0)
        {
            first1bit = first1bit << 1;
        }

        // 方法二：找到两个只出现一次的数字的第一个位值不同的位置。
        // int first1bit = (num1XorNum2 & (num1XorNum2 - 1)) ^ num1XorNum2;

        num1[0] = 0;
        num2[0] = 0;
        for (int i = 0; i < array.length; i++)
        {
            if ((array[i] & first1bit) == 0)
            {
                num1[0] ^= array[i];
            }
            else
            {
                num2[0] ^= array[i];
            }
        }
    }

    /**
     * 面试题56：数组中数字出现的次数 题目二：数组中唯一只出现一次的数字。
     * 思路：三个相同的数字异或之后还是本身。转换思路：如果一个数字出现三次，那么他的二进制表示的每一位(0或者1)也出现3次。如果把所有出现3次的数字的二进制表示的每一位都分别相加起来，那么每一位的和都能被3整除！！！
     */
    public static int FindNumAppearOnce(int[] array)
    {
        if (array == null || array.length == 0)
        {
            throw new RuntimeException("Invalid input!");
        }

        int[] bitSum = new int[32];
        for (int i = 0; i < array.length; i++)
        {
            for (int j = 0; j < bitSum.length; j++)
            {
                if ((array[i] & (1 << j)) != 0)
                {
                    bitSum[j]++;
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < bitSum.length; i++)
        {
            if (bitSum[i] % 3 != 0)
            {
                ans = ans | (1 << i);
            }
        }

        return ans;
    }

    public static void main(String[] args)
    {
        int[] array = new int[] { 0, 0, 2, 1, 4, 8, 2, 1 };
        int num1[] = new int[1];
        int num2[] = new int[1];
        FindNumsAppearOnce(array, num1, num2);
        System.out.println(num1[0] + " " + num2[0]);

        int[] array2 = new int[] { 1, 1, 1, 2, 3, 3, 3 };
        System.out.println(FindNumAppearOnce(array2));
    }
}
