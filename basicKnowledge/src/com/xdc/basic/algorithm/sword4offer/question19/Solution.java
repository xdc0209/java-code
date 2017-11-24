package com.xdc.basic.algorithm.sword4offer.question19;

public class Solution
{
    /**
     * 面试题19：正则表达式匹配。
     */
    public static boolean match(char[] str, char[] pattern)
    {
        if (str == null || pattern == null)
        {
            return false;
        }

        return matchCore(str, 0, pattern, 0);
    }

    /**
     * 解决思路：
     * 
     * 1 当模式中的第二个字符不是“*”时：
     * 1.1 如果字符串第一个字符和模式中的第一个字符相匹配，那么字符串和模式都后移1个字符，继续匹配。
     * 1.2 如果字符串第一个字符和模式中的第一个字符相不匹配，直接返回false。
     * 
     * 2 而当模式中的第二个字符是“*”时：
     * 2.1 如果字符串第一个字符跟模式第一个字符匹配，可以有2种匹配方式：
     * 2.1.1 字符串不变，模式后移2字符，继续匹配。相当于x*被忽略。
     * 2.1.2 字符串后移1字符，模式不变，继续匹配。即继续匹配字符串下一位，因为x*可以匹配多位。
     * 2.2 如果字符串第一个字符跟模式第一个字符不匹配，则字符串不变，模式后移2字符，继续匹配。
     */
    private static boolean matchCore(char[] str, int strIndex, char[] pattern, int patternIndex)
    {
        // str到尾，pattern到尾，匹配成功。
        if (strIndex == str.length && patternIndex == pattern.length)
        {
            return true;
        }

        // str未到尾，pattern到尾，匹配失败。另外一点如果str先到尾，是不一定匹配失败，比如str=a和pattern=ab*c*。
        if (strIndex != str.length && patternIndex == pattern.length)
        {
            return false;
        }

        // 模式第2个字符是*。
        if (patternIndex + 1 < pattern.length && pattern[patternIndex + 1] == '*')
        {
            // 字符串第1个字符跟模式第1个字符可匹配。
            if (strIndex < str.length && (pattern[patternIndex] == str[strIndex] || pattern[patternIndex] == '.'))
            {
                return matchCore(str, strIndex, pattern, patternIndex + 2) // 字符串和模式不实行匹配，模式后移2位，理解为x*匹配0个字符。
                        || matchCore(str, strIndex + 1, pattern, patternIndex); // 字符串和模式实行匹配，尝试匹配下一个字符串。
            }
            // 字符串第1个字符跟模式第1个字符不可匹配，模式后移2位。
            else
            {
                return matchCore(str, strIndex, pattern, patternIndex + 2);
            }
        }
        // 模式第2个字符不是*。
        else
        {
            // 字符串第1个字符跟模式第1个字符可匹配，因为模式第2个字符不是*，所以必须实行匹配，因此都后移1位。
            if (strIndex < str.length && (pattern[patternIndex] == str[strIndex] || pattern[patternIndex] == '.'))
            {
                return matchCore(str, strIndex + 1, pattern, patternIndex + 1);
            }
            // 字符串第1个字符跟模式第1个字符不可匹配，直接返回false。
            else
            {
                return false;
            }
        }
    }

    public static void main(String[] args)
    {
        System.out.println(match("aaa".toCharArray(), "a.a".toCharArray()));
        System.out.println(match("aaa".toCharArray(), "ab*ac*a".toCharArray()));
        System.out.println(match("aaa".toCharArray(), "ab*ac*ad*e*".toCharArray()));
        System.out.println(match("aaa".toCharArray(), "aa.a".toCharArray()));
        System.out.println(match("aaa".toCharArray(), "ab*a".toCharArray()));
    }
}
