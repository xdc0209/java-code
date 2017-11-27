package com.xdc.basic.algorithm.sword4offer.question22;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class Solution
{
    public static ListNode FindKthToTail(ListNode head, int k)
    {
        ListNode node1 = head;
        while (k > 0 && node1 != null)
        {
            k--;
            node1 = node1.next;
        }

        if (k > 0)
        {
            return null;
        }

        ListNode node2 = head;
        while (node1 != null)
        {
            node1 = node1.next;
            node2 = node2.next;
        }

        return node2;
    }

    public static ListNode FindKthToTail2(ListNode head, int k)
    {
        if (head == null || k <= 0)
        {
            return null;
        }

        Queue<ListNode> queue = new LinkedBlockingDeque<ListNode>();
        ListNode node = head;
        while (node != null)
        {
            if (queue.size() == k)
            {
                queue.remove();
            }

            queue.add(node);
            node = node.next;
        }

        if (queue.size() == k)
        {
            return queue.remove();
        }
        else
        {
            return null;
        }
    }

    public static void main(String[] args)
    {
        ListNode head = addNodes(null, 1, 2, 3, 3, 4, 4, 5);
        System.out.println(head);
        System.out.println();

        System.out.println(FindKthToTail(head, -1));
        System.out.println(FindKthToTail(head, 0));
        System.out.println(FindKthToTail(head, 1));
        System.out.println(FindKthToTail(head, 2));
        System.out.println(FindKthToTail(head, 3));
        System.out.println(FindKthToTail(head, 4));
        System.out.println(FindKthToTail(head, 5));
        System.out.println(FindKthToTail(head, 6));
        System.out.println(FindKthToTail(head, 7));
        System.out.println(FindKthToTail(head, 8));
        System.out.println(FindKthToTail(head, 9));
        System.out.println();

        System.out.println(FindKthToTail(null, -1));
        System.out.println(FindKthToTail(null, 0));
        System.out.println(FindKthToTail(null, 1));
        System.out.println(FindKthToTail(null, 2));
        System.out.println(FindKthToTail(null, 3));
        System.out.println(FindKthToTail(null, 4));
        System.out.println(FindKthToTail(null, 5));
        System.out.println(FindKthToTail(null, 6));
        System.out.println(FindKthToTail(null, 7));
        System.out.println(FindKthToTail(null, 8));
        System.out.println(FindKthToTail(null, 9));
        System.out.println();
    }

    private static ListNode addNodes(ListNode headNode, int... values)
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
    ListNode next = null;

    ListNode(int val)
    {
        this.val = val;
    }

    @Override
    public String toString()
    {
        return String.format("ListNode [val=%s, next=%s]", val, next);
    }
}
