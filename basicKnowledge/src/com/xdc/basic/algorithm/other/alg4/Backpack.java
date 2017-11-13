package com.xdc.basic.algorithm.other.alg4;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 0-1背包问题。
 */
public class Backpack
{
    /**
     * 0-1背包问题的任意一个最优解(其实最优解可能是多个)。(动态规划)
     */
    public static List<Commodity> bag1(List<Commodity> commodities, int capacity)
    {
        // values[i][j]表示当容量为j，前i-1个物品可以组成的总价值的最大值。特别地，为了编程简单，values[0][j]表示无可选物品，values[i][0]表示背包容量为0，这两种情况总价值都是0。
        int[][] values = new int[commodities.size() + 1][capacity + 1];
        for (int i = 1; i <= commodities.size(); i++)
        {
            Commodity commodity = commodities.get(i - 1);
            for (int j = 1; j <= capacity; j++)
            {
                if (commodity.weight > j) // 当前物品装不下。
                {
                    values[i][j] = values[i - 1][j];
                }
                else // 当前物品装得下。
                {
                    // 比较当前物品装和不装的情况下总价值的大小。
                    if (values[i - 1][j - commodity.weight] + commodity.value > values[i - 1][j])
                    {
                        values[i][j] = values[i - 1][j - commodity.weight] + commodity.value; // 装当前物品可使总价值更大化。
                    }
                    else
                    {
                        values[i][j] = values[i - 1][j]; // 不装当前物品可使总价值更大化。
                    }
                }
            }
        }

        // 根据总价值矩阵，找到其中一组最优解。如果改用回溯法，可以找到所有最优解。
        Stack<Commodity> stack = new Stack<Commodity>();
        for (int i = commodities.size(), j = capacity; i > 0 && j > 0;)
        {
            if (values[i][j] == values[i - 1][j])
            {
                i = i - 1;
            }
            else
            {
                Commodity commodity = commodities.get(i - 1);
                stack.add(commodity);
                i = i - 1;
                j = j - commodity.weight;
            }
        }

        List<Commodity> selectedCommodities = new ArrayList<Commodity>();
        while (!stack.isEmpty())
        {
            selectedCommodities.add(stack.pop());
        }

        return selectedCommodities;
    }

    /**
     * 0-1背包问题的任意一个最优解(其实最优解可能是多个)。(枚举法)
     */
    public static List<Commodity> bag2(List<Commodity> commodities, int capacity)
    {
        class Bag
        {
            int             weight;
            int             value;
            List<Commodity> commodities = new ArrayList<Commodity>();

            public Bag()
            {
            }

            public Bag(int weight, int value, List<Commodity> commodities, Commodity commodity)
            {
                this.weight = weight;
                this.value = value;
                this.commodities = new ArrayList<Commodity>(commodities);
                this.commodities.add(commodity);
            }

            @Override
            public String toString()
            {
                return String.format("Bag [weight=%s, value=%s, commodities=%s]", weight, value, commodities);
            }
        }

        List<Bag> bags = new ArrayList<Bag>();

        Bag bestBag = new Bag();
        bags.add(bestBag);

        for (int i = 0; i < commodities.size(); i++) // 0-1背包问题的解空间为一棵幂集树，在此循环中显式地构造幂集。
        {
            int size = bags.size();
            for (int j = 0; j < size; j++)
            {
                Bag bag = bags.get(j);
                Commodity commodity = commodities.get(i);
                if (bag.weight + commodity.weight <= capacity) // 如果背包放不下当前物品，进行剪枝。
                {
                    int newWeight = bag.weight + commodity.weight;
                    int newValue = bag.value + commodity.value;
                    Bag newBag = new Bag(newWeight, newValue, bag.commodities, commodity);
                    bags.add(newBag);

                    if (newBag.value > bestBag.value)
                    {
                        bestBag = newBag;
                    }
                }
            }
        }

        return bestBag.commodities;
    }

    public static void main(String[] args)
    {
        List<Commodity> commodities = new ArrayList<Commodity>();
        commodities.add(new Commodity("A", 2, 6));
        commodities.add(new Commodity("B", 2, 3));
        commodities.add(new Commodity("C", 6, 5));
        commodities.add(new Commodity("D", 5, 4));
        commodities.add(new Commodity("E", 4, 6));

        List<Commodity> selectedCommodities1 = bag1(commodities, 10);
        System.out.println(selectedCommodities1);

        List<Commodity> selectedCommodities2 = bag2(commodities, 10);
        System.out.println(selectedCommodities2);
    }
}

class Commodity
{
    String name;
    int    weight;
    int    value;

    public Commodity(String name, int weight, int value)
    {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    @Override
    public String toString()
    {
        return String.format("Commodity [name=%s, weight=%s, value=%s]", name, weight, value);
    }
}
