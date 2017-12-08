package com.xdc.basic.algorithm.sword4offer.question50;

public class Solution2
{
    private int[] hashtable;

    private int   index;

    public Solution2()
    {
        hashtable = new int[256];// 题目说明了字符在ASCII字符内。
        for (int i = 0; i < hashtable.length; i++)
        {
            hashtable[i] = -1;
        }

        index = 0;
    }

    // Insert one char from string stream.
    public void Insert(char ch)
    {
        if (hashtable[ch] == -1)
        {
            hashtable[ch] = index;
        }
        else if (hashtable[ch] >= 0)
        {
            hashtable[ch] = -2;
        }

        index++;
    }

    // Return the first appearence once char in current string stream.
    public char FirstAppearingOnce()
    {
        int min = -1;
        for (int i = 0; i < hashtable.length; i++)
        {
            if (hashtable[i] >= 0 && (min == -1 || hashtable[i] < hashtable[min]))
            {
                min = i;
            }
        }

        return min == -1 ? '#' : (char) min;
    }

    public static void main(String[] args)
    {
        Solution2 solution2 = new Solution2();

        String string = "google";

        System.out.println(solution2.FirstAppearingOnce());

        for (int i = 0; i < string.length(); i++)
        {
            solution2.Insert(string.charAt(i));
            System.out.println(solution2.FirstAppearingOnce());
        }
    }
}
