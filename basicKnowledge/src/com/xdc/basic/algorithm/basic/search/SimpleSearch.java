package com.xdc.basic.algorithm.basic.search;

public class SimpleSearch
{
    /**
     * 简单查找，数组不必有序，时间复杂度为O(n)
     */
    public static int search(int[] n, int search)
    {
        if (n == null || n.length == 0)
        {
            return -1;
        }

        for (int i = 0; i < n.length; i++)
        {
            if (n[i] == search)
            {
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args)
    {
        int[] n = { 1, 2, 3, 4, 5, 6, 7, 8 };
        System.out.println(SimpleSearch.search(n, 5));
        System.out.println(SimpleSearch.search(n, 9));
    }
}
