package com.xdc.basic.algorithm.basic.sort.insertion.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 希尔插入排序，又称缩小增量排序
 * 参考：http://blog.csdn.net/chy89224/article/details/70045064
 * 参考：http://blog.csdn.net/foliciatarier/article/details/53891144
 * 参考：http://blog.csdn.net/pacosonswjtu/article/details/52093398
 */
@SuppressWarnings("unused")
public class ShellInsertionSort
{
    // 希尔增量：最坏情况运行时间为O(N^2)，公式为：D(1)=N/2， D(k+1)=D(k)/2，其中k>=1，N为数组长度。此处因N值未定，无法打表。此序列为降序，程序正序处理就可以了。缺点：增量元素不互质，则小增量可能根本不起作用。
    // private static final int[] SHELL_D = {};

    // Hibbard增量：最坏时间复杂度为 Θ(N^3/2)，平均时间复杂度约为 O(N^5/4)。公式为：D(k)=2^k-1，其中k>=1。此序列为升序，程序需逆序处理。
    private static final int[] D_HIBBARD   = { 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767,
            65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727,
            268435455, 536870911, 1073741823, 2147483647 };

    // Sedgewick增量：最坏时间复杂度为 O(N^4/3)，平均时间复杂度约为 O(N^7/6)。公式为：较复杂，见genSedgewickDeltas()。此序列为升序，程序需逆序处理。
    private static final int[] D_SEDGEWICK = { 1, 5, 19, 41, 109, 209, 505, 929, 2161, 3905, 8929, 16001, 36289, 64769,
            146305, 260609, 587521, 1045505, 2354689, 4188161, 9427969, 16764929, 37730305, 67084289, 150958081,
            268386305, 603906049, 1073643521 };

    // 默认增量
    private static final int[] D_DEFAULT   = D_HIBBARD;

    public static void sort(int[] n)
    {
        for (int i = D_DEFAULT.length - 1; i >= 0; i--)
        {
            ShellInsertionSort.sort(n, D_DEFAULT[i]);
        }
    }

    private static void sort(int[] n, int d)
    {
        // 整个数组根据增量d进行分组，每组的第一个元素已经是有序的集合了，从第二个元素开始处理(注意每组的第二个元素为每组的第一个元素加d)
        for (int i = d; i < n.length; i++)
        {
            // 记录待插入的元素
            int t = n[i];

            // 在同一组中，将大于待插入的元素的所有元素整体后移一个单位
            int j = i - d;
            for (; j >= 0 && t < n[j]; j = j - d)
            {
                n[j + d] = n[j];
            }

            // 插入元素
            n[j + d] = t;
        }
    }

    /**
     * 生成希尔增量
     * http://blog.csdn.net/u013354805/article/details/51217369
     */
    private static void genShellDeltas()
    {
        // 假设待排序的数组长度为100000000
        int N = 100000000;

        List<Long> deltas = new ArrayList<Long>();

        for (int delta = N / 2; delta > 0; delta = delta / 2)
        {
            deltas.add((long) delta);
        }

        System.out.println(deltas);
    }

    /**
     * 生成Hibbard增量序列
     * 参考：http://blog.csdn.net/foliciatarier/article/details/53891144
     */
    private static void genHibbardDeltas()
    {
        List<Long> deltas = new ArrayList<Long>();

        for (int i = 1; i < Integer.MAX_VALUE; i++)
        {
            long delta = (long) (Math.pow(2, i) - 1);
            if (delta > Integer.MAX_VALUE)
            {
                break;
            }

            deltas.add(delta);
        }

        System.out.println(deltas);
    }

    /**
     * 生成Sedgewick增量序列
     * 参考：http://blog.csdn.net/pacosonswjtu/article/details/52093398
     * 参考：http://blog.csdn.net/foliciatarier/article/details/53891144
     */
    private static void genSedgewickDeltas()
    {
        List<Long> deltas = new ArrayList<Long>();

        int startup1 = 0;
        int startup2 = 2;
        for (int i = 0; i < Integer.MAX_VALUE; i++)
        {
            if (i % 2 == 0)
            {
                long delta = (long) (9 * Math.pow(4, startup1) - 9 * Math.pow(2, startup1) + 1);
                if (delta > Integer.MAX_VALUE)
                {
                    break;
                }

                deltas.add(delta);
                startup1++;
            }
            else
            {
                long delta = (long) (Math.pow(4, startup2) - 3 * Math.pow(2, startup2) + 1);
                if (delta > Integer.MAX_VALUE)
                {
                    break;
                }

                deltas.add(delta);
                startup2++;
            }
        }

        System.out.println(deltas);
    }

    public static void main(String[] args)
    {
        int[] n = new int[] { 6, 5, 3, 1, 8, 7, 2, 4 };
        ShellInsertionSort.sort(n);
        System.out.println(Arrays.toString(n));

        genShellDeltas();
        genHibbardDeltas();
        genSedgewickDeltas();
    }
}
