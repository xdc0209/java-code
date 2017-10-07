package com.xdc.basic.algorithm.sword4offer.question07;

public class ALG
{
    /**
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
     */
    public static TreeNode alg1(int[] pre, int[] in)
    {
        if (pre == null || pre.length == 0 || in == null || in.length == 0)
        {
            return null;
        }

        if (pre.length != in.length)
        {
            throw new RuntimeException("Invalid input.");
        }

        // 先序遍历序列第一个数字即为根节点的值
        TreeNode root = new TreeNode(pre[0]);

        for (int i = 1; i < pre.length; i++)
        {
            TreeNode curNode = root;
            TreeNode newNode = new TreeNode(pre[i]);

            int curNodeIndex = find1(in, curNode.val);
            int newNodeIndex = find1(in, newNode.val);

            if (curNodeIndex == -1)
            {
                throw new RuntimeException("Invalid input.");
            }

            if (newNodeIndex == -1)
            {
                throw new RuntimeException("Invalid input.");
            }

            // 从根开始左右扫描，确定新节点的位置
            while ((curNodeIndex > newNodeIndex && curNode.left != null)
                    || (curNodeIndex < newNodeIndex && curNode.right != null))
            {
                if (curNodeIndex == newNodeIndex)
                {
                    throw new RuntimeException("Invalid input.");
                }

                if (curNodeIndex > newNodeIndex && curNode.left != null)
                {
                    curNode = curNode.left;
                }

                if (curNodeIndex < newNodeIndex && curNode.right != null)
                {
                    curNode = curNode.right;
                }

                curNodeIndex = find1(in, curNode.val);

                if (curNodeIndex == -1)
                {
                    throw new RuntimeException("Invalid input.");
                }
            }

            // 新节点在左边
            if (curNodeIndex > newNodeIndex && curNode.left == null)
            {
                curNode.left = newNode;
            }

            // 新节点在右边
            if (curNodeIndex <= newNodeIndex && curNode.right == null)
            {
                curNode.right = newNode;
            }
        }

        return root;
    }

    /**
     * 在数组中查找元素，如果存在返回元素下标，如果不存在返回-1
     */
    public static int find1(int[] n, int val)
    {
        for (int i = 0; i < n.length; i++)
        {
            if (n[i] == val)
            {
                return i;
            }
        }

        return -1;
    }

    /**
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
     */
    public static TreeNode alg2(int[] pre, int[] in)
    {
        if (pre == null || pre.length == 0 || in == null || in.length == 0)
        {
            return null;
        }

        if (pre.length != in.length)
        {
            throw new RuntimeException("Invalid input.");
        }

        return alg2(pre, 0, pre.length - 1, in, 0, in.length - 1);
    }

    /**
     * 递归建立树，注意参数的边界都是前闭后闭： [preStart, preEnd], [inStart, inEnd]
     */
    private static TreeNode alg2(int[] pre, int preStart, int preEnd, int[] in, int inStart, int inEnd)
    {
        if (preStart < 0 || preStart > preEnd || preEnd > pre.length - 1 || inStart < 0 || inStart > inEnd
                || inEnd > in.length - 1)
        {
            return null;
        }

        TreeNode root = new TreeNode(pre[preStart]);

        int index = find2(in, pre[preStart]);
        if (index == -1)
        {
            throw new RuntimeException("Invalid input.");
        }

        root.left = alg2(pre, preStart + 1, preStart + 1 + (index - inStart) - 1, in, inStart, index - 1);
        root.right = alg2(pre, preStart + 1 + (index - inStart), preEnd, in, index + 1, inEnd);

        return root;
    }

    /**
     * 在数组中查找元素，如果存在返回元素下标，如果不存在返回-1
     */
    public static int find2(int[] n, int val)
    {
        for (int i = 0; i < n.length; i++)
        {
            if (n[i] == val)
            {
                return i;
            }
        }

        return -1;
    }

    /**
     * 输出树的图形
     */
    public static void treeGraph(TreeNode root, int deep)
    {
        if (root == null)
        {
            return;
        }

        treeGraph(root.right, deep + 1);

        for (int i = 0; i < deep; i++)
        {
            System.out.print("....");
        }
        System.out.println(root.val);

        treeGraph(root.left, deep + 1);
    }

    public static void main(String[] args)
    {
        int[] pre = { 1, 2, 4, 7, 3, 5, 6, 8 };
        int[] in = { 4, 7, 2, 1, 5, 3, 8, 6 };

        TreeNode alg = alg2(pre, in);

        treeGraph(alg, 1);
    }
}

class TreeNode
{
    int      val;
    TreeNode left;
    TreeNode right;

    TreeNode()
    {
    }

    TreeNode(int x)
    {
        val = x;
    }

    TreeNode(int val, TreeNode left, TreeNode right)
    {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString()
    {
        return String.valueOf(val);
    }
}
