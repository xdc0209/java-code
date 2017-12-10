package com.xdc.basic.algorithm.sword4offer.question50;

public class Solution
{
    public static int FirstNotRepeatingChar(String string)
    {
        if (string == null || string.length() == 0)
        {
            return -1;
        }

        int[] hashtable = new int[256]; // 题目说明了字符在ASCII字符内。

        for (int i = 0; i < string.length(); i++)
        {
            hashtable[string.charAt(i)]++;
        }

        for (int i = 0; i < string.length(); i++)
        {
            if (hashtable[string.charAt(i)] == 1)
            {
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args)
    {
        System.out.println(FirstNotRepeatingChar("abaccdeff"));
    }
}
