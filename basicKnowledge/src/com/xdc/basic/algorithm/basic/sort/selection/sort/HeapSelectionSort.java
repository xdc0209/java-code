package com.xdc.basic.algorithm.basic.sort.selection.sort;

import java.util.Arrays;

/**
 * 堆排序
 */
public class HeapSelectionSort
{
    public static void sort(int[] n)
    {
        // 从数组的最后一个元素的父节点开始调整数组，直到调整完第一个元素时，整个数组变成大顶堆
        for (int i = (n.length - 1 - 1) / 2; i >= 0; i--)
        {
            heapAdjust(n, i, n.length);
        }

        for (int i = n.length - 1; i >= 0; i--)
        {
            // 交换堆顶和堆的最后一个元素
            swap(n, 0, i);

            // 调整数组为大顶堆
            heapAdjust(n, 0, i);
        }
    }

    /**
     * 已知n[start,end)中的记录除n[start]之外均满足堆的定义。本函数调整n[start]，使n[start,end)成为一个大顶堆
     * 
     * @param n
     *            数组
     * @param start
     *            起始元素位置(包含此元素)
     * @param end
     *            终止元素位置(不包含此元素)
     */
    private static void heapAdjust(int[] n, int start, int end)
    {
        // 记录堆顶元素的值
        int heap = n[start];

        // 在以0为起始的数组体系中，如果某个元素的坐标为i，那么它的左孩子的坐标为2*i+1，它的右孩子的坐标为2*i+2
        int parent = start;
        int leftChild = 2 * parent + 1;
        int rightChild = leftChild + 1;
        while (leftChild < end)
        {
            // 记录左右孩子中的较大者
            int maxChild = leftChild;
            if (rightChild < end && n[leftChild] < n[rightChild])
            {
                maxChild = rightChild;
            }

            // 如果堆顶元素大于等于左右孩子中的较大者，则停止循环
            if (heap >= n[maxChild])
            {
                break;
            }

            // 左右孩子中的较大者上移
            n[parent] = n[maxChild];

            parent = maxChild;
            leftChild = 2 * parent + 1;
            rightChild = leftChild + 1;
        }

        // 堆顶元素下移到应在的位置
        n[parent] = heap;
    }

    private static void swap(int[] n, int i, int j)
    {
        int temp = n[i];
        n[i] = n[j];
        n[j] = temp;
    }

    public static void main(String[] args)
    {
        int[] n = new int[] { 6, 5, 3, 1, 8, 7, 2, 4 };
        HeapSelectionSort.sort(n);
        System.out.println(Arrays.toString(n));
    }
}
