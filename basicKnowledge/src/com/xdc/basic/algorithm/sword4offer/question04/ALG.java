package com.xdc.basic.algorithm.sword4offer.question04;

public class ALG
{
    /**
     * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     */
    public static boolean alg(int target, int[][] array)
    {
        // 数组为空，返回false
        if (array == null || array.length == 0)
        {
            return false;
        }

        int row = array.length - 1;
        int col = 0;
        int maxColLen = 0;

        // 考虑到不等长问题，获得最长的一维的数组长度。
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] != null && maxColLen < array.length)
            {
                maxColLen = array.length;
            }
        }

        while (row >= 0 && col < maxColLen)
        {
            // 行为空，或者行的col列后已无元素，或者原点的值大于待搜索的值
            if (array[row] == null || array[row].length <= col || array[row][col] > target)
            {
                row--;
            }

            // 原点的值小于待搜索的值
            else if (array[row][col] < target)
            {
                col++;
            }

            // 原点的值等于待搜索的值
            else
            {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args)
    {
        int[][] array1 = { { 1, 2, 8 }, null, { 2, 4, 9, 12 }, { 4, 7 }, { 6, 8, 11, 15 }, null };
        boolean alg1 = ALG.alg(12, array1);
        System.out.println(alg1);

        int[][] array2 = new int[][] { { 2, 3, 4 }, { 4, 5, 6 }, { 7, 8, 9 } };
        boolean alg2 = ALG.alg(5, array2);
        System.out.println(alg2);
    }
}
