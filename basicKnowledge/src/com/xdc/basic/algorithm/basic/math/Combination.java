package com.xdc.basic.algorithm.basic.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 组合，与排列类似，唯一的区别就是不区分元素的顺序。
 * 
 * 重复组合，和排列一样，也有此概念。
 *
 * 注意：组合与重复组合是两个概念，本程序只是组合的。
 */
public class Combination
{
    /**
     * 从n取m组成组合。(递归)
     * 
     * @param elements
     *            元素集合。
     * @param m
     *            取m个元素。
     * @param combination
     *            递归时使用的临时缓存。
     * @param combinations
     *            所有组合。
     */
    public static <T extends Comparable<T>> void combination(List<T> elements, int m, List<T> combination,
            List<List<T>> combinations)
    {
        if (m < 0 || m > elements.size())
        {
            throw new IllegalArgumentException("M is illegal.");
        }

        if (combination.size() == m)
        {
            combinations.add(new ArrayList<T>(combination));
            return;
        }

        for (int i = 0; i < elements.size(); i++)
        {
            T element = elements.get(i);
            if (!combination.contains(element)
                    && (combination.isEmpty() || combination.get(combination.size() - 1).compareTo(element) < 0)) // 与排列相比，限制元素的顺序，进行剪枝。
            {
                combination.add(element);

                combination(elements, m, combination, combinations);

                combination.remove(combination.size() - 1);
            }
        }
    }

    /**
     * 从n取m组成排列。(栈)
     * 
     * 算法思路：将n个元素看出一个完全图，从各个顶点依次深度遍历。
     */
    public static <T extends Comparable<T>> List<List<T>> combination(List<T> elements, int m)
    {
        if (m < 0 || m > elements.size())
        {
            throw new IllegalArgumentException("M is illegal.");
        }

        List<List<T>> combinations = new ArrayList<List<T>>();

        if (m == 0)
        {
            List<T> combination = new ArrayList<T>();
            combinations.add(combination);
            return combinations;
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
                    List<T> combination = new ArrayList<T>();
                    for (int j = 0; j < stack1.size(); j++)
                    {
                        combination.add(stack1.get(j));
                    }
                    combinations.add(combination);

                    stack1.pop();
                    stack2.pop();

                    continue;
                }

                T element = stack1.pop();
                Integer k = stack2.pop();

                for (k = k + 1; k < elements.size(); k++)
                {
                    T next = elements.get(k);
                    if (!stack1.contains(next) && !element.equals(next) && element.compareTo(next) < 0) // 与排列相比，限制元素的顺序，进行剪枝。
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

        return combinations;
    }

    public static void main(String[] args)
    {
        List<String> elements = new ArrayList<String>();
        elements.add("1");
        elements.add("2");
        elements.add("3");
        elements.add("4");

        List<String> combination1 = new ArrayList<String>();
        List<List<String>> combinations1 = new ArrayList<List<String>>();
        combination(elements, 2, combination1, combinations1);
        System.out.println(combinations1);

        List<List<String>> combinations2 = combination(elements, 2);
        System.out.println(combinations2);
    }
}
