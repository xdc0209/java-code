package com.xdc.basic.algorithm.sword4offer.question49;

import java.util.Arrays;

public class Solution
{
    public static int GetUglyNumber_Solution(int index)
    {
        if (index <= 0)
        {
            return 0;
        }

        int[] uglyNumbers = new int[index];
        uglyNumbers[0] = 1;

        int t2 = 0;
        int t3 = 0;
        int t5 = 0;

        for (int i = 1; i < uglyNumbers.length; i++)
        {
            while (uglyNumbers[t2] * 2 <= uglyNumbers[i - 1])
            {
                t2++;
            }

            while (uglyNumbers[t3] * 3 <= uglyNumbers[i - 1])
            {
                t3++;
            }

            while (uglyNumbers[t5] * 5 <= uglyNumbers[i - 1])
            {
                t5++;
            }

            uglyNumbers[i] = Math.min(Math.min(uglyNumbers[t2] * 2, uglyNumbers[t3] * 3), uglyNumbers[t5] * 5);
        }

        System.out.println(Arrays.toString(uglyNumbers));

        return uglyNumbers[index - 1];
    }

    public static int GetUglyNumber_Solution2(int index)
    {
        if (index <= 0)
        {
            return 0;
        }

        int[] uglyNumbers = new int[index];
        uglyNumbers[0] = 1;

        int t2 = 0;
        int t3 = 0;
        int t5 = 0;

        for (int i = 1; i < uglyNumbers.length; i++)
        {
            int uglyNumber2 = uglyNumbers[t2] * 2;
            int uglyNumber3 = uglyNumbers[t3] * 3;
            int uglyNumber5 = uglyNumbers[t5] * 5;

            if (uglyNumber2 < uglyNumber3)
            {
                if (uglyNumber2 < uglyNumber5)
                {
                    uglyNumbers[i] = uglyNumber2;
                    t2++;
                }
                else if (uglyNumber2 == uglyNumber5)
                {
                    uglyNumbers[i] = uglyNumber2;
                    t2++;
                    t5++;
                }
                else
                {
                    uglyNumbers[i] = uglyNumber5;
                    t5++;
                }
            }
            else if (uglyNumber2 == uglyNumber3)
            {
                if (uglyNumber2 < uglyNumber5)
                {
                    uglyNumbers[i] = uglyNumber2;
                    t2++;
                    t3++;
                }
                else if (uglyNumber2 == uglyNumber5)
                {
                    uglyNumbers[i] = uglyNumber2;
                    t2++;
                    t3++;
                    t5++;
                }
                else
                {
                    uglyNumbers[i] = uglyNumber5;
                    t5++;
                }
            }
            else
            {
                if (uglyNumber3 < uglyNumber5)
                {
                    uglyNumbers[i] = uglyNumber3;
                    t3++;
                }
                else if (uglyNumber3 == uglyNumber5)
                {
                    uglyNumbers[i] = uglyNumber3;
                    t3++;
                    t5++;
                }
                else
                {
                    uglyNumbers[i] = uglyNumber5;
                    t5++;
                }
            }
        }

        System.out.println(Arrays.toString(uglyNumbers));

        return uglyNumbers[index - 1];
    }

    public static int GetUglyNumber_Solution3(int index)
    {
        if (index <= 0)
        {
            return 0;
        }

        int[] uglyNumbers = new int[index];
        uglyNumbers[0] = 1;

        int t2 = 0;
        int t3 = 0;
        int t5 = 0;

        for (int i = 1; i < uglyNumbers.length; i++)
        {
            int uglyNumber2 = uglyNumbers[t2] * 2;
            int uglyNumber3 = uglyNumbers[t3] * 3;
            int uglyNumber5 = uglyNumbers[t5] * 5;

            uglyNumbers[i] = Math.min(Math.min(uglyNumber2, uglyNumber3), uglyNumber5);

            if (uglyNumbers[i] == uglyNumber2)
            {
                t2++;
            }
            if (uglyNumbers[i] == uglyNumber3)
            {
                t3++;
            }
            if (uglyNumbers[i] == uglyNumber5)
            {
                t5++;
            }
        }

        System.out.println(Arrays.toString(uglyNumbers));

        return uglyNumbers[index - 1];
    }

    public static void main(String[] args)
    {
        System.out.println(GetUglyNumber_Solution(1500));
    }
}
