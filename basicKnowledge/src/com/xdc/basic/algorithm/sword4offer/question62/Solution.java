package com.xdc.basic.algorithm.sword4offer.question62;

public class Solution
{
    /**
     * 面试题62：圆圈中最后剩下的数。
     */
    public static int LastRemaining_Solution(int n, int m)
    {
        if (n < 1 || m < 1)
        {
            return -1;
        }

        ListNode head = new ListNode(0);
        ListNode node = head;
        for (int i = 1; i < n; i++)
        {
            ListNode newNode = new ListNode(i);
            node.next = newNode;
            node = node.next;
        }
        node.next = head;

        int num = 1;
        ListNode node1 = head;
        ListNode node2 = node;
        while (node1.next != node1)
        {
            if (num % m == 0)
            {
                node2.next = node1.next;
            }
            else
            {
                node2 = node1;
            }

            num++;
            node1 = node1.next;
        }

        return node1.val;
    }

    public static void main(String[] args)
    {
        System.out.println(LastRemaining_Solution(5, 3));
        System.out.println();

        System.out.println(LastRemaining_Solution(5, 1));
        System.out.println();

        System.out.println(LastRemaining_Solution(5, 8));
        System.out.println();

        System.out.println(LastRemaining_Solution(1, 8));
        System.out.println();

        System.out.println(LastRemaining_Solution(2, 8));
        System.out.println();
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
