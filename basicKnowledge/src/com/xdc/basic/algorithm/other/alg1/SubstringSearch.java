package com.xdc.basic.algorithm.other.alg1;

/**
 * 子字符串搜素。
 * 
 * 假设text长度为M，search长度为N。
 * BF(Brute Force，暴力检索)：或叫朴素算法，很朴素，没有花样。时间复杂度O(M*N)。
 * RK(Robin-Karp，哈希检索)
 * KMP(教科书上最常见算法)：利用最大前后缀加快搜索。时间复杂度O(M+N)。
 * BM(Boyer Moore)：比KMP快。平均时间复杂度O(M)。
 * Sunday：比BM快。平均时间复杂度O(M)。
 * 
 * 参考：http://blog.csdn.net/v_july_v/article/details/7041827
 * 参考：http://www.ruanyifeng.com/blog/2013/05/Knuth–Morris–Pratt_algorithm.html
 */
public class SubstringSearch
{
    /**
     * Brute Force，暴力检索。
     * 假设text长度为M，search长度为N，则时间复杂度是是O(M*N)。
     */
    public static int BF_Search1(String text, String search)
    {
        if (text == null || text.isEmpty() || search == null || text.isEmpty())
        {
            return -1;
        }

        int i = 0;
        while (i < text.length() - search.length() + 1)
        {
            int j = 0;
            while (j < search.length() && text.charAt(i + j) == search.charAt(j))
            {
                j++;
            }

            if (j == search.length())
            {
                return i;
            }

            i++;
        }

        return -1;
    }

    /**
     * Brute Force，暴力检索。
     * 假设text长度为M，search长度为N，则时间复杂度是是O(M*N)。
     */
    public static int BF_Search2(String text, String search)
    {
        if (text == null || text.isEmpty() || search == null || text.isEmpty())
        {
            return -1;
        }

        int i = 0;
        int j = 0;
        while (i < text.length() - search.length() + 1 && j < search.length())
        {
            if (text.charAt(i) == search.charAt(j))
            {
                // ①如果当前字符匹配成功（即S[i] == P[j]），则i++，j++，继续匹配下一个字符。
                i++;
                j++;
            }
            else
            {
                // ②如果失配（即S[i]! = P[j]），令i = i - (j - 1)，j = 0。相当于每次匹配失败时，i回溯，j被置为0。
                i = i - j + 1;
                j = 0;
            }
        }

        // 匹配成功，返回模式串p在文本串s中的位置，否则返回-1。
        if (j == search.length())
        {
            return i - j;
        }

        return -1;
    }

    /**
     * KMP算法。
     * 假设text长度为M，search长度为N，则时间复杂度是是O(M+N)。
     */
    public static int KMP_Search(String text, String search)
    {
        if (text == null || text.isEmpty() || search == null || text.isEmpty())
        {
            return -1;
        }

        int[] next = KMP_GetNext(search);

        int i = 0;
        int j = 0;
        while (i < text.length() && j < search.length())
        {
            // ①如果j = -1，或者当前字符匹配成功（即S[i] == P[j]），都令i++，j++，继续匹配下一个字符。
            if (j == -1 || text.charAt(i) == search.charAt(j))
            {
                i++;
                j++;
            }
            else
            {
                // ②如果j != -1，且当前字符匹配失败（即S[i] != P[j]），则令i不变，j = next[j]，next[j]即为j所对应的next值。
                j = next[j];
            }
        }

        // 匹配成功，返回模式串p在文本串s中的位置，否则返回-1。
        if (j == search.length())
        {
            return i - j;
        }

        return -1;
    }

    private static int[] KMP_GetNext(String search)
    {
        int[] next = new int[search.length()];

        next[0] = -1;
        int k = -1; // 最大前后缀匹配中前缀的下一个下标（最大前后缀匹配的长度）。
        int j = 0; // 模式串下标。
        while (j < search.length() - 1) // j为计算当前next值的前一个下标。
        {
            // k == -1表示0~j的字符串中没有前后缀匹配。
            // 如果pattern.charAt(k) == pattern.charAt(j)，最大前后缀匹配长度加1。
            // p[k]表示前缀，p[j]表示后缀。
            if (k == -1 || search.charAt(j) == search.charAt(k))
            {
                ++k;
                ++j;
                next[j] = k; // next[j]表示search[0]~search[k-1]与search[j-k]~search[j-1]相等。
            }
            else
            {
                k = next[k];
            }
        }

        return next;
    }

    /**
     * BM算法。不实现了，用到再研究。
     * 假设text长度为M，search长度为N，则时间复杂度为O(M)。注：平均时间复杂度为O(M)，最坏是O(M*N)。
     * 虽然KMP算法号称O(M+N)的时间复杂度，BM最坏为O(M*N)，但实际效率反而是BM更高，这是由于实际情况时经常能达到BM的平均效率。
     * 各种记事本的“查找”功能(CTRL + F)一般都是采用的此算法。
     */
    public static int BM_Search(String text, String search)
    {
        return -1;
    }

    /**
     * Sunday算法，它是BM算法的一种改进型。不实现了，用到再研究。
     * 假设text长度为M，search长度为N，则时间复杂度为O(M)。注：平均时间复杂度为O(M)，最坏是O(M*N)。与BM相比，虽然复杂度相同，实际上Sunday比BM算法还要快。
     */
    public static int Sunday_Search(String text, String search)
    {
        return -1;
    }

    public static void main(String[] args)
    {
        System.out.println(BF_Search1("BBC ABCDAB ABCDABCDABDE", "ABCDABD"));
        System.out.println(BF_Search1("BBC ABCDAB ABCDABCDABDE", "ABCDABD"));
        System.out.println(KMP_Search("BBC ABCDAB ABCDABCDABDE", "ABCDABD"));
    }
}
