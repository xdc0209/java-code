package com.xdc.basic.algorithm.sword4offer.question08;

import java.util.Stack;

public class ALG
{
    /**
     * 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
     */
    public static TreeLinkNode alg1(TreeLinkNode search)
    {
        if (search == null)
        {
            return null;
        }

        // 找到根节点
        TreeLinkNode root = search;
        while (root.next != null)
        {
            root = root.next;
        }

        Stack<TreeLinkNode> stack = new Stack<TreeLinkNode>();

        // 标记是否找到搜索的节点
        boolean find = false;

        // 从根节点开始中序遍历
        TreeLinkNode node = root;
        while (node != null || !stack.isEmpty())
        {
            while (node != null)
            {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();

            // 取出元素
            if (find)
            {
                return node;
            }

            if (node.val == search.val)
            {
                // 找到了节点，下次循环就可以取出下个元素了
                find = true;
            }

            node = node.right;
        }

        return null;
    }

    /**
     * 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
     */
    public static TreeLinkNode alg2(TreeLinkNode search)
    {
        if (search == null)
        {
            return null;
        }

        TreeLinkNode node = search;
        if (node.right != null)
        {
            node = node.right;
            while (node.left != null)
            {
                node = node.left;
            }
            return node;
        }
        else
        {
            TreeLinkNode parent = node.next;
            while (parent != null && parent.right == node)
            {
                node = parent;
                parent = node.next;
            }
            return parent;
        }
    }

    public static void main(String[] args)
    {
        TreeLinkNode treeLinkNode1 = new TreeLinkNode(10);

        TreeLinkNode treeLinkNode2 = new TreeLinkNode(6);
        TreeLinkNode treeLinkNode3 = new TreeLinkNode(14);

        TreeLinkNode treeLinkNode4 = new TreeLinkNode(4);
        TreeLinkNode treeLinkNode5 = new TreeLinkNode(8);
        TreeLinkNode treeLinkNode6 = new TreeLinkNode(12);
        TreeLinkNode treeLinkNode7 = new TreeLinkNode(16);

        treeLinkNode1.left = treeLinkNode2;
        treeLinkNode1.right = treeLinkNode3;
        treeLinkNode2.next = treeLinkNode1;
        treeLinkNode3.next = treeLinkNode1;

        treeLinkNode2.left = treeLinkNode4;
        treeLinkNode2.right = treeLinkNode5;
        treeLinkNode4.next = treeLinkNode2;
        treeLinkNode5.next = treeLinkNode2;

        treeLinkNode3.left = treeLinkNode6;
        treeLinkNode3.right = treeLinkNode7;
        treeLinkNode6.next = treeLinkNode3;
        treeLinkNode7.next = treeLinkNode3;

        TreeLinkNode alg2 = alg2(treeLinkNode1);
        System.out.println(alg2 != null ? alg2.val : null);
    }
}

class TreeLinkNode
{
    int          val;
    TreeLinkNode left  = null;
    TreeLinkNode right = null;
    TreeLinkNode next  = null; // 这里应该是parent更恰当，应该是出题人当时发懵

    TreeLinkNode(int val)
    {
        this.val = val;
    }
}
