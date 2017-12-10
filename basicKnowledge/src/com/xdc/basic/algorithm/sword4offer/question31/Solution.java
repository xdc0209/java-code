package com.xdc.basic.algorithm.sword4offer.question31;

import java.util.Stack;

public class Solution
{
    public static boolean IsPopOrder(int[] pushA, int[] popA)
    {
        if (pushA == null || pushA.length == 0 || popA == null || popA.length == 0 || pushA.length != popA.length)
        {
            return false;
        }

        int pushAIndex = 0;
        int popAIndex = 0;

        Stack<Integer> stack = new Stack<Integer>();

        while (pushAIndex < pushA.length || !stack.isEmpty())
        {
            if (pushAIndex < pushA.length && pushA[pushAIndex] == popA[popAIndex])
            {
                pushAIndex++;
                popAIndex++;
            }
            else if (!stack.isEmpty() && stack.peek() == popA[popAIndex])
            {
                stack.pop();
                popAIndex++;
            }
            else
            {
                if (pushAIndex < pushA.length)
                {
                    stack.push(pushA[pushAIndex]);
                    pushAIndex++;
                }
                else
                {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean IsPopOrder2(int[] pushA, int[] popA)
    {
        if (pushA == null || pushA.length == 0 || popA == null || popA.length == 0 || pushA.length != popA.length)
        {
            return false;
        }

        Stack<Integer> stack = new Stack<Integer>();

        for (int i = 0, j = 0; i < pushA.length; i++)
        {
            stack.push(pushA[i]);

            while (!stack.isEmpty() && stack.peek() == popA[j])
            {
                stack.pop();
                j++;
            }
        }

        return stack.empty();
    }

    public static void main(String[] args)
    {
        System.out.println(IsPopOrder(newArray(1, 2, 3, 4, 5), newArray(5, 4, 3, 2, 1)));
        System.out.println(IsPopOrder(newArray(1, 2, 3, 4, 5), newArray(4, 5, 3, 2, 1)));
        System.out.println(IsPopOrder(newArray(1, 2, 3, 4, 5), newArray(4, 3, 5, 1, 2)));
        System.out.println(IsPopOrder(newArray(1, 2, 3, 4, 5), newArray(1, 2, 3, 4, 5)));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
