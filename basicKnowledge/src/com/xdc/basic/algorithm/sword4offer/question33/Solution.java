package com.xdc.basic.algorithm.sword4offer.question33;

public class Solution
{
    public static boolean VerifySquenceOfBST(int[] sequence)
    {
        if (sequence == null || sequence.length == 0)
        {
            return false;
        }

        return VerifySquenceOfBST(sequence, 0, sequence.length - 1);
    }

    private static boolean VerifySquenceOfBST(int[] sequence, int start, int end)
    {
        if (!(0 <= start && start <= end && end <= sequence.length - 1))
        {
            return true;
        }

        // 最后一个元素为根节点。
        int root = end;

        // 找到左子树的右边界。
        int left = start;
        while (left <= end - 1 && sequence[root] > sequence[left])
        {
            left++;
        }

        // 找到右子树的左边界。
        int right = end - 1;
        while (right >= start && sequence[root] < sequence[right])
        {
            right--;
        }

        // 如果边界没有重合，说明数组中的元素不是左边的一片都根节点值小，右边的一片都比根节点值大，这是不符合二叉搜索树的性质的，所以返回false。
        if (left < right)
        {
            return false;
        }

        return VerifySquenceOfBST(sequence, start, left - 1) && VerifySquenceOfBST(sequence, right + 1, end - 1);
    }

    @SuppressWarnings("unused")
    private static boolean VerifySquenceOfBST2(int[] sequence, int start, int end)
    {
        if (!(0 <= start && start <= end && end <= sequence.length - 1))
        {
            return true;
        }

        // 最后一个元素为根节点。
        int root = end;

        // 找到左子树与右子树的边界。
        int i = start;
        while (i <= end - 1 && sequence[root] > sequence[i])
        {
            i++;
        }

        // 校验右子树中的元素是否都比根节点值大。
        int j = i;
        while (j <= end - 1 && sequence[root] < sequence[j])
        {
            j++;
        }
        if (j <= end - 1)
        {
            return false;
        }

        return VerifySquenceOfBST2(sequence, start, i - 1) && VerifySquenceOfBST2(sequence, i, end - 1);
    }

    public static void main(String[] args)
    {
        System.out.println(VerifySquenceOfBST(newArray(5, 7, 6, 9, 11, 10, 8)));
        System.out.println(VerifySquenceOfBST(newArray(7, 4, 6, 5)));
    }

    private static int[] newArray(int... values)
    {
        return values;
    }
}
