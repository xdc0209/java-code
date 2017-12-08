package com.xdc.basic.algorithm.sword4offer.question51;

public class Solution
{
    public static int InversePairs(int[] array)
    {
        if (array == null || array.length <= 1)
        {
            return 0;
        }

        return sort(array);
    }

    /**
     * 归并排序数组
     */
    public static int sort(int[] n)
    {
        return sort(n, 0, n.length - 1);
    }

    /**
     * 归并排序子数组n[low,high]
     * 
     * @param n
     *            数组
     * @param low
     *            起始元素位置(包含此元素)
     * @param high
     *            终止元素位置(包含此元素)
     */
    private static int sort(int[] n, int low, int high)
    {
        if (low >= high)
        {
            return 0;
        }

        int middle = (low + high) / 2;
        int count1 = sort(n, low, middle);
        int count2 = sort(n, middle + 1, high);
        int count3 = merge(n, low, middle, high);

        return (count1 + count2 + count3) % 1000000007;
    }

    /**
     * 将有序的n[low,middle]和n[middle+1,middle]合并为有序的n[low,high]
     * 
     * @param n
     *            数组
     * @param low
     *            数组起始位置
     * @param middle
     *            数组中间位置
     * @param high
     *            数组终止位置
     */
    private static int merge(int[] n, int low, int middle, int high)
    {
        int count = 0;

        // 临时结果存储数组
        int[] t = new int[high - low + 1];

        int i = middle;
        int j = high;
        int k = high - low;

        // 将子数组n[low,middle]和n[middle+1,high]的元素由大到小地并入临时数组t
        while (i >= low && j >= middle + 1)
        {
            if (n[i] > n[j])
            {
                t[k] = n[i];
                i--;
                k--;
                count = (count + j - (middle + 1) + 1) % 1000000007; // 增加的逆序对数等于第二个子数组中剩余的元素个数
            }
            else
            {
                t[k] = n[j];
                j--;
                k--;
            }
        }

        // 将子数组n[low,middle]剩余的元素并入临时数组t
        while (i >= low)
        {
            t[k] = n[i];
            i--;
            k--;
        }

        // 将子数组n[middle+1,high]剩余的元素并入临时数组t
        while (j >= middle + 1)
        {
            t[k] = n[j];
            j--;
            k--;
        }

        // 临时数组t写回数组n
        for (int p = low, q = 0; p <= high && q <= t.length - 1; p++, q++)
        {
            n[p] = t[q];
        }

        return count;
    }

    public static void main(String[] args)
    {
        System.out.println(InversePairs(newArray(7, 5, 6, 4)));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
