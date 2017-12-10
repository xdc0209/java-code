package com.xdc.basic.algorithm.sword4offer.question23;

public class Solution
{
    public static ListNode EntryNodeOfLoop(ListNode head)
    {
        if (head == null || head.next == null)
        {
            return null;
        }

        // 1.判断是否存在环。
        ListNode node1 = head.next; // 先走一步。
        ListNode node2 = head.next.next; // 先走两步。
        while (node2 != null && node2.next != null)
        {
            if (node1 == node2) // 如果存在环，则一定会在环中相遇的。
            {
                break;
            }

            node1 = node1.next; // 每次走一步。
            node2 = node2.next.next; // 每次走两步。
        }

        // 如果node2或node2.next走到头了，则没有环。
        if (node2 == null || node2.next == null)
        {
            return null;
        }

        // 2.计算环的中元素的个数。
        int nodeCountInloop = 1;
        ListNode node3 = node2;
        while (node3.next != node2)
        {
            nodeCountInloop++;
            node3 = node3.next;
        }

        // 3.找到环的入口节点。
        // node4先前进nodeCountInloop步。
        ListNode node4 = head;
        for (int i = 0; i < nodeCountInloop; i++)
        {
            node4 = node4.next;
        }

        // node4和node5会在环的入口节点相遇。
        ListNode node5 = head;
        while (node4 != node5)
        {
            node4 = node4.next;
            node5 = node5.next;
        }

        return node5;
    }

    /**
     * 规律1：两个指针，一个fast、一个slow，同时从一个链表的头部出发。fast一次走2步，slow一次走一步，如果该链表有环，两个指针必然在环内相遇。
     * 规律2：此时只需要把其中的一个指针重新指向链表头部，另一个不变(还在环内)，这次两个指针一次走一步，相遇的地方就是入口节点。
     * 规律的正确性网上有证明：https://www.nowcoder.com/questionTerminal/253d2c59ec3e4bc68da16833f79a38e4
     */
    ListNode EntryNodeOfLoop1(ListNode head)
    {
        if (head == null || head.next == null)
        {
            return null;
        }

        ListNode node1 = head.next;
        ListNode node2 = head.next.next;
        while (node2 != null && node2.next != null)
        {
            if (node1 == node2)
            {
                node2 = head;
                while (node1 != node2)
                {
                    node1 = node1.next;
                    node2 = node2.next;
                }
                return node1;
            }

            node1 = node1.next;
            node2 = node2.next.next;
        }

        return null;
    }

    public static void main(String[] args)
    {
        ListNode head = addNodes(null, 1, 2, 3, 4, 5, 6);
        System.out.println(head);
        System.out.println();

        head.next.next.next.next.next.next = head.next.next;
        System.out.println(EntryNodeOfLoop(head));
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
        return String.format("ListNode [val=%s]", val);
    }
}
