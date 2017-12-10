package com.xdc.basic.algorithm.sword4offer.question25;

public class Solution
{
    /**
     * 循环实现。（不使用临时头节点）
     */
    public static ListNode Merge(ListNode head1, ListNode head2)
    {
        if (head1 == null)
        {
            return head2;
        }

        if (head2 == null)
        {
            return head1;
        }

        ListNode node1 = head1;
        ListNode node2 = head2;

        ListNode head = null;
        ListNode node = null;
        while (node1 != null && node2 != null)
        {
            if (node1.val <= node2.val)
            {
                if (head == null)
                {
                    head = node1;
                    node = node1;
                }
                else
                {
                    node.next = node1;
                    node = node.next;
                }

                node1 = node1.next;
            }
            else
            {
                if (head == null)
                {
                    head = node2;
                    node = node2;
                }
                else
                {
                    node.next = node2;
                    node = node.next;
                }

                node2 = node2.next;
            }
        }

        if (node1 != null)
        {
            node.next = node1;
        }

        if (node2 != null)
        {
            node.next = node2;
        }

        return head;
    }

    /**
     * 循环实现。（使用临时头节点）
     */
    public static ListNode Merge2(ListNode head1, ListNode head2)
    {
        // 添加一个临时的头结点，避免很多空判断。
        ListNode newHead1 = new ListNode(Integer.MIN_VALUE);
        newHead1.next = head1;
        ListNode newHead2 = new ListNode(Integer.MIN_VALUE);
        newHead2.next = head2;

        ListNode newHead = new ListNode(Integer.MIN_VALUE);
        ListNode node = newHead;

        ListNode node1 = newHead1.next;
        ListNode node2 = newHead2.next;
        while (node1 != null && node2 != null)
        {
            if (node1.val <= node2.val)
            {
                node.next = node1;
                node = node.next;
                node1 = node1.next;
            }
            else
            {
                node.next = node2;
                node = node.next;

                node2 = node2.next;
            }
        }

        if (node1 != null)
        {
            node.next = node1;
        }

        if (node2 != null)
        {
            node.next = node2;
        }

        return newHead.next;
    }

    /**
     * 递归实现。
     */
    public static ListNode Merge3(ListNode head1, ListNode head2)
    {
        if (head1 == null)
        {
            return head2;
        }

        if (head2 == null)
        {
            return head1;
        }

        ListNode head = null;

        if (head1.val <= head2.val)
        {
            head = head1;
            head.next = Merge3(head1.next, head2);
        }
        else
        {
            head = head2;
            head.next = Merge3(head1, head2.next);
        }

        return head;
    }

    public static void main(String[] args)
    {
        testMerge(newArray(), newArray());

        testMerge(newArray(), newArray(1));
        testMerge(newArray(), newArray(1, 2));
        testMerge(newArray(), newArray(1, 2, 3));

        testMerge(newArray(1), newArray());
        testMerge(newArray(1, 2), newArray());
        testMerge(newArray(1, 2, 3), newArray());

        testMerge(newArray(2), newArray(1));
        testMerge(newArray(2), newArray(1, 2));
        testMerge(newArray(2), newArray(1, 2, 3));

        testMerge(newArray(1), newArray(2));
        testMerge(newArray(1, 2), newArray(2));
        testMerge(newArray(1, 2, 3), newArray(2));

        testMerge(newArray(1, 3, 5), newArray(2, 4, 6));
    }

    public static void testMerge(int[] values1, int[] values2)
    {
        ListNode head1 = addNodes(null, values1);
        ListNode head2 = addNodes(null, values2);
        System.out.println(head1);
        System.out.println(head2);

        ListNode merged = Merge(head1, head2);
        System.out.println(merged);

        System.out.println();
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
