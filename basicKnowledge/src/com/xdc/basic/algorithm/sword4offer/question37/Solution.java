package com.xdc.basic.algorithm.sword4offer.question37;

import java.util.LinkedList;

public class Solution
{
    public static String Serialize(TreeNode root)
    {
        if (root == null)
        {
            return null;
        }

        return SerializeCore(root);
    }

    public static String SerializeCore(TreeNode root)
    {
        if (root == null)
        {
            return "$";
        }

        String left = SerializeCore(root.left);
        String right = SerializeCore(root.right);

        return root.val + "," + left + "," + right;
    }

    public static TreeNode Deserialize(String string)
    {
        if (string == null || string.length() == 0)
        {
            return null;
        }

        String[] values = string.split(",", -1);
        LinkedList<String> valueList = new LinkedList<String>();
        for (String value : values)
        {
            if (value != null && !value.trim().isEmpty())
            {
                valueList.add(value.trim());
            }
        }

        return DeserializeCore(valueList);
    }

    public static TreeNode DeserializeCore(LinkedList<String> valueList)
    {
        if (valueList == null || valueList.size() == 0)
        {
            return null;
        }

        String value = valueList.removeFirst();

        if ("$".equals(value))
        {
            return null;
        }

        TreeNode root = new TreeNode(Integer.valueOf(value));

        root.left = DeserializeCore(valueList);
        root.right = DeserializeCore(valueList);

        return root;
    }

    public static void main(String[] args)
    {
        final Integer X = null;
        TreeNode root = TreeNode.createTree(9, 5, 3, X, 4, X, X, 7, 6, X, X, 8, X, X, 11, 10, X, X, 12, X, X);
        TreeNode.treeGraph(root);
        System.out.println();

        String serialize = Serialize(root);
        System.out.println(serialize);
        System.out.println();

        TreeNode deserialize = Deserialize(serialize);
        TreeNode.treeGraph(deserialize);
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
