package com.xdc.basic.algorithm.basic.math;

import java.util.ArrayList;
import java.util.List;

/**
 * 幂集，所谓幂集(Power Set)， 就是原集合中所有的子集(包括全集和空集)构成的集族。
 */
public class PowerSet
{
    /**
     * 幂集。(递归)
     * 
     * @param elements
     *            元素集合。
     * @param m
     *            递归时使用的临时变量，用于记录已处理的位置。
     * @param powerset
     *            递归时使用的临时缓存。
     * @param powersets
     *            所有幂集。
     */
    public static <T> void powerset(List<T> elements, int m, List<T> powerset, List<List<T>> powersets)
    {
        if (elements.size() == m)
        {
            powersets.add(new ArrayList<T>(powerset));
            return;
        }

        powerset(elements, m + 1, powerset, powersets);

        powerset.add(elements.get(m));
        powerset(elements, m + 1, powerset, powersets);
        powerset.remove(powerset.size() - 1);
    }

    /**
     * 幂集。(增量构造法)
     */
    public static List<List<String>> powerset(List<String> elements)
    {
        List<List<String>> powersets = new ArrayList<List<String>>();

        powersets.add(new ArrayList<String>());

        for (int i = 0; i < elements.size(); i++)
        {
            int size = powersets.size();
            for (int j = 0; j < size; j++)
            {
                List<String> powerset = new ArrayList<String>(powersets.get(j));
                powerset.add(elements.get(i));
                powersets.add(powerset);
            }
        }

        return powersets;
    }

    /**
     * 幂集。(二进制法)
     */
    public static List<List<String>> powerset2(List<String> elements)
    {
        List<List<String>> powersets = new ArrayList<List<String>>();

        for (int i = 0; i < (1 << elements.size()); i++) // 幂集个数为2的元素个数次幂。
        {
            List<String> powerset = new ArrayList<String>();
            for (int j = 0; j < elements.size(); j++)
            {
                if ((i & (1 << j)) != 0) // 从i的低位到高位，依次判断该位是否为1。
                {
                    powerset.add(elements.get(j));
                }
            }
            powersets.add(powerset);
        }

        return powersets;
    }

    /**
     * 幂集。(二进制法)
     */
    public static List<List<String>> powerset3(List<String> elements)
    {
        List<List<String>> powersets = new ArrayList<List<String>>();

        int[] bits = new int[elements.size()];

        for (int i = 0; i < (1 << elements.size()); i++) // 幂集个数为2的元素个数次幂。
        {
            List<String> powerset = new ArrayList<String>();
            for (int j = 0; j < bits.length; j++)
            {
                if (bits[bits.length - 1 - j] == 1) // 从bits的低位到高位，依次判断该位是否为1。
                {
                    powerset.add(elements.get(j));
                }
            }
            powersets.add(powerset);

            // 模拟二进制加法。
            for (int j = bits.length - 1; j >= 0; j--)
            {
                if (bits[j] == 0)
                {
                    bits[j] = 1;
                    break;
                }
                else
                {
                    bits[j] = 0;
                }
            }
        }

        return powersets;
    }

    public static void main(String[] args)
    {
        List<String> elements = new ArrayList<String>();
        elements.add("1");
        elements.add("2");
        elements.add("3");
        elements.add("4");

        List<String> powerset1 = new ArrayList<String>();
        List<List<String>> powersets1 = new ArrayList<List<String>>();
        powerset(elements, 0, powerset1, powersets1);
        System.out.println(powersets1);

        List<List<String>> powersets2 = powerset(elements);
        System.out.println(powersets2);
    }
}
