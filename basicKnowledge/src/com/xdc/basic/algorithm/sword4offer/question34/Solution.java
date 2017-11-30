package com.xdc.basic.algorithm.sword4offer.question34;

import java.util.ArrayList;

public class Solution
{
    public static ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target)
    {
        ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>(); // 最终路径集。
        ArrayList<Integer> path = new ArrayList<Integer>(); // 临时路径。

        FindPathCore(root, target, path, paths);

        return paths;
    }

    private static void FindPathCore(TreeNode root, int target, ArrayList<Integer> path,
            ArrayList<ArrayList<Integer>> paths)
    {
        if (root == null)
        {
            return;
        }

        path.add(root.val);

        // 当前节点为叶子节点，且节点的值等于期望的值。
        if (root.left == null && root.right == null && root.val == target)
        {
            paths.add(new ArrayList<Integer>(path));
        }

        FindPathCore(root.left, target - root.val, path, paths);
        FindPathCore(root.right, target - root.val, path, paths);

        path.remove(path.size() - 1);
    }

    public static void main(String[] args)
    {
        TreeNode treeNode1 = new TreeNode(10);
        TreeNode treeNode2 = new TreeNode(-5);
        TreeNode treeNode3 = new TreeNode(12);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(17);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;

        TreeNode.treeGraph(treeNode1);
        System.out.println();

        System.out.println(FindPath(treeNode1, 22));
        System.out.println();
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
        return String.format("TreeNode [val=%s]", val);
    }

    /**
     * 输出树的图形
     */
    public static void treeGraph(TreeNode root)
    {
        TreeNode.treeGraph(root, 1);
    }

    /**
     * 输出树的图形
     */
    public static void treeGraph(TreeNode root, int deep)
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
