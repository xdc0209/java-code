package com.xdc.basic.algorithm.sword4offer.question53;

public class Solution
{
    public static int GetNumberOfK(int[] array, int k)
    {
        if (array == null || array.length == 0)
        {
            return 0;
        }

        int low = 0;
        int high = array.length - 1;
        int firstK = -1;
        while (low <= high)
        {
            int mid = (low + high) / 2;

            if (array[mid] > k)
            {
                high = mid - 1;
            }
            else if (array[mid] < k)
            {
                low = mid + 1;
            }
            else // array[mid] == k
            {
                high = mid - 1;
                firstK = mid;
            }
        }

        low = 0;
        high = array.length - 1;
        int lastK = -1;
        while (low <= high)
        {
            int mid = (low + high) / 2;

            if (array[mid] > k)
            {
                high = mid - 1;
            }
            else if (array[mid] < k)
            {
                low = mid + 1;
            }
            else // array[mid] == k
            {
                low = mid + 1;
                lastK = mid;
            }
        }

        if (firstK != -1 && lastK != -1)
        {
            return lastK - firstK + 1;
        }
        else
        {
            return 0;
        }
    }

    public static void main(String[] args)
    {
        System.out.println(GetNumberOfK(newArray(1, 2, 3, 3, 3, 3, 4, 5), 0));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
