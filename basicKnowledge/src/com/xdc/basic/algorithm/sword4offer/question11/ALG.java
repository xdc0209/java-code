package com.xdc.basic.algorithm.sword4offer.question11;

public class ALG
{
    public static int alg(int[] n)
    {
        // 输入非法
        if (n == null || n.length == 0)
        {
            throw new RuntimeException("Invalid input!");
        }

        // 如果旋转数组是在原始非递减有序数组基础上旋转了一个0个元素，即无移动，那么数组的第一个元素就是最小的。
        if (n[0] < n[n.length - 1])
        {
            return n[0];
        }

        // 考虑到数组开头可能有多个连续相等的元素的情况，从数组开头开始，找到其中的最后面一个元素。
        int low = 0;
        int i = low + 1;
        for (; i < n.length; i++)
        {
            if (n[low] != n[i])
            {
                break;
            }
        }
        low = i - 1;

        // 考虑到数组结尾可能有多个连续相等的元素的情况，从数组结尾开始，找到其中的最前面一个元素。
        int high = n.length - 1;
        int j = high - 1;
        for (; j > low; j--)
        {
            if (n[high] != n[j])
            {
                break;
            }
        }
        high = j + 1;

        // 两个指针的距离是1时，表明第一个指针已经指向第一个递增子数组的末尾，而第二个指针指向第二个递增子数组的开头，第二个子数组的第一个数字就是最小的数字，因此第二个指针指向的数字就是我们查找的结果。
        while (high - low > 1)
        {
            int mid = (low + high) / 2;

            if (n[mid] > n[0])
            {
                low = mid;
            }
            else if (n[mid] < n[0])
            {
                high = mid;
            }
            else
            {
                throw new RuntimeException("Invalid input!");
            }
        }

        return n[high];
    }

    public static void main(String[] args)
    {
        int[] n1 = { 3, 4, 5, 1, 2 };
        int[] n2 = { 1, 0, 1, 1, 1 };
        int[] n3 = { 1, 1, 1, 0, 1 };

        int alg1 = alg(n1);
        int alg2 = alg(n2);
        int alg3 = alg(n3);

        System.out.println(alg1);
        System.out.println(alg2);
        System.out.println(alg3);
    }
}
