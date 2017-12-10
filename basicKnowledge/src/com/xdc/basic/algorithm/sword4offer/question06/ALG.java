package com.xdc.basic.algorithm.sword4offer.question06;

import java.util.ArrayList;
import java.util.Stack;

public class ALG
{
    /**
     * 输入一个链表，从尾到头打印链表每个节点的值。(显示的使用栈)
     */
    public static ArrayList<Integer> alg(ListNode head)
    {
        Stack<ListNode> stack = new Stack<ListNode>();
        ListNode node = head;
        while (node != null)
        {
            stack.push(node);
            node = node.next;
        }

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        while (!stack.isEmpty())
        {
            ListNode nodePoped = stack.pop();
            arrayList.add(nodePoped.val);
        }

        return arrayList;
    }

    /**
     * 输入一个链表，从尾到头打印链表每个节点的值。(使用递归栈)
     */
    public static ArrayList<Integer> alg2(ListNode head)
    {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();

        if (head == null)
        {
            return arrayList;
        }

        arrayList.addAll(alg2(head.next));
        arrayList.add(head.val);

        return arrayList;
    }

    public static void main(String[] args)
    {
        ListNode listNode1 = new ListNode(11);
        ListNode listNode2 = new ListNode(22);
        ListNode listNode3 = new ListNode(33);

        listNode1.next = listNode2;
        listNode2.next = listNode3;

        ArrayList<Integer> alg = alg(listNode1);
        System.out.println(alg);
    }
}

class ListNode
{
    int      val;
    ListNode next = null;

    ListNode()
    {
    }

    ListNode(int val)
    {
        this.val = val;
    }

    ListNode(int val, ListNode next)
    {
        this.val = val;
        this.next = next;
    }
}
