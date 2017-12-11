package com.xdc.basic.algorithm.sword4offer.question54;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Solution
{
    /**
     * 面试题54：二叉搜索树的第k个结点。
     * 先求树的左子树大小，再得到结果。时间复杂度O(n^2)，空间复杂度O(1)。这个算法浪费在size算法的结果重复计算了很多次。
     */
    public static TreeNode KthNode(TreeNode root, int k)
    {
        if (root == null || k <= 0)
        {
            return null;
        }

        int leftSize = treeSize(root.left);

        if (k <= leftSize)
        {
            return KthNode(root.left, k);
        }
        else if (k == leftSize + 1)
        {
            return root;
        }
        else
        {
            return KthNode(root.right, k - (leftSize + 1));
        }
    }

    private static int treeSize(TreeNode root)
    {
        if (root == null)
        {
            return 0;
        }

        int leftSize = treeSize(root.left);

        int rightSize = treeSize(root.right);

        return leftSize + 1 + rightSize;
    }

    /**
     * 面试题54：二叉搜索树的第k个结点。
     * 中序递归。时间复杂度O(n)，空间复杂度O(1)。
     */
    public static TreeNode KthNode2(TreeNode root, int k)
    {
        if (root == null || k <= 0)
        {
            return null;
        }

        List<TreeNode> treeNodes = new ArrayList<TreeNode>();

        KthNode2Core(root, treeNodes, k);

        return k <= treeNodes.size() ? treeNodes.get(k - 1) : null;
    }

    private static void KthNode2Core(TreeNode root, List<TreeNode> treeNodes, int k)
    {
        if (root == null)
        {
            return;
        }

        KthNode2Core(root.left, treeNodes, k);

        if (treeNodes.size() < k)
        {
            treeNodes.add(root);
        }

        if (treeNodes.size() == k)
        {
            return;
        }

        KthNode2Core(root.right, treeNodes, k);
    }

    public static void main(String[] args)
    {
        final Integer X = null;
        TreeNode root = TreeNode.createTree(5, 3, 2, X, X, 4, X, X, 7, 6, X, X, 8, X, X);
        TreeNode.treeGraph(root);
        System.out.println();

        System.out.println(KthNode(root, -1));
        System.out.println(KthNode(root, 0));
        System.out.println(KthNode(root, 1));
        System.out.println(KthNode(root, 2));
        System.out.println(KthNode(root, 3));
        System.out.println(KthNode(root, 4));
        System.out.println(KthNode(root, 5));
        System.out.println(KthNode(root, 6));
        System.out.println(KthNode(root, 7));
        System.out.println(KthNode(root, 8));
        System.out.println(KthNode(root, 9));
    }
}

class TreeNode
{
    int      val   = 0;
    TreeNode left  = null;
    TreeNode right = null;

    public TreeNode(int val)
    {
        this.val = val;
    }

    @Override
    public String toString()
    {
        return String.format("%s[%s,%s]", val, left, right);
    }

    /**
     * 使用先序序列创建树。
     */
    public static TreeNode createTree(Integer... values)
    {
        LinkedList<Integer> valueList = new LinkedList<Integer>();
        for (Integer value : values)
        {
            valueList.add(value);
        }

        return createTree(valueList);
    }

    /**
     * 使用先序序列创建树(内部使用)。
     */
    private static TreeNode createTree(LinkedList<Integer> valueList)
    {
        if (valueList == null || valueList.size() == 0)
        {
            return null;
        }

        Integer value = valueList.removeFirst();

        if (value == null)
        {
            return null;
        }

        TreeNode root = new TreeNode(value);

        root.left = createTree(valueList);
        root.right = createTree(valueList);

        return root;
    }

    /**
     * 输出树的图形。
     */
    public static void treeGraph(TreeNode root)
    {
        TreeNode.treeGraph(root, 1);
    }

    /**
     * 输出树的图形(内部使用)。
     */
    private static void treeGraph(TreeNode root, int deep)
    {
        if (root == null)
        {
            for (int i = 0; i < deep; i++)
            {
                System.out.print("....");
            }
            System.out.println("$");

            return;
        }

        // 输出右子树
        treeGraph(root.right, deep + 1);

        // 输出根节点
        for (int i = 0; i < deep; i++)
        {
            System.out.print("....");
        }
        System.out.println(root.val);

        // 输出左子树
        treeGraph(root.left, deep + 1);
    }
}
