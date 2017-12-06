package com.xdc.basic.algorithm.sword4offer.question46;

public class Solution
{
    public static int GetTranslationCount(int number)
    {
        if (number < 0)
        {
            throw new RuntimeException("Invalid input!");
        }

        char[] digits = String.valueOf(number).toCharArray();
        int length = digits.length;

        if (length == 1)
        {
            return 1;
        }

        int[] counts = new int[length + 1];
        counts[length - 0] = 1;
        counts[length - 1] = 1;
        for (int i = length - 2; i >= 0; i--)
        {
            counts[i] = counts[i + 1] + counts[i + 2] * isLetter(digits[i], digits[i + 1]);
        }

        return counts[0];
    }

    public static int isLetter(char i, char j)
    {
        int letter = (i - '0') * 10 + (j - '0');
        return (10 <= letter && letter <= 25) ? 1 : 0;
    }

    public static void main(String[] args)
    {
        System.out.println(GetTranslationCount(1));
        System.out.println(GetTranslationCount(11));
        System.out.println(GetTranslationCount(21));
        System.out.println(GetTranslationCount(31));
        System.out.println(GetTranslationCount(12258));
    }
}
