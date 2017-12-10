package com.xdc.basic.algorithm.sword4offer.question41;

import java.util.TreeSet;

public class Solution2
{
    private TreeSet<Integer> mins = new TreeSet<Integer>(); // 中位数左边的数。
    private TreeSet<Integer> maxs = new TreeSet<Integer>(); // 中位数右边的数。

    public void Insert(Integer num)
    {
        if (((mins.size() + maxs.size()) & 1) == 0) // 如果当前总个数为偶数，则放到mins中。
        {
            if (!maxs.isEmpty() && maxs.first() < num)
            {
                mins.add(maxs.pollFirst());
                maxs.add(num);
            }
            else
            {
                mins.add(num);
            }
        }
        else // 如果当前总个数为奇数，则放到maxs中。
        {
            if (!mins.isEmpty() && mins.last() > num)
            {
                maxs.add(mins.pollLast());
                mins.add(num);
            }
            else
            {
                maxs.add(num);
            }
        }
    }

    public void Insert2(Integer num)
    {
        if (((mins.size() + maxs.size()) & 1) == 0) // 如果当前总个数为偶数，则放到mins中。
        {
            maxs.add(num); // 先放到maxs中。
            mins.add(maxs.pollFirst()); // 再将maxs中的最小值放到mins中。
        }
        else // 如果当前总个数为奇数，则放到maxs中。
        {
            mins.add(num); // 先放到mins中。
            maxs.add(mins.pollLast()); // 再将mins中的最大值放到maxs中。
        }
    }

    public Double GetMedian()
    {
        if (mins.size() + maxs.size() == 0)
        {
            return 0.0;
        }
        else if (((mins.size() + maxs.size()) & 1) == 0)
        {
            return (mins.last() + maxs.first()) / 2.0;
        }
        else
        {
            return mins.last() / 1.0;
        }
    }

    public static void main(String[] args)
    {
        Solution2 solution = new Solution2();
        solution.Insert(5);
        solution.Insert(1);
        solution.Insert(4);
        solution.Insert(7);
        solution.Insert(3);
        solution.Insert(9);
        solution.Insert(10);

        System.out.println(solution.GetMedian());
    }
}
