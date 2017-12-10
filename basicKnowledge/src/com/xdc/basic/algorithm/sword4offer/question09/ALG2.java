package com.xdc.basic.algorithm.sword4offer.question09;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 用两个队列模拟栈
 */
public class ALG2
{
    Queue<Integer> queue1 = new LinkedBlockingQueue<Integer>();
    Queue<Integer> queue2 = new LinkedBlockingQueue<Integer>();

    /**
     * 入栈
     */
    public void push(int node)
    {
        if (queue1.isEmpty())
        {
            queue1.add(node);
            while (!queue2.isEmpty())
            {
                queue1.add(queue2.remove());
            }
            return;
        }

        if (queue2.isEmpty())
        {
            queue2.add(node);
            while (!queue1.isEmpty())
            {
                queue2.add(queue1.remove());
            }
            return;
        }

        throw new RuntimeException("Impossible to run here!");
    }

    /**
     * 出栈
     */
    public int pop()
    {
        if (!queue1.isEmpty())
        {
            return queue1.remove();
        }

        if (!queue2.isEmpty())
        {
            return queue2.remove();
        }

        throw new RuntimeException("Pop failed, due to stack is empty.");
    }

    public static void main(String[] args)
    {
        ALG2 alg = new ALG2();

        alg.push(1);
        alg.push(2);
        alg.push(3);

        System.out.println(alg.pop());
        System.out.println(alg.pop());
        System.out.println(alg.pop());
    }
}
