package com.xdc.basic.algorithm.basic.tree.losertree;

/**
 * 败者树：
 * 是完全二叉树， 因此数据结构可以采用一维数组。其元素为1个冠军节点、k-1个比较节点(非叶子节点)、k个数据节点(叶子节点)，共2k个。
 * 为了支持不同数据类型的数据节点，使用两个一维数组实现败者树。tree[0]为冠军结点，tree[1]--tree[k-1]为比较结点，data[0]--data[k-1]为叶子结点。
 * 败者树主要用于K路归并排序，在学习败者树的接口时想想在K路归并排序中的应用，更容易理解接口设计思想。
 * 
 * 败者树特点：
 * 比较节点记录失败者，胜利者参加下一轮比赛。最终的胜利者记录在冠军节点。
 * 
 * 特别说明：
 * 1. 如果值相等，则索引小的为胜利者。
 * 2. null表示此位置无元素，在败者树中始终是败者。
 * 3. 如果败者树中所有的元素都已为null，此时无真正的胜利者，胜利者索引使用特定值-1。
 */
public class LoserTree<T extends Comparable<T>>
{
    private int   type; // 1表示值大的为胜利者，即tree[0]为最大元素；-1表示值小的为胜利者，即tree[0]为最小元素。
    private T[]   data; // 数据节点(叶子节点)，存放数据。
    private int[] tree; // 比较节点(非叶子节点)，存放数据的索引。

    @SafeVarargs
    public static <S extends Comparable<S>> LoserTree<S> newMaxLoserTree(S... data)
    {
        return new LoserTree<S>(1, data);
    }

    @SafeVarargs
    public static <S extends Comparable<S>> LoserTree<S> newMinLoserTree(S... data)
    {
        return new LoserTree<S>(-1, data);
    }

    @SafeVarargs
    private LoserTree(int type, T... data)
    {
        this.type = type;
        this.data = data;
        this.tree = new int[data.length];

        initLoserTree();
    }

    /**
     * 获取当前胜利者的索引。在败者树中null的有特殊含义，表示原来胜者的位置上已经无下一元素，为了适应败者树的性质，我们认为null始终是败者。
     * 当胜利者的位置为null时，表明败者树中所有的元素都已为null，此时无真正的胜利者，此时返回-1。
     */
    public int winner()
    {
        return data[tree[0]] != null ? tree[0] : -1;
    }

    /**
     * 获取当前的胜利者的值，并使用下一元素接替胜利者的位置，并选举胜利者。如果无下一元素，则使用null作为接替者。
     */
    public T adjust(T t)
    {
        T winner = data[tree[0]];

        data[tree[0]] = t;
        adjust(tree[0]);

        return winner;
    }

    /**
     * 初始化败者树。
     */
    private void initLoserTree()
    {
        // 寻找初始胜利者，如果值相等，则索引小的为胜利者。
        int winner = 0;
        for (int i = 1; i < data.length; i++)
        {
            if (compare(data[i], data[winner]) > 0)
            {
                winner = i;
            }
        }

        // 初始所有比较节点的值为胜利者。
        for (int i = 0; i < tree.length; i++)
        {
            tree[i] = winner;
        }

        // 调整所有比较节点的值。
        for (int i = data.length - 1; i >= 0; i--)
        {
            adjust(i);
        }
    }

    /**
     * 调整败者树。
     */
    private void adjust(int i)
    {
        // winner是数据节点的索引，其含义是每次比较的胜利者。
        int winner = i;

        // challenger是比较节点的索引，其含义是上次调整的失败者，这次调整重新来挑战的。
        int challenger = (data.length + winner) / 2;

        while (challenger > 0)
        {
            // 如果树节点胜利，则树节点成为新的胜利者，上次的胜利者留在树节点。特别地，如果值相等，则索引小的为胜利者。
            int cmp = compare(data[tree[challenger]], data[winner]);
            if (cmp > 0 || (cmp == 0 && tree[challenger] < winner))
            {
                int temp = winner;
                winner = tree[challenger];
                tree[challenger] = temp;
            }

            challenger = challenger / 2; // 比较节点的父节点。
        }

        tree[0] = winner;
    }

    /**
     * 比较元素。如果返回值大于0，则t1是胜者；如果返回值小于0，则t2是胜者；如果返回值等于0，则平局。
     */
    private int compare(T t1, T t2)
    {
        if (t1 == t2) // t1与t2相等，包含同时为空和同时不为空两种情况。
        {
            return 0;
        }

        if (t1 == null) // 根据败者树的性质，空值为败者。t1为空，t2不为空，则t1为败者。
        {
            return -1;
        }

        if (t2 == null) // 根据败者树的性质，空值为败者。t1不为空，t2为空，则t2为败者。
        {
            return 1;
        }

        return t1.compareTo(t2) * type;
    }

    public static void main(String[] args)
    {
        LoserTree<Integer> minLoserTree = LoserTree.newMinLoserTree(10, 9, 20, 6, 12);
        System.out.println(minLoserTree);

        LoserTree<Integer> maxLoserTree = LoserTree.newMaxLoserTree(10, 9, 20, 6, 12);
        System.out.println(maxLoserTree);
    }
}
