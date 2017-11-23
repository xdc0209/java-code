package com.xdc.basic.algorithm.sword4offer.question18;

public class Solution
{
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
            deleteNode.value = deleteNode.next.value;
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

    public static void main(String[] args)
    {
        ListNode headNode = new ListNode(1);
        headNode.next = new ListNode(2);
        headNode.next.next = new ListNode(3);
        headNode.next.next.next = new ListNode(4);
        headNode.next.next.next.next = new ListNode(5);
        System.out.println(headNode);

        headNode = deleteNode(headNode, headNode.next.next);
        System.out.println(headNode);
    }
}

class ListNode
{
    int      value;
    ListNode next;

    public ListNode(int value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return String.format("ListNode [value=%s, next=%s]", value, next);
    }
}
