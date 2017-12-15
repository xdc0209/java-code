package com.xdc.basic.algorithm.sword4offer.question38;

import java.util.ArrayList;

public class Solution
{
    /**
     * 面试题38：字符串的排列。
     * 网上关于排列的代码大多是这个代码，但是此代码有个缺点，就是排列不是按照字典序排序的，更全面的代码参考：com.xdc.basic.algorithm.basic.math.Permutation
     */
    public static ArrayList<String> Permutation(String str)
    {
        ArrayList<String> permutations = new ArrayList<String>();

        if (str == null || str.length() == 0)
        {
            return permutations;
        }

        Permutation(str.toCharArray(), 0, permutations);

        return permutations;
    }

    public static void Permutation(char[] chars, int m, ArrayList<String> permutations)
    {
        if (m < 0 || m > chars.length)
        {
            throw new IllegalArgumentException("M is illegal.");
        }

        if (chars.length == m)
        {
            permutations.add(String.valueOf(chars));
            return;
        }

        for (int i = m; i < chars.length; i++)
        {
            char t = chars[m];
            chars[m] = chars[i];
            chars[i] = t;

            Permutation(chars, m + 1, permutations);

            t = chars[m];
            chars[m] = chars[i];
            chars[i] = t;
        }
    }

    public static void main(String[] args)
    {
        ArrayList<String> permutation = Permutation("abc");
        System.out.println(permutation);
    }
}
