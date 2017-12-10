package com.xdc.basic.algorithm.sword4offer.question32;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class Solution
{
    /**
     * 面试题32：从上到下打印二叉树(层次遍历) 题目一：不分行从上往下打印二叉树。
     */
    public static ArrayList<Integer> PrintFromTopToBottom(TreeNode root)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        if (root == null)
        {
            return list;
        }

        // 手工模拟执行发现：其实队列中一直在两种状态切换，状态1：层次切换状态，此时队列中只有树的一层，且此层的全部节点都在队列中；状态2：节点拓展状态，此时队列中包含树的两层，当前层节点个数在减少，下一次节点个数在增加。
        Queue<TreeNode> queue = new LinkedBlockingQueue<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty())
        {
            TreeNode node = queue.remove();

            list.add(node.val);

            if (node.left != null)
            {
                queue.add(node.left);
            }

            if (node.right != null)
            {
                queue.add(node.right);
            }
        }

        return list;
    }

    /**
     * 面试题32：从上到下打印二叉树(层次遍历) 题目二：把二叉树打印成多行。
     */
    public static ArrayList<ArrayList<Integer>> Print21(TreeNode root)
    {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();

        if (root == null)
        {
            return list;
        }

        // 手工模拟执行发现：其实队列中一直在两种状态切换，状态1：层次切换状态，此时队列中只有树的一层，且此层的全部节点都在队列中；状态2：节点拓展状态，此时队列中包含树的两层，当前层节点个数在减少，下一次节点个数在增加。
        Queue<TreeNode> queue = new LinkedBlockingQueue<TreeNode>();
        queue.add(root);

        int currLevelCount = 1;
        int nextLevelCount = 0;
        ArrayList<Integer> currLevelList = new ArrayList<Integer>();

        while (!queue.isEmpty())
        {
            TreeNode node = queue.remove();

            currLevelList.add(node.val);

            if (node.left != null)
            {
                queue.add(node.left);
                nextLevelCount++;
            }

            if (node.right != null)
            {
                queue.add(node.right);
                nextLevelCount++;
            }

            if (currLevelList.size() == currLevelCount)
            {
                currLevelCount = nextLevelCount;
                nextLevelCount = 0;

                list.add(currLevelList);
                currLevelList = new ArrayList<Integer>();
            }
        }

        return list;
    }

    /**
     * 面试题32：从上到下打印二叉树(层次遍历) 题目二：把二叉树打印成多行。
     */
    public static ArrayList<ArrayList<Integer>> Print22(TreeNode root)
    {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();

        if (root == null)
        {
            return list;
        }

        // 手工模拟执行发现：其实队列中一直在两种状态切换，状态1：层次切换状态，此时队列中只有树的一层，且此层的全部节点都在队列中；状态2：节点拓展状态，此时队列中包含树的两层，当前层节点个数在减少，下一次节点个数在增加。
        Queue<TreeNode> queue = new LinkedBlockingQueue<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty())
        {
            ArrayList<Integer> currLevelList = new ArrayList<Integer>();
            list.add(currLevelList);

            int currLevelRemain = queue.size();
            while (currLevelRemain > 0)
            {
                TreeNode node = queue.remove();

                currLevelList.add(node.val);

                if (node.left != null)
                {
                    queue.add(node.left);
                }

                if (node.right != null)
                {
                    queue.add(node.right);
                }

                currLevelRemain--;
            }
        }

        return list;
    }

    /**
     * 面试题32：从上到下打印二叉树(层次遍历) 题目三：按之字形顺序打印二叉树。
     */
    public static ArrayList<ArrayList<Integer>> Print31(TreeNode root)
    {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();

        if (root == null)
        {
            return list;
        }

        Stack<TreeNode> stack1 = new Stack<TreeNode>(); // 奇数层。
        Stack<TreeNode> stack2 = new Stack<TreeNode>(); // 偶数层。

        stack1.push(root);

        while (!stack1.isEmpty() || !stack2.isEmpty())
        {
            ArrayList<Integer> list1 = new ArrayList<Integer>();
            while (!stack1.isEmpty())
            {
                TreeNode node = stack1.pop();

                list1.add(node.val);

                if (node.left != null)
                {
                    stack2.push(node.left);
                }

                if (node.right != null)
                {
                    stack2.push(node.right);
                }
            }
            if (!list1.isEmpty())
            {
                list.add(list1);
            }

            ArrayList<Integer> list2 = new ArrayList<Integer>();
            while (!stack2.isEmpty())
            {
                TreeNode node = stack2.pop();

                list2.add(node.val);

                if (node.right != null)
                {
                    stack1.push(node.right);
                }

                if (node.left != null)
                {
                    stack1.push(node.left);
                }
            }
            if (!list2.isEmpty())
            {
                list.add(list2);
            }
        }

        return list;
    }

    /**
     * 面试题32：从上到下打印二叉树(层次遍历) 题目三：按之字形顺序打印二叉树。(不推荐，因为里面存在reverse方法)
     */
    public static ArrayList<ArrayList<Integer>> Print32(TreeNode root)
    {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();

        if (root == null)
        {
            return list;
        }

        // 手工模拟执行发现：其实队列中一直在两种状态切换，状态1：层次切换状态，此时队列中只有树的一层，且此层的全部节点都在队列中；状态2：节点拓展状态，此时队列中包含树的两层，当前层节点个数在减少，下一次节点个数在增加。
        Queue<TreeNode> queue = new LinkedBlockingQueue<TreeNode>();
        queue.add(root);
        int level = 1;
        while (!queue.isEmpty())
        {
            ArrayList<Integer> currLevelList = new ArrayList<Integer>();
            list.add(currLevelList);

            int currLevelRemain = queue.size();
            while (currLevelRemain > 0)
            {
                TreeNode node = queue.remove();

                currLevelList.add(node.val);

                if (node.left != null)
                {
                    queue.add(node.left);
                }

                if (node.right != null)
                {
                    queue.add(node.right);
                }

                currLevelRemain--;
            }

            if ((level & 1) == 0)
            {
                Collections.reverse(currLevelList); // 网友：我有一次面试，算法考的就是之字形打印二叉树，用了reverse，直接被鄙视了，面试官说海量数据时效率根本就不行。
            }
            level++;
        }

        return list;
    }

    public static void main(String[] args)
    {
        TreeNode treeNode1 = new TreeNode(8);
        TreeNode treeNode2 = new TreeNode(6);
        TreeNode treeNode3 = new TreeNode(10);
        TreeNode treeNode4 = new TreeNode(5);
        TreeNode treeNode5 = new TreeNode(7);
        TreeNode treeNode6 = new TreeNode(9);
        TreeNode treeNode7 = new TreeNode(11);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;
        treeNode3.left = treeNode6;
        treeNode3.right = treeNode7;
        TreeNode.treeGraph(treeNode1);
        System.out.println();

        System.out.println(Print31(treeNode1));
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
