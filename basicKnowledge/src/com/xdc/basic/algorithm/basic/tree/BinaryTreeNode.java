package com.xdc.basic.algorithm.basic.tree;

/**
 * 二叉树节点
 */
public class BinaryTreeNode
{
    private int            data;

    private BinaryTreeNode lchild;

    private BinaryTreeNode rchild;

    public BinaryTreeNode()
    {
    }

    public BinaryTreeNode(int data)
    {
        this.data = data;
    }

    public BinaryTreeNode(int data, BinaryTreeNode lchild, BinaryTreeNode rchild)
    {
        this.data = data;
        this.lchild = lchild;
        this.rchild = rchild;
    }

    public int getData()
    {
        return data;
    }

    public void setData(int data)
    {
        this.data = data;
    }

    public BinaryTreeNode getLchild()
    {
        return lchild;
    }

    public void setLchild(BinaryTreeNode lchild)
    {
        this.lchild = lchild;
    }

    public BinaryTreeNode getRchild()
    {
        return rchild;
    }

    public void setRchild(BinaryTreeNode rchild)
    {
        this.rchild = rchild;
    }

    @Override
    public String toString()
    {
        return String.valueOf(data);
    }
}
