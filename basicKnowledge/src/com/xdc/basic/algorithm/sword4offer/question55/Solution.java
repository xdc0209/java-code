package com.xdc.basic.algorithm.sword4offer.question55;

import java.util.LinkedList;

public class Solution
{
    /**
     * 面试题55：二叉树的深度 题目一：二叉树的深度。
     */
    public static int TreeDepth(TreeNode root)
    {
        if (root == null)
        {
            return 0;
        }

        int leftDepth = TreeDepth(root.left);
        int rightDepth = TreeDepth(root.right);

        return leftDepth > rightDepth ? leftDepth + 1 : rightDepth + 1;
    }

    /**
     * 面试题55：二叉树的深度 题目二：平衡二叉树。
     * 先序遍历二叉树，在遍历过程获取左右子树的高度，进而判断每个节点是否平衡。此解法每个节点都会多次访问，效率不高。
     */
    public static boolean IsBalanced_Solution(TreeNode root)
    {
        if (root == null)
        {
            return true;
        }

        return -1 <= TreeDepth(root.left) - TreeDepth(root.right) && TreeDepth(root.left) - TreeDepth(root.right) <= 1
                && IsBalanced_Solution(root.left) && IsBalanced_Solution(root.right);
    }

    /**
     * 面试题55：二叉树的深度 题目二：平衡二叉树。
     * 后序遍历二叉树，在遍历过程中，根据左右子树的树高，评断是否平衡。
     */
    public static boolean IsBalanced_Solution2(TreeNode root)
    {
        return IsBalanced_Solution2Core(root) != -1;
    }

    /**
     * 如果是平衡的树返回树高，否则返回-1。
     */
    private static int IsBalanced_Solution2Core(TreeNode root)
    {
        if (root == null)
        {
            return 0;
        }

        int left = IsBalanced_Solution2Core(root.left);
        int ritht = IsBalanced_Solution2Core(root.right);

        if (left != -1 && ritht != -1 && -1 <= left - ritht && left - ritht <= 1)
        {
            return left > ritht ? left + 1 : ritht + 1;
        }

        return -1;
    }

    public static void main(String[] args)
    {
        final Integer X = null;
        TreeNode root = TreeNode.createTree(5, 3, 2, X, X, 4, X, X, 7, 6, X, X, 8, X, X);
        TreeNode.treeGraph(root);
        System.out.println();

        System.out.println(TreeDepth(root));

        System.out.println(IsBalanced_Solution2(root));
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
