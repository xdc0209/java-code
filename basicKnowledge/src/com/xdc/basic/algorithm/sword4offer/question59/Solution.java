package com.xdc.basic.algorithm.sword4offer.question59;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Solution
{
    /**
     * 面试题59：队列的最大值 题目一：滑动窗口的最大值。
     */
    public static ArrayList<Integer> maxInWindows(int[] num, int size)
    {
        // 特殊情况处理。
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        if (num == null || num.length == 0 || size <= 0)
        {
            return numbers;
        }

        // 用来保存可能是滑动窗口最大值的数字的下标。
        Deque<Integer> deque = new LinkedList<Integer>();

        for (int i = 0; i < num.length; i++)
        {
            // 如果已有数字小于待存入的数据，这些数字已经不可能是滑动窗口的最大值，因此它们将会依次地从队尾删除。
            while (!deque.isEmpty() && num[deque.peekLast()] <= num[i])
            {
                deque.pollLast();
            }

            deque.offerLast(i);

            // 如果队列的头部元素已经从滑动窗口里滑出，滑出的数字需要从队列的头部删除。
            while (!deque.isEmpty() && deque.peekLast() - deque.peekFirst() >= size)
            {
                deque.pollFirst();
            }

            if (i >= size - 1)
            {
                numbers.add(num[deque.peekFirst()]);
            }
        }

        return numbers;
    }

    public static void main(String[] args)
    {
        System.out.println(maxInWindows(newArray(2, 3, 4, 2, 6, 2, 5, 1), 3));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
