package com.xdc.basic.algorithm.sword4offer.question26;

public class Solution
{
    public static boolean HasSubtree(TreeNode root1, TreeNode root2)
    {
        if (root1 == null || root2 == null)
        {
            return false;
        }

        return DoesTree1haveTree2(root1, root2) || HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2);
    }

    public static boolean DoesTree1haveTree2(TreeNode root1, TreeNode root2)
    {
        if (root2 == null)
        {
            return true;
        }

        if (root1 == null)
        {
            return false;
        }

        return root1.val == root2.val && DoesTree1haveTree2(root1.left, root2.left)
                && DoesTree1haveTree2(root1.right, root2.right);
    }

    public static void main(String[] args)
    {
        TreeNode treeNode1 = new TreeNode(8);
        TreeNode treeNode2 = new TreeNode(8);
        TreeNode treeNode3 = new TreeNode(7);
        TreeNode treeNode4 = new TreeNode(9);
        TreeNode treeNode5 = new TreeNode(2);
        TreeNode treeNode6 = new TreeNode(4);
        TreeNode treeNode7 = new TreeNode(7);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;
        treeNode5.left = treeNode6;
        treeNode5.right = treeNode7;
        TreeNode.treeGraph(treeNode1);
        System.out.println();

        TreeNode treeNode11 = new TreeNode(8);
        TreeNode treeNode12 = new TreeNode(9);
        TreeNode treeNode13 = new TreeNode(2);
        treeNode11.left = treeNode12;
        treeNode11.right = treeNode13;
        TreeNode.treeGraph(treeNode11);
        System.out.println();

        System.out.println(HasSubtree(treeNode1, treeNode11));
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
