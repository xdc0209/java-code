package com.xdc.basic.algorithm.sword4offer.question36;

import java.util.LinkedList;

public class Solution
{
    /**
     * 面试题36：二叉搜索树与双向链表。返回双向链表的头指针。
     */
    public static TreeNode Convert(TreeNode root)
    {
        if (root == null)
        {
            return null;
        }

        TreeNode head = root;

        if (root.left != null)
        {
            // 处理左子树。
            TreeNode headLeft = Convert(root.left);
            TreeNode tailLeft = headLeft;
            while (tailLeft != null && tailLeft.right != null) // 找到双向链表的尾指针，注意这里导致算法复杂度提高n倍。
            {
                tailLeft = tailLeft.right;
            }

            root.left = tailLeft;
            tailLeft.right = root;

            head = headLeft;
        }

        if (root.right != null)
        {
            // 处理右子树。
            TreeNode headRight = Convert(root.right);

            root.right = headRight;
            headRight.left = root;
        }

        return head;
    }

    private static class DoubleLinkedList
    {
        TreeNode head;
        TreeNode tail;

        public DoubleLinkedList(TreeNode head, TreeNode tail)
        {
            this.head = head;
            this.tail = tail;
        }
    }

    /**
     * 面试题36：二叉搜索树与双向链表。返回双向链表的头指针。
     */
    public static TreeNode Convert2(TreeNode root)
    {
        if (root == null)
        {
            return null;
        }

        DoubleLinkedList dll = Convert2Core(root);

        return dll.head;
    }

    private static DoubleLinkedList Convert2Core(TreeNode root)
    {
        DoubleLinkedList dll = new DoubleLinkedList(root, root);

        if (root.left != null)
        {
            // 处理左子树。
            DoubleLinkedList dlll = Convert2Core(root.left);

            root.left = dlll.tail;
            dlll.tail.right = root;

            dll.head = dlll.head;
        }

        if (root.right != null)
        {
            // 处理右子树。
            DoubleLinkedList dllr = Convert2Core(root.right);

            root.right = dllr.head;
            dllr.head.left = root;

            dll.tail = dllr.tail;
        }

        return dll;
    }

    public static void main(String[] args)
    {
        final Integer X = null;
        TreeNode root = TreeNode.createTree(9, 5, 3, X, 4, X, X, 7, 6, X, X, 8, X, X, 11, 10, X, X, 12, X);

        TreeNode.treeGraph(root);
        System.out.println();

        TreeNode head = Convert(root);
        TreeNode node = head;
        while (node != null)
        {
            System.out.print(String.format("%s(%s, %s), ", node.val, node.left != null ? node.left.val : null,
                    node.right != null ? node.right.val : null));
            node = node.right;
        }
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
