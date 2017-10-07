package com.xdc.basic.algorithm.other.alg1;

public class ALG
{
    /**
     * 问题描述：给定一个数组，其中某个元素的个数大于数组总个数的一半，求出这个元素。
     */
    public static int alg(int[] n)
    {
        int major = n[0];
        int count = 1;

        for (int i = 1; i < n.length; i++)
        {
            if (count == 0)
            {
                major = n[i];
                count = 1;
            }
            else if (n[i] == major)
            {
                count++;
            }
            else
            {
                count--;
            }
        }

        return major;
    }

    public static void main(String[] args)
    {
        int alg = ALG.alg(new int[] { 2, 3, 3, 45, 5, 5, 3, 3, 3 });
        System.out.println(alg);
    }
}
