package com.xdc.basic.algorithm.sword4offer.question57;

import java.util.ArrayList;

public class Solution
{
    /**
     * 面试题57：和为S的数字 题目一：和为S的两个数字。
     */
    public static ArrayList<Integer> FindNumbersWithSum(int[] array, int sum)
    {
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        if (array == null || array.length < 2)
        {
            return numbers;
        }

        int low = 0;
        int high = array.length - 1;
        while (low < high)
        {
            if (array[low] + array[high] > sum)
            {
                high--;
            }
            else if (array[low] + array[high] < sum)
            {
                low++;
            }
            else // array[low] + array[high] == sum
            {
                numbers.add(array[low]);
                numbers.add(array[high]);
                return numbers;
            }
        }

        return numbers;
    }

    /**
     * 面试题57：和为S的数字 题目二：和为S的连续正数序列。
     */
    public static ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum)
    {
        ArrayList<ArrayList<Integer>> seqs = new ArrayList<ArrayList<Integer>>();

        for (int i = (sum + 1) / 2; i >= 2; i--)
        {
            if (i % 2 == 0)
            {
                if (sum % i == 0)
                {
                    continue;
                }

                int low = sum / i;
                int high = sum / i + 1;
                if (low - ((i - 2) / 2) > 0 && (low + high) * (i / 2) == sum)
                {
                    ArrayList<Integer> seq = new ArrayList<Integer>();
                    for (int j = low - ((i - 2) / 2); j <= high + ((i - 2) / 2); j++)
                    {
                        seq.add(j);
                    }
                    seqs.add(seq);
                }
            }
            else
            {
                if (sum % i != 0)
                {
                    continue;
                }

                int mid = sum / i;
                if (mid - (i - 1) / 2 > 0 && mid * i == sum)
                {
                    ArrayList<Integer> seq = new ArrayList<Integer>();
                    for (int j = mid - (i - 1) / 2; j <= mid + (i - 1) / 2; j++)
                    {
                        seq.add(j);
                    }
                    seqs.add(seq);
                }
            }
        }

        return seqs;
    }

    /**
     * 面试题57：和为S的数字 题目二：和为S的连续正数序列。
     */
    public static ArrayList<ArrayList<Integer>> FindContinuousSequence2(int sum)
    {
        ArrayList<ArrayList<Integer>> seqs = new ArrayList<ArrayList<Integer>>();

        int low = 1;
        int high = 2;
        int mid = (sum + 1) / 2;
        int curSum = low + high;
        while (low < mid)
        {
            if (curSum == sum)
            {
                ArrayList<Integer> seq = new ArrayList<Integer>();
                for (int i = low; i <= high; i++)
                {
                    seq.add(i);
                }
                seqs.add(seq);
            }

            while (low < mid && curSum > sum)
            {
                curSum -= low;
                low++;

                if (curSum == sum)
                {
                    ArrayList<Integer> seq = new ArrayList<Integer>();
                    for (int i = low; i <= high; i++)
                    {
                        seq.add(i);
                    }
                    seqs.add(seq);
                }
            }

            high++;
            curSum += high;
        }

        return seqs;
    }

    public static void main(String[] args)
    {
        System.out.println(FindNumbersWithSum(newArray(2, 3, 5, 7, 8, 9), 6));
        System.out.println(FindNumbersWithSum(newArray(2, 3, 5, 7, 8, 9), 8));
        System.out.println(FindNumbersWithSum(newArray(2, 3, 5, 7, 8, 9), 9));
        System.out.println(FindNumbersWithSum(newArray(1, 2, 4, 7, 11, 15), 15));

        System.out.println(FindContinuousSequence(0));
        System.out.println(FindContinuousSequence(1));
        System.out.println(FindContinuousSequence(2));
        System.out.println(FindContinuousSequence(3));
        System.out.println(FindContinuousSequence(9));
        System.out.println(FindContinuousSequence(15));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
