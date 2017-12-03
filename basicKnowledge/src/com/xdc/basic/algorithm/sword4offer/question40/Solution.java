package com.xdc.basic.algorithm.sword4offer.question40;

import java.util.ArrayList;
import java.util.TreeSet;

public class Solution
{
    public static ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k)
    {
        ArrayList<Integer> mins = new ArrayList<Integer>();
        if (input == null || k < 1 || input.length < k)
        {
            return mins;
        }

        int[] bigTopHeap = new int[k];

        int indexInput = 0;
        int indexHeap = 0;

        // 前k个元素直接入堆。
        while (indexInput < input.length && indexHeap < bigTopHeap.length)
        {
            bigTopHeap[indexHeap] = input[indexInput];

            indexHeap++;
            indexInput++;
        }

        // 从堆的最后一个元素的父节点开始调整堆，直到调整完第一个元素时，整个堆变成大顶堆。
        for (int i = (indexHeap - 1 - 1) / 2; i >= 0; i--)
        {
            heapAdjust(bigTopHeap, i, indexHeap);
        }

        // 后续元素尝试入堆。
        while (indexInput < input.length)
        {
            if (input[indexInput] < bigTopHeap[0])
            {
                bigTopHeap[0] = input[indexInput];
                heapAdjust(bigTopHeap, 0, indexHeap);
            }

            indexInput++;
        }

        // 对堆排序。
        for (int i = indexHeap - 1; i >= 0; i--)
        {
            swap(bigTopHeap, 0, i);

            heapAdjust(bigTopHeap, 0, i);
        }

        // 输出结果。
        for (int i = 0; i < indexHeap; i++)
        {
            mins.add(bigTopHeap[i]);
        }

        return mins;
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

    public static ArrayList<Integer> GetLeastNumbers_Solution2(int[] input, int k)
    {
        ArrayList<Integer> mins = new ArrayList<Integer>();
        if (input == null || k < 1 || input.length < k)
        {
            return mins;
        }

        // 使用TreeSet模拟小顶堆。
        TreeSet<Integer> treeSet = new TreeSet<Integer>();

        for (int i = 0; i < input.length; i++)
        {
            if (i < k) // 前k个元素直接入堆。
            {
                treeSet.add(input[i]);
            }
            else // 后续元素尝试入堆。
            {
                if (input[i] < treeSet.last())
                {
                    treeSet.pollLast();
                    treeSet.add(input[i]);
                }
            }
        }

        // 输出结果。
        for (Integer integer : treeSet)
        {
            mins.add(integer);
        }

        return mins;
    }

    public static void main(String[] args)
    {
        System.out.println(GetLeastNumbers_Solution(newArray(4, 5, 1, 6, 2, 7, 3, 8), 4));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
