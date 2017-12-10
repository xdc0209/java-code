package com.xdc.basic.skills.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public interface TreeNode<T> extends Serializable
{
    /**
     * 获取TreeNode所包含的附件对象
     */
    public T attchment();

    /**
     * 添加附件到TreeNode对象中
     */
    public void attch(T attchment);

    /**
     * 查询当前TreeNode的父节点, 如果为空，则说明此节点为根节点
     */
    public TreeNode<T> getParent();

    /**
     * 设置父节点, 如果设置的父节点为空, 则当前节点为根节点
     */
    public void setParent(TreeNode<T> parent);

    /**
     * 添加子节点到子节点列表最后, 不接受null值
     */
    public void append(TreeNode<T> node);

    /**
     * 添加子节点到子节点列表最后, 不接受null值
     */
    public void append(T nodeAttchment);

    /**
     * 将目标节点node插入到当前节点的子节点列表的index(从0开 始)位置,
     * 如果index < 0, 则插入到列表头,
     * 如果index 大于列表元素个数，则添加到列表最后
     */
    public void insert(TreeNode<T> node, int index);

    /**
     * 将目标节点附件nodeAttchment插入到当前节点的子节点列表的index(从0开 始)位置,
     * 如果index < 0, 则插入到列表头,
     * 如果index 大于列表元素个数，则添加到列表最后
     */
    public void insert(T nodeAttchment, int index);

    /**
     * 获取所有子节点列表, 如果当前节点是叶子节点则返回的节点数组长度为0
     */
    public Collection<TreeNode<T>> children();

    /**
     * 获取子节点数目
     */
    public int getChildCount();

    /**
     * 查询第index(从0开始)个子节点, 如果index不在索引范围之内则返回null
     */
    public TreeNode<T> getChildAt(int index);

    /**
     * 查询目标节点node在子节点列表中的索引位置(从0开始), 如果 目标节点不是当前节点的子节点, 则返回-1
     */
    public int getIndex(TreeNode<T> node);

    /**
     * 从子节点列表中删除目标节点, 如果目标节点为null值, 或者不是当前节点的子节点 则当前节点什么都不做
     */
    public void remove(TreeNode<T> node);

    /**
     * 删除指定位置的子节点, 如果指定位置超出子节点列表索引范围, 则什么也不做
     */
    public void remove(int index);

    /**
     * 清除所有子节点
     */
    public void clear();

    /**
     * 是否为叶子节点
     */
    public boolean isLeaf();

    /**
     * 返回一个层次遍历迭代器
     */
    public Iterator<TreeNode<T>> levelOrderIterator();
}
