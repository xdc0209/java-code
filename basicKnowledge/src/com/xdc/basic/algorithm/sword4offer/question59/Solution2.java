package com.xdc.basic.algorithm.sword4offer.question59;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 面试题59：队列的最大值 题目二：队列的最大值
 */
public class Solution2
{
    Deque<Integer> dataDeque = new LinkedList<Integer>();
    Deque<Integer> maxDeque  = new LinkedList<Integer>();

    /**
     * 加入队尾，叫enqueue更合适
     */
    public void push(int node)
    {
        while (!maxDeque.isEmpty() && maxDeque.peekLast() < node)
        {
            maxDeque.pollLast();
        }

        maxDeque.offerLast(node);
        dataDeque.offerLast(node);
    }

    /**
     * 移除队首，叫dequeue更合适
     */
    public int pop()
    {
        if (dataDeque.isEmpty())
        {
            throw new RuntimeException("The queue is empty.");
        }

        if (dataDeque.peekFirst() == maxDeque.peekFirst())
        {
            maxDeque.pollFirst();
        }

        return dataDeque.pollFirst();
    }

    /**
     * 队首的值。
     */
    public int top()
    {
        if (dataDeque.isEmpty())
        {
            throw new RuntimeException("The queue is empty.");
        }

        return dataDeque.peekFirst();
    }

    /**
     * 队列中的最大值。
     */
    public int max()
    {
        if (dataDeque.isEmpty())
        {
            throw new RuntimeException("The queue is empty.");
        }

        return maxDeque.peekFirst();
    }

    public static void main(String[] args)
    {
        Solution2 solution2 = new Solution2();

        solution2.push(6);
        System.out.println(solution2.max());
        solution2.push(2);
        System.out.println(solution2.max());
        solution2.push(3);
        System.out.println(solution2.max());
        solution2.push(6);
        System.out.println(solution2.max());
        solution2.pop();
        System.out.println(solution2.max());
        solution2.pop();
        System.out.println(solution2.max());
        solution2.pop();
        System.out.println(solution2.max());
        solution2.pop();

        solution2.push(6);
        System.out.println(solution2.max());
        solution2.push(2);
        System.out.println(solution2.max());
        solution2.push(3);
        System.out.println(solution2.max());
        solution2.pop();
        System.out.println(solution2.max());
        solution2.pop();
        System.out.println(solution2.max());
        solution2.push(6);
        System.out.println(solution2.max());
        solution2.pop();
        System.out.println(solution2.max());
        solution2.pop();
    }
}
