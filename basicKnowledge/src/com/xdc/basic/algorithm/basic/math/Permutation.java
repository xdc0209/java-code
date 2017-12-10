package com.xdc.basic.algorithm.basic.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 摘自：https://baike.baidu.com/item/%E6%8E%92%E5%88%97/7804523?fr=aladdin
 * 
 * 排列，一般地，从n个不同元素中取出m(0≤m≤n)个元素，按照一定的顺序排成一列，叫做从n个元素中取出m个元素的一个排列(permutation)。特别地，当m=n时，这个排列被称作全排列(all permutation)。
 * 
 * 重复排列(permutation with repetiton)是一种特殊的排列。从n个不同元素中可重复地选取m个元素。按照一定的顺序排成一列，称作从n个元素中取m个元素的可重复排列。当且仅当所取的元素相同，且元素的排列顺序也相同，则两个排列相同。
 * 
 * 注意：排列与重复排列是两个概念，本程序只是排列的。
 */
public class Permutation
{
    /**
     * 从n取m组成排列。(递归)
     * 
     * @param elements
     *            元素集合。
     * @param m
     *            取m个元素。
     * @param permutate
     *            递归时使用的临时缓存。
     * @param permutates
     *            所有排列。
     */
    public static <T> void permutate(List<T> elements, int m, List<T> permutate, List<List<T>> permutates)
    {
        if (m < 0 || m > elements.size())
        {
            throw new IllegalArgumentException("M is illegal.");
        }

        if (permutate.size() == m)
        {
            permutates.add(new ArrayList<T>(permutate));
            return;
        }

        for (int i = 0; i < elements.size(); i++)
        {
            T element = elements.get(i);
            if (!permutate.contains(element))
            {
                permutate.add(element);

                permutate(elements, m, permutate, permutates);

                permutate.remove(permutate.size() - 1);
            }
        }
    }

    /**
     * 从n取m组成排列。(栈)
     * 
     * 算法思路：将n个元素看出一个完全图，从各个顶点依次深度遍历。
     */
    public static <T> List<List<T>> permutate(List<T> elements, int m)
    {
        if (m < 0 || m > elements.size())
        {
            throw new IllegalArgumentException("M is illegal.");
        }

        List<List<T>> permutates = new ArrayList<List<T>>();

        if (m == 0)
        {
            List<T> permutate = new ArrayList<T>();
            permutates.add(permutate);
            return permutates;
        }

        for (int i = 0; i < elements.size(); i++)
        {
            Stack<T> stack1 = new Stack<T>(); // 存储元素。
            Stack<Integer> stack2 = new Stack<Integer>(); // 存储stack1中对应位置的元素刚刚访问过的邻接元素。

            stack1.add(elements.get(i));
            stack2.add(-1);

            while (!stack1.isEmpty())
            {
                if (stack1.size() == m)
                {
                    List<T> permutate = new ArrayList<T>();
                    for (int j = 0; j < stack1.size(); j++)
                    {
                        permutate.add(stack1.get(j));
                    }
                    permutates.add(permutate);

                    stack1.pop();
                    stack2.pop();

                    continue;
                }

                T element = stack1.pop();
                Integer k = stack2.pop();

                for (k = k + 1; k < elements.size(); k++)
                {
                    T next = elements.get(k);
                    if (!stack1.contains(next) && !element.equals(next))
                    {
                        stack1.push(element);
                        stack2.push(k);

                        stack1.push(next);
                        stack2.push(-1);

                        break;
                    }
                }
            }
        }

        return permutates;
    }

    public static void main(String[] args)
    {
        List<String> elements = new ArrayList<String>();
        elements.add("1");
        elements.add("2");
        elements.add("3");
        elements.add("4");

        List<String> permutate1 = new ArrayList<String>();
        List<List<String>> permutates1 = new ArrayList<List<String>>();
        permutate(elements, 2, permutate1, permutates1);
        System.out.println(permutates1);

        List<List<String>> permutate2 = permutate(elements, 2);
        System.out.println(permutate2);
    }
}
