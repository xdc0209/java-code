package com.xdc.basic.skills.tree;

import java.util.Collection;
import java.util.Vector;

public class DefaultTreeNode<T> extends AbstractTreeNode<T>
{
    private static final long         serialVersionUID = 8326258226683831316L;

    /**
     * 树节点中的附件对象
     */
    private T                         attchment;

    /**
     * 父节点
     */
    private TreeNode<T>               parent;

    /**
     * 子节点列表
     */
    private final Vector<TreeNode<T>> childs;

    /**
     * 生成一个新的TreeNode对象
     */
    public DefaultTreeNode()
    {
        attchment = null;
        parent = null;
        childs = new Vector<TreeNode<T>>();
    }

    /**
     * 生成一个新的TreeNode对象, 并指定附件
     */
    public DefaultTreeNode(T attchment)
    {
        this.attchment = attchment;
        parent = null;
        childs = new Vector<TreeNode<T>>();
    }

    /**
     * 获取TreeNode所包含的附件对象
     */
    @Override
    public T attchment()
    {
        return attchment;
    }

    /**
     * 添加附件到TreeNode对象中
     */
    @Override
    public void attch(T attchment)
    {
        this.attchment = attchment;
    }

    /**
     * 查询当前TreeNode的父节点
     */
    @Override
    public TreeNode<T> getParent()
    {
        return parent;
    }

    /**
     * 设置父节点, 如果设置的父节点为空, 则当前节点为根节点
     */
    @Override
    public void setParent(TreeNode<T> parent)
    {
        this.parent = parent;
    }

    /**
     * 添加子节点到子节点列表最后, 不接受null值
     */
    @Override
    public void append(TreeNode<T> node)
    {
        if (node == null)
        {
            return;
        }

        TreeNode<T> oldParent = node.getParent();
        if (oldParent != null)
        {
            oldParent.remove(node);
        }

        node.setParent(this);
        childs.add(node);
    }

    /**
     * 添加子节点到子节点列表最后, 不接受null值
     */
    @Override
    public void append(T nodeAttchment)
    {
        if (nodeAttchment == null)
        {
            return;
        }

        append(new DefaultTreeNode<T>(nodeAttchment));
    }

    /**
     * 将目标节点node插入到当前节点的子节点列表的index(从0开 始)位置,
     * 如果index < 0, 则插入到列表头,
     * 如果index 大于列表元素个数，则添加到列表最后
     */
    @Override
    public void insert(TreeNode<T> node, int index)
    {
        if (node == null)
        {
            return;
        }

        TreeNode<T> oldParent = node.getParent();
        if (oldParent != null)
        {
            oldParent.remove(node);
        }

        node.setParent(this);
        if (index < childs.size())
        {
            int realIndex = (index < 0) ? 0 : index;
            childs.insertElementAt(node, realIndex);
        }
        else
        {
            childs.add(node);
        }
    }

    /**
     * 将目标节点附件nodeAttchment插入到当前节点的子节点列表的index(从0开 始)位置,
     * 如果index < 0, 则插入到列表头,
     * 如果index 大于列表元素个数，则添加到列表最后
     */
    @Override
    public void insert(T nodeAttchment, int index)
    {
        if (nodeAttchment == null)
        {
            return;
        }

        insert(new DefaultTreeNode<T>(nodeAttchment), index);
    }

    /**
     * 获取所有子节点列表, 如果当前节点是叶子节点则返回的节点数组长度为0
     */
    @Override
    public Collection<TreeNode<T>> children()
    {
        return childs;
    }

    /**
     * 获取子节点数目
     */
    @Override
    public int getChildCount()
    {
        return childs.size();
    }

    /**
     * 查询第index(从0开始)个子节点, 如果index不在索引范围之内则返回null
     */
    @Override
    public TreeNode<T> getChildAt(int index)
    {
        if (index < 0 || index >= childs.size())
        {
            return null;
        }

        return childs.get(index);
    }

    /**
     * 查询目标节点node在子节点列表中的索引位置(从0开始), 如果 目标节点不是当前节点的子节点, 则返回-1
     */
    @Override
    public int getIndex(TreeNode<T> node)
    {
        if ((node == null) || (node.getParent() != this))
        {
            return -1;
        }

        return childs.indexOf(node);
    }

    /**
     * 从子节点列表中删除目标节点, 如果目标节点为null值, 或者不是当前节点的子节点 则当前节点什么都不做
     */
    @Override
    public void remove(TreeNode<T> node)
    {
        if ((node == null) || (node.getParent() != this))
        {
            return;
        }

        childs.remove(node);
    }

    /**
     * 删除指定位置的子节点, 如果指定位置超出子节点列表索引范围, 则什么也不做
     */
    @Override
    public void remove(int index)
    {
        if (index < 0 || index >= childs.size())
        {
            return;
        }

        childs.remove(index);
    }

    /**
     * 清除所有子节点
     */
    @Override
    public void clear()
    {
        childs.clear();
    }

    /**
     * 是否为叶子节点
     * 
     * @return 是否为叶子节点
     */
    @Override
    public boolean isLeaf()
    {
        return childs.size() == 0;
    }
}