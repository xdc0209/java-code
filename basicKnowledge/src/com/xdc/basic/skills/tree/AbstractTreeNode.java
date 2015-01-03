package com.xdc.basic.skills.tree;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * TreeNode抽象实现, 提供非线程安全迭代器实现
 */
public abstract class AbstractTreeNode<T> implements TreeNode<T>
{
    // 简述下树的遍历
    // 二叉树遍历的顺序：如果用L、D、R分别表示遍历左子树、访问根结点、遍历右子树，则对二叉树的遍历可以有下列六种（3!=6）组合：LDR、 LRD、 DLR、 DRL、RDL、 RLD。
    // 若再限定先左后右的次序，则只剩下三种组合：LDR（中序遍历）、LRD（后序遍历）、DLR（前序遍历，或叫先序遍历）。 
    // 
    // 这里的实现的树是一般的树（子节点不止两个），常用的遍历为层次遍历，原理同二叉树的前序遍历。

    private static final long serialVersionUID = 1425451366642064662L;

    /**
     * 层次遍历迭代器，实现对树的层次遍历
     */
    private class InnerIterator<N> implements Iterator<TreeNode<N>>
    {
        private TreeNode<N>                   node;

        private final LinkedList<TreeNode<N>> queue;

        public InnerIterator(TreeNode<N> root)
        {
            node = root;
            queue = new LinkedList<TreeNode<N>>();
            queue.offer(root);
        }

        @Override
        public boolean hasNext()
        {
            return !queue.isEmpty();
        }

        @Override
        public TreeNode<N> next()
        {
            node = queue.poll();
            if (null != node)
            {
                for (TreeNode<N> child : node.children())
                {
                    if (null != child)
                    {
                        queue.offer(child);
                    }
                }
            }
            return node;
        }

        @Override
        public void remove()
        {
            if ((null != node) && (null != node.getParent()))
            {
                node.setParent(null);
                node.getParent().remove(node);
            }
        }
    }

    /**
     * 返回一个层次遍历迭代器
     */
    @Override
    public InnerIterator<T> levelOrderIterator()
    {
        InnerIterator<T> it = new InnerIterator<T>(this);
        return it;
    }
}