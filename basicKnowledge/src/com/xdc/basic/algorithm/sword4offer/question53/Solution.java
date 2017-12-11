package com.xdc.basic.algorithm.sword4offer.question53;

public class Solution
{
    /**
     * 面试题53：在排序数组中查找数字 题目一：数字在排序数组中出现的次数。
     */
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

    /**
     * 面试题53：在排序数组中查找数字 题目二：0~n-1中缺失的数字。
     * 使用异或，时间复杂度O(n)，空间复杂度O(1)。
     */
    public static int GetMissingNumber(int[] array)
    {
        if (array == null || array.length == 0)
        {
            return 0;
        }

        int ans = 0;

        for (int i = 0; i < array.length; i++)
        {
            ans = ans ^ array[i];
        }

        for (int i = 0; i <= array.length; i++)
        {
            ans = ans ^ i;
        }

        return ans;
    }

    /**
     * 面试题53：在排序数组中查找数字 题目二：0~n-1中缺失的数字。
     * 使用求和(容易溢出)，时间复杂度O(n)，空间复杂度O(1)。
     */
    public static int GetMissingNumber2(int[] array)
    {
        if (array == null || array.length == 0)
        {
            return 0;
        }

        int ans = 0;

        for (int i = 0; i <= array.length; i++)
        {
            ans = ans + i;
        }

        for (int i = 0; i < array.length; i++)
        {
            ans = ans - array[i];
        }

        return ans;
    }

    /**
     * 面试题53：在排序数组中查找数字 题目二：0~n-1中缺失的数字。
     * 使用二分查找，时间复杂度O(logn)，空间复杂度O(1)。
     */
    public static int GetMissingNumber3(int[] array)
    {
        if (array == null || array.length == 0)
        {
            return 0;
        }

        int low = 0;
        int high = array.length - 1;
        int missing = -1;
        while (low <= high)
        {
            int mid = (low + high) / 2;

            if (array[mid] != mid)
            {
                high = mid - 1;
                missing = mid;
            }
            else // array[mid] == mid
            {
                low = mid + 1;
            }
        }

        return missing;
    }

    /**
     * 面试题53：在排序数组中查找数字 题目三：数组中数值和下标相等的元素。
     * 使用二分查找，时间复杂度O(logn)，空间复杂度O(1)。
     */
    public static int GetNumberSameAsIndex(int[] array)
    {
        if (array == null || array.length == 0)
        {
            return -1;
        }

        int low = 0;
        int high = array.length - 1;

        while (low <= high)
        {
            int mid = (low + high) / 2;

            if (array[mid] > mid)
            {
                high = mid - 1;
            }
            else if (array[mid] < mid)
            {
                low = mid + 1;
            }
            else // array[mid] == mid
            {
                return mid;
            }
        }

        return -1;
    }

    public static void main(String[] args)
    {
        System.out.println(GetNumberOfK(newArray(1, 2, 3, 3, 3, 3, 4, 5), 0));

        System.out.println(GetMissingNumber(newArray(0, 1, 3, 4, 5, 6)));
        System.out.println(GetMissingNumber2(newArray(0, 1, 3, 4, 5, 6)));
        System.out.println(GetMissingNumber3(newArray(0, 1, 3, 4, 5, 6)));

        System.out.println(GetNumberSameAsIndex(newArray(-3, -1, 1, 3, 5)));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
