package com.xdc.basic.algorithm.sword4offer.question09;

import java.util.Stack;

/**
 * 用两个栈模拟队列
 */
public class ALG1
{
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    /**
     * 入队列，叫enqueue更合适
     */
    public void push(int node)
    {
        stack1.push(node);
    }

    /**
     * 出队列，叫dequeue更合适
     */
    public int pop()
    {
        if (stack2.isEmpty())
        {
            while (!stack1.isEmpty())
            {
                stack2.push(stack1.pop());
            }
        }

        if (stack2.isEmpty())
        {
            throw new RuntimeException("Dequeue failed, due to queue is empty.");
        }

        return stack2.pop();
    }

    public static void main(String[] args)
    {
        ALG1 alg = new ALG1();

        alg.push(1);
        alg.push(2);
        alg.push(3);

        System.out.println(alg.pop());
        System.out.println(alg.pop());
        System.out.println(alg.pop());
    }
}
