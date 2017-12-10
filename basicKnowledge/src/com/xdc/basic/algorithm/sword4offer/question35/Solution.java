package com.xdc.basic.algorithm.sword4offer.question35;

import java.util.HashMap;
import java.util.Map;

public class Solution
{
    /**
     * 面试题35：复杂链表的复制。时间复杂度O(n^2)，空间复杂度O(1)。
     */
    public static RandomListNode Clone(RandomListNode head)
    {
        if (head == null)
        {
            return null;
        }

        // 1.复制RandomListNode.next。
        RandomListNode cloneHead = new RandomListNode(head.label);
        RandomListNode node = head.next;
        RandomListNode cloneNode = cloneHead;
        while (node != null)
        {
            RandomListNode newNode = new RandomListNode(node.label);
            cloneNode.next = newNode;

            node = node.next;
            cloneNode = cloneNode.next;
        }

        // 2.复制RandomListNode.random。
        node = head;
        cloneNode = cloneHead;
        while (node != null)
        {
            if (node.random != null)
            {
                RandomListNode node2 = head;
                RandomListNode cloneNode2 = cloneHead;
                while (node2 != null)
                {
                    if (node.random == node2)
                    {
                        cloneNode.random = cloneNode2;
                        break;
                    }

                    node2 = node2.next;
                    cloneNode2 = cloneNode2.next;
                }
            }

            node = node.next;
            cloneNode = cloneNode.next;
        }

        return cloneHead;
    }

    /**
     * 面试题35：复杂链表的复制。时间复杂度O(n)，空间复杂度O(n)。注意此题比较特殊，不要实现equals()和hashCode()，只需使用默认的equals中的==比较可以了，因为复杂链表中可能出现环，实现equals()难度较大，实现不好就容易出现死循环。
     */
    public static RandomListNode Clone2(RandomListNode head)
    {
        Map<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();

        RandomListNode node = head;
        while (node != null)
        {
            RandomListNode newNode = new RandomListNode(node.label);
            map.put(node, newNode);

            node = node.next;
        }

        node = head;
        while (node != null)
        {
            RandomListNode cloneNode = map.get(node);
            cloneNode.next = map.get(node.next);
            cloneNode.random = map.get(node.random);

            node = node.next;
        }

        return map.get(head);
    }

    /**
     * 面试题35：复杂链表的复制。时间复杂度O(n)，空间复杂度O(1)。注意：此算法中对原始链表进行了修改并复原操作，因此此方法是非线程安全的。
     */
    public static RandomListNode Clone3(RandomListNode head)
    {
        if (head == null)
        {
            return null;
        }

        // 1.复制原始链表的任一节点N并创建新节点N'，再把N'链接到N的后边，如原来是A->B->C变成A->A'->B->B'->C->C'。
        RandomListNode node = head;
        while (node != null)
        {
            RandomListNode cloneNode = new RandomListNode(node.label);
            cloneNode.next = node.next;
            cloneNode.random = null;

            node.next = cloneNode;

            node = cloneNode.next;
        }

        // 2.如果原始链表上的节点N的random指向S，则对应的复制节点N'的random指向S的下一个节点S'。
        node = head;
        while (node != null)
        {
            RandomListNode cloneNode = node.next;
            if (node.random != null)
            {
                cloneNode.random = node.random.next;
            }

            node = cloneNode.next;
        }

        // 3.把得到的链表拆成两个链表，奇数位置上的结点组成原始链表，偶数位置上的结点组成复制出来的链表。
        node = head;
        RandomListNode cloneHead = node.next;
        RandomListNode cloneNode = node.next;
        node.next = cloneNode.next;
        node = node.next;
        while (node != null)
        {
            cloneNode.next = node.next;
            cloneNode = cloneNode.next;
            node.next = cloneNode.next;
            node = node.next;
        }

        return cloneHead;
    }

    public static void main(String[] args)
    {
        RandomListNode head = addNodes(null, 1, 2, 3, 4, 5);
        head.next.next.random = head.next;
        System.out.println(head);

        RandomListNode clone = Clone(head);
        System.out.println(clone);
    }

    private static RandomListNode addNodes(RandomListNode headNode, int... values)
    {
        // 添加一个临时的头结点，避免很多空判断。
        RandomListNode newHeadNode = new RandomListNode(Integer.MIN_VALUE);
        newHeadNode.next = headNode;

        RandomListNode node = newHeadNode;
        while (node.next != null)
        {
            node = node.next;
        }

        for (int i = 0; i < values.length; i++)
        {
            RandomListNode newNode = new RandomListNode(values[i]);

            node.next = newNode;
            node = node.next;
        }

        // 返回时跳过临时的头结点。
        return newHeadNode.next;
    }
}

class RandomListNode
{
    int            label;
    RandomListNode next   = null;
    RandomListNode random = null;

    RandomListNode(int label)
    {
        this.label = label;
    }

    @Override
    public String toString()
    {
        return String.format("RandomListNode [label=%s, next=%s, random=%s]", label, next,
                (random != null ? random.label : null));
    }
}
