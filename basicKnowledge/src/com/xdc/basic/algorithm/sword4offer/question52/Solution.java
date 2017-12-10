package com.xdc.basic.algorithm.sword4offer.question52;

public class Solution
{
    public static ListNode FindFirstCommonNode(ListNode head1, ListNode head2)
    {
        int count1 = 0;
        ListNode node1 = head1;
        while (node1 != null)
        {
            count1++;
            node1 = node1.next;
        }

        int count2 = 0;
        ListNode node2 = head2;
        while (node2 != null)
        {
            count2++;
            node2 = node2.next;
        }

        // 走完长度差。
        node1 = head1;
        node2 = head2;
        if (count1 > count2)
        {
            for (int i = 0; i < count1 - count2; i++)
            {
                node1 = node1.next;
            }
        }
        else
        {
            for (int i = 0; i < count2 - count1; i++)
            {
                node2 = node2.next;
            }
        }

        // 齐头并进。
        while (node1 != node2 && node1 != null && node2 != null)
        {
            node1 = node1.next;
            node2 = node2.next;
        }

        return node1;
    }

    public static void main(String[] args)
    {
        ListNode head1 = addNodes(null, newArray(1, 2, 3, 6, 7));
        ListNode head2 = addNodes(null, newArray(4, 5));

        System.out.println(head1);
        System.out.println(head2);

        System.out.println(FindFirstCommonNode(null, null));
        System.out.println(FindFirstCommonNode(head1, null));
        System.out.println(FindFirstCommonNode(null, head2));
        System.out.println(FindFirstCommonNode(head1, head2));

        head2.next.next = head1.next.next.next;
        System.out.println(FindFirstCommonNode(head1, head2));
        System.out.println(FindFirstCommonNode(head1, head1));
    }

    private static int[] newArray(int... values)
    {
        return values;
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
