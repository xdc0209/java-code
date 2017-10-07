package com.xdc.basic.algorithm.sword4offer.question05;

public class ALG
{
    /**
     * 请实现一个函数，将一个字符串中的空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
     */
    public static String alg(StringBuffer str)
    {
        if (str == null)
        {
            return null;
        }

        // 统计空格的个数
        int blankNum = 0;
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) == ' ')
            {
                blankNum++;
            }
        }

        // 计算最终字符串的长度
        int oldStrLen = str.length();
        int newStrLen = str.length() + blankNum * 2;

        // 设置字符串最终长度
        str.setLength(newStrLen);

        // 从后往前处理字符串
        for (int i = oldStrLen - 1, j = newStrLen - 1; i >= 0; i--)
        {
            char c = str.charAt(i);
            if (c == ' ')
            {
                str.setCharAt(j--, '0');
                str.setCharAt(j--, '2');
                str.setCharAt(j--, '%');
            }
            else
            {
                str.setCharAt(j--, c);
            }
        }

        return str.toString();
    }

    public static void main(String[] args)
    {
        System.out.println(alg(new StringBuffer("We Are Happy")));
    }
}
