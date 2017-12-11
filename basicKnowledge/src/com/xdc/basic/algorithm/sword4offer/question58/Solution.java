package com.xdc.basic.algorithm.sword4offer.question58;

public class Solution
{
    /**
     * 面试题58：翻转字符串 题目一：翻转单词的顺序。
     * 举例：
     * 原始：“I am a student.”
     * 期望：“student. a am I”
     */
    public static String ReverseSentence(String string)
    {
        char[] chars = string.toCharArray();

        ReverseSentence(chars, 0, chars.length); // 整体翻转。

        int i = 0;
        int j = 0;
        while (i < chars.length)
        {
            while (i < chars.length && chars[i] == ' ')
            {
                i++;
            }
            j = i + 1;

            while (j < chars.length && chars[j] != ' ')
            {
                j++;
            }

            ReverseSentence(chars, i, j); // 局部翻转。
            i = j;
        }

        return String.valueOf(chars);
    }

    private static void ReverseSentence(char[] chars, int i, int j)
    {
        j--;
        while (i < j)
        {
            char t = chars[i];
            chars[i] = chars[j];
            chars[j] = t;

            i++;
            j--;
        }
    }

    /**
     * 面试题58：翻转字符串 题目二：左旋转字符串。
     */
    public static String LeftRotateString(String string, int n)
    {
        if (string == null || string.length() == 0)
        {
            return new String();
        }

        n = (n % string.length() + string.length()) % string.length();

        char[] chars = string.toCharArray();

        ReverseSentence(chars, 0, n); // 左侧翻转。
        ReverseSentence(chars, n, string.length()); // 右侧翻转。
        ReverseSentence(chars, 0, string.length()); // 整体翻转。

        return String.valueOf(chars);
    }

    public static void main(String[] args)
    {
        System.out.println(ReverseSentence("student. a am I"));

        System.out.println(LeftRotateString("abcdef", -4));
        System.out.println(LeftRotateString("abcdef", 2));
        System.out.println(LeftRotateString("abcdef", 8));
    }
}
