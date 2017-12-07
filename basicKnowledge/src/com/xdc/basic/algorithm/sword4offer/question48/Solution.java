package com.xdc.basic.algorithm.sword4offer.question48;

import java.util.HashMap;
import java.util.Map;

public class Solution
{
    public static int longestSubstringWithoutDuplication(String string)
    {
        if (string == null || string.length() == 0)
        {
            return 0;
        }

        int curLen = 0;

        int maxLen = 0;
        int start = -1;
        int end = -1;

        Map<Character, Integer> last = new HashMap<Character, Integer>();

        for (int i = 0, j = 0; j < string.length(); j++)
        {
            if (!last.containsKey(string.charAt(j)) || last.get(string.charAt(j)) < i)
            {
                curLen++;
            }
            else
            {
                curLen = j - last.get(string.charAt(j));
                i = last.get(string.charAt(j)) + 1;
            }

            last.put(string.charAt(j), j);

            if (curLen > maxLen)
            {
                maxLen = curLen;
                start = i;
                end = j;
            }
        }

        System.out.println(string + " " + start + " " + end + " " + maxLen);

        return maxLen;
    }

    public static void main(String[] args)
    {
        longestSubstringWithoutDuplication("arabcacfr");
        longestSubstringWithoutDuplication("abcdecfghi");
    }
}
