package com.xdc.basic.algorithm.sword4offer.question17;

public class Solution
{
    public static void Print1ToMaxOfNDigits(int n)
    {
        StringBuilder sb = new StringBuilder(n + 1);
        sb.append("1");

        while (sb.length() <= n)
        {
            System.out.println(sb);
            sb = addOne(sb);
        }
    }

    private static StringBuilder addOne(StringBuilder sb)
    {
        for (int i = sb.length() - 1; i >= 0; i--)
        {
            char c = sb.charAt(i);
            if (c == '9')
            {
                sb.setCharAt(i, '0');
                if (i == 0)
                {
                    sb.insert(0, '1');
                    break;
                }
            }
            else
            {
                sb.setCharAt(i, (char) (c + 1));
                break;
            }

        }

        return sb;
    }

    public static void main(String[] args)
    {
        Print1ToMaxOfNDigits(5);
    }
}
