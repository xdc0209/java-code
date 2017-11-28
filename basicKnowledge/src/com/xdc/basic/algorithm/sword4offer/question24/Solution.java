package com.xdc.basic.algorithm.sword4offer.question24;

public class Solution
{
    public static ListNode ReverseList(ListNode head)
    {
        ListNode node1 = null;
        ListNode node2 = head;
        while (node2 != null)
        {
            ListNode node3 = node2.next;
            node2.next = node1;
            node1 = node2;
            node2 = node3;
        }

        return node1;
    }

    public static void main(String[] args)
    {
        testReverseList();
        testReverseList(1);
        testReverseList(1, 2);
        testReverseList(1, 2, 3);
        testReverseList(1, 2, 3, 4);
        testReverseList(1, 2, 3, 4, 5);
    }

    public static void testReverseList(int... values)
    {
        ListNode head = addNodes(null, values);
        System.out.println(head);

        ListNode reversed = ReverseList(head);
        System.out.println(reversed);

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
