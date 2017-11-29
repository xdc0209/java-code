package com.xdc.basic.algorithm.sword4offer.question30;

import java.util.Stack;

public class Solution
{
    private Stack<Integer> dataStack = new Stack<Integer>();
    private Stack<Integer> minStack  = new Stack<Integer>();

    public void push(int value)
    {
        dataStack.push(value);

        if (minStack.isEmpty() || value <= minStack.peek())
        {
            minStack.push(value);
        }
        else
        {
            minStack.push(minStack.peek());
        }
    }

    public void pop()
    {
        if (!dataStack.isEmpty())
        {
            dataStack.pop();
        }

        if (!minStack.isEmpty())
        {
            minStack.pop();
        }
    }

    public int top()
    {
        return dataStack.peek();
    }

    public int min()
    {
        return minStack.peek();
    }

    public static void main(String[] args)
    {
        Solution stack = new Solution();
        stack.push(7);
        System.out.println(stack.top());
        System.out.println(stack.min());
        System.out.println();

        stack.push(6);
        System.out.println(stack.top());
        System.out.println(stack.min());
        System.out.println();

        stack.push(3);
        System.out.println(stack.top());
        System.out.println(stack.min());
        System.out.println();

        stack.push(8);
        System.out.println(stack.top());
        System.out.println(stack.min());
        System.out.println();

        stack.push(2);
        System.out.println(stack.top());
        System.out.println(stack.min());
        System.out.println();

        stack.push(5);
        System.out.println(stack.top());
        System.out.println(stack.min());
        System.out.println();
    }
}
