package com.xdc.basic.algorithm.sword4offer.question68;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Solution
{
    private static boolean getNodePath(TreeNode root, TreeNode node, List<TreeNode> path)
    {
        if (root == null || node == null || path == null)
        {
            return false;
        }

        path.add(root);

        if (root == node)
        {
            return true;
        }

        if (getNodePath(root.left, node, path))
        {
            return true;
        }

        if (getNodePath(root.right, node, path))
        {
            return true;
        }

        path.remove(path.size() - 1);

        return false;
    }

    public static TreeNode getLastCommonParent(TreeNode root, TreeNode node1, TreeNode node2)
    {
        List<TreeNode> path1 = new ArrayList<TreeNode>();
        getNodePath(root, node1, path1);

        List<TreeNode> path2 = new ArrayList<TreeNode>();
        getNodePath(root, node2, path2);

        TreeNode commonParent = null;
        for (int i = 0; i < path1.size() && i < path2.size(); i++)
        {
            if (path1.get(i) != path2.get(i))
            {
                break;
            }

            commonParent = path1.get(i);
        }

        return commonParent;
    }

    public static void main(String[] args)
    {
        final Integer X = null;
        TreeNode root = TreeNode.createTree(9, 5, 3, X, 4, X, X, 7, 6, X, X, 8, X, X, 11, 10, X, X, 12, X, X);
        TreeNode.treeGraph(root);
        System.out.println();

        System.out.println(getLastCommonParent(null, null, null));
        System.out.println(getLastCommonParent(root, null, null));
        System.out.println(getLastCommonParent(root, root, null));
        System.out.println(getLastCommonParent(root, root, root));
        System.out.println(getLastCommonParent(root, root.left.right.left, root.left.right.right));
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
