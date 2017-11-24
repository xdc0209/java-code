package com.xdc.basic.algorithm.sword4offer.question18;

import org.junit.Test;

public class Solution
{
    /**
     * 面试题18：删除链表中的节点 题目一：在O(1)的时间内删除链表节点。
     */
    public static ListNode deleteNode(ListNode headNode, ListNode deleteNode)
    {
        if (deleteNode == null)
        {
            return headNode;
        }

        if (headNode == null)
        {
            return null;
        }

        if (deleteNode.next != null) // 要删除的节点不是最后一个节点。
        {
            deleteNode.val = deleteNode.next.val;
            deleteNode.next = deleteNode.next.next;
        }
        else if (headNode == deleteNode) // 要删除的节点是最后一个节点，而且要删除的节点是第一个节点。
        {
            return null;
        }
        else // 要删除的节点是最后一个节点，而且要删除的节点不是第一个节点。
        {
            ListNode node = headNode;
            while (node != null && node.next != deleteNode)
            {
                node = node.next;
            }

            if (node != null)
            {
                node.next = deleteNode.next;
            }
        }

        return headNode;
    }

    /**
     * 面试题18：删除链表中的节点 题目二：删除链表中重复的节点。
     */
    public static ListNode deleteDuplication(ListNode headNode)
    {
        // 添加一个临时的头结点，避免很多空判断。
        ListNode newHeadNode = new ListNode(Integer.MIN_VALUE);
        newHeadNode.next = headNode;

        ListNode prev = newHeadNode;
        ListNode node = newHeadNode.next;
        while (node != null)
        {
            boolean duplicated = false;
            while (node.next != null && node.val == node.next.val)
            {
                duplicated = true;
                node.next = node.next.next;
            }

            if (duplicated)
            {
                prev.next = node.next;
                node = node.next;
            }
            else
            {
                prev = node;
                node = node.next;
            }
        }

        // 返回时跳过临时的头结点。
        return newHeadNode.next;
    }

    /**
     * 面试题18：删除链表中的节点 题目二：删除链表中重复的节点。
     */
    public static ListNode deleteDuplication2(ListNode headNode)
    {
        // 添加一个临时的头结点，避免很多空判断。
        ListNode newHeadNode = new ListNode(Integer.MIN_VALUE);
        newHeadNode.next = headNode;

        ListNode prev = newHeadNode;
        ListNode node = newHeadNode.next;
        while (node != null)
        {
            if (node.next != null && node.val == node.next.val)
            {
                int val = node.val;
                while (node != null && node.val == val)
                {
                    prev.next = node.next;
                    node = node.next;
                }
            }
            else
            {
                prev = node;
                node = node.next;
            }
        }

        // 返回时跳过临时的头结点。
        return newHeadNode.next;
    }

    @Test
    public void deleteNode()
    {
        ListNode headNode = addNodes(null, 1, 2, 3, 4, 5);
        System.out.println(headNode);

        headNode = deleteNode(headNode, headNode.next.next);
        System.out.println(headNode);
    }

    @Test
    public void deleteDuplication()
    {
        ListNode headNode = addNodes(null, 1, 2, 3, 3, 4, 4, 5);
        System.out.println(headNode);

        headNode = deleteDuplication(headNode);
        System.out.println(headNode);
    }

    @Test
    public void deleteDuplication2()
    {
        ListNode headNode = addNodes(null, 1, 2, 3, 3, 4, 4, 5);
        System.out.println(headNode);

        headNode = deleteDuplication2(headNode);
        System.out.println(headNode);
    }

    private ListNode addNodes(ListNode headNode, int... values)
    {
        // 添加一个临时的头结点，避免很多空判断。
        ListNode newHeadNode = new ListNode(Integer.MIN_VALUE);
        newHeadNode.next = headNode;

        ListNode node = newHeadNode;
        while (node.next != null)
        {
            node = node.next;
        }

        for (int i = 0; i < values.length; i++)
        {
            ListNode newNode = new ListNode(values[i]);

            node.next = newNode;
            node = node.next;
        }

        // 返回时跳过临时的头结点。
        return newHeadNode.next;
    }
}

class ListNode
{
    int      val;
    ListNode next;

    public ListNode(int val)
    {
        this.val = val;
    }

    @Override
    public String toString()
    {
        return String.format("ListNode [val=%s, next=%s]", val, next);
    }
}
