package com.xdc.basic.algorithm.basic.search.静态查找表;

public class BinarySearch
{
    /**
     * 二分查找(折半查找)。前提是有序的顺序存储结构(有序的链表存储结构也是不行的)，时间复杂度为O(logn)。
     */
    public static int search1(int[] n, int search)
    {
        if (n == null || n.length == 0)
        {
            return -1;
        }

        int low = 0;
        int high = n.length - 1;

        while (low <= high)
        {
            int mid = (low + high) / 2;

            if (n[mid] > search)
            {
                high = mid - 1;
            }
            else if (n[mid] < search)
            {
                low = mid + 1;
            }
            else // n[mid] == search。
            {
                return mid;
            }
        }

        return -1;
    }

    /**
     * 二分查找(折半查找)，递归实现。前提是有序的顺序存储结构(有序的链表存储结构也是不行的)，时间复杂度为O(logn)。
     */
    public static int search2(int[] n, int search)
    {
        return search2(n, 0, n.length - 1, search);
    }

    /**
     * 二分查找的辅助方法，注意下标边界为前闭后闭：[low, high]
     */
    private static int search2(int[] n, int low, int high, int search)
    {
        if (low > high)
        {
            return -1;
        }

        int mid = (low + high) / 2;

        if (n[mid] > search)
        {
            return search2(n, low, mid - 1, search);
        }
        else if (n[mid] < search)
        {
            return search2(n, mid + 1, high, search);
        }
        else
        {
            return mid;
        }
    }

    public static void main(String[] args)
    {
        int[] n = { 1, 2, 3, 4, 5, 6, 7, 8 };

        System.out.println(BinarySearch.search1(n, 5));
        System.out.println(BinarySearch.search1(n, 9));

        System.out.println(BinarySearch.search2(n, 5));
        System.out.println(BinarySearch.search2(n, 9));
    }
}
