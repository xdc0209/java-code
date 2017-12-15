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
    public static <T> void permutate1(List<T> elements, int m, List<T> permutate, List<List<T>> permutates)
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

                permutate1(elements, m, permutate, permutates);

                permutate.remove(permutate.size() - 1);
            }
        }
    }

    /**
     * 从n取m组成排列。(栈)
     * 
     * 算法思路：将n个元素看出一个完全图，从各个顶点依次深度遍历。
     */
    public static <T> List<List<T>> permutate2(List<T> elements, int m)
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

    /**
     * 从n取m组成排列。(递归)
     * 
     * 网上关于排列的代码大多是这个代码，但是此代码有个缺点，就是排列不是按照字典序排序的，而且会修改原始列表的元素的顺序。
     * 
     * @param elements
     *            元素集合。
     * @param k
     *            递归辅助变量，外部调用时应填0。
     * @param m
     *            取m个元素。
     * @param permutates
     *            所有排列。
     */
    public static <T> void permutation3(List<T> elements, int k, int m, List<List<T>> permutates)
    {
        if (m < 0 || m > elements.size())
        {
            throw new IllegalArgumentException("M is illegal.");
        }

        if (k == m)
        {
            permutates.add(new ArrayList<T>(elements.subList(0, m)));
            return;
        }

        for (int i = k; i < elements.size(); i++)
        {
            swap(elements, k, i);

            permutation3(elements, k + 1, m, permutates);

            swap(elements, k, i);
        }
    }

    /**
     * 从n取m组成排列。
     * 
     * 算法思路：C++ STL算法next_permutation的思想。
     * 
     * 摘自：http://blog.csdn.net/v_july_v/article/details/6879101
     * 
     * @param elements
     *            元素集合。
     * @param m
     *            取m个元素。
     */
    public static <T extends Comparable<T>> List<List<T>> permutate4(List<T> elements, int m)
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

        // 计算“从n取m组成排列”在“全排列”中重复的个数，重复的排列只取一个。
        int count = 0;
        int repeat = 1;
        for (int i = elements.size() - m; i > 1; i--)
        {
            repeat = repeat * i;
        }

        while (true)
        {
            // 过滤重复排列。
            count++;
            if (count % repeat == 0)
            {
                permutates.add(new ArrayList<T>(elements.subList(0, m)));
            }

            // 对当前排列从后向前扫描，找到一对为升序的相邻元素，记为i和i+1。
            int i;
            for (i = elements.size() - 2; i >= 0; i--)
            {
                if (elements.get(i).compareTo(elements.get(i + 1)) < 0)
                {
                    break;
                }
            }

            // 如果不存在这样一对为升序的相邻元素，则所有排列均已找到，算法结束。
            if (i < 0)
            {
                break;
            }

            // 重新对当前排列从后向前扫描，找到第一个大于i的元素k。
            int k;
            for (k = elements.size() - 1; k > i; k--)
            {
                if (elements.get(k).compareTo(elements.get(i)) > 0)
                {
                    break;
                }
            }

            // 交换i和k，然后对从i+1开始到结束的子序列反转，则此时得到的新排列就为下一个字典序排列。
            swap(elements, i, k);
            reverse(elements, i + 1, elements.size());
        }

        return permutates;
    }

    private static <T> void swap(List<T> elements, int i, int j)
    {
        T t = elements.get(i);
        elements.set(i, elements.get(j));
        elements.set(j, t);
    }

    private static <T> void reverse(List<T> elements, int i, int j)
    {
        j--;
        while (i < j)
        {
            swap(elements, i, j);

            i++;
            j--;
        }
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
        permutate1(elements, 2, permutate1, permutates1);
        System.out.println(permutates1);

        List<List<String>> permutates2 = permutate2(elements, 2);
        System.out.println(permutates2);

        List<List<String>> permutates3 = new ArrayList<List<String>>();
        permutation3(elements, 0, 2, permutates3);
        System.out.println(permutates3);

        List<List<String>> permutates4 = permutate4(elements, 2);
        System.out.println(permutates4);
    }
}
