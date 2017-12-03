package com.xdc.basic.algorithm.sword4offer.question39;

public class Solution
{
    private static final int NO_MAJOR = 0;

    public static int MoreThanHalfNum_Solution(int[] array)
    {
        if (array == null || array.length == 0)
        {
            return NO_MAJOR;
        }

        int major = FindMoreThanHalfNum(array);

        return CheckMoreThanHalfNum(array, major) ? major : NO_MAJOR;
    }

    private static boolean CheckMoreThanHalfNum(int[] array, int major)
    {
        int count = 0;
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == major)
            {
                count++;
            }
        }

        return count > (array.length >> 1);
    }

    private static int FindMoreThanHalfNum(int[] array)
    {
        int major = array[0];
        int count = 1;

        for (int i = 1; i < array.length; i++)
        {
            if (count == 0)
            {
                major = array[i];
                count = 1;
            }
            else if (array[i] == major)
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
        System.out.println(MoreThanHalfNum_Solution(newArray(1, 2, 3, 2, 2, 2, 5, 4, 2)));
        System.out.println(MoreThanHalfNum_Solution(newArray(1, 2, 3, 2, 2, 2, 5, 4, 2, 1)));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
