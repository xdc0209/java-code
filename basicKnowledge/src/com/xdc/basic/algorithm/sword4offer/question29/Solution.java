package com.xdc.basic.algorithm.sword4offer.question29;

import java.util.ArrayList;

public class Solution
{
    public static ArrayList<Integer> printMatrix(int[][] matrix)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0)
        {
            return list;
        }

        // ---上3---
        // 左2---右0
        // ---下1---
        int d = 0; // 当前方向：0,1,2,3-->右下左上。
        int[] dx = { 0, 1, 0, -1 }; // x方向(上下方向)移动。
        int[] dy = { 1, 0, -1, 0 }; // y方向(左右方向)移动。

        int[] w = { matrix[0].length, matrix.length, -1, -1 }; // 各个方向上的墙壁。

        int x = 0 - dx[d]; // 当前x坐标。注意这里是在原点后退dx[d]。
        int y = 0 - dy[d]; // 当前y坐标。注意这里是在原点后退dy[d]。

        while ((w[0] - w[2] > 1) && (w[1] - w[3] > 1)) // 墙壁间有空隙就一直循环。
        {
            while ((w[3] < x + dx[d]) && (x + dx[d] < w[1]) && (w[2] < y + dy[d]) && (y + dy[d] < w[0])) // 如果当前方向上的下个位置是合法的，就一直循环。
            {
                x = x + dx[d];
                y = y + dy[d];
                list.add(matrix[x][y]);
            }

            w[prev(d)] = w[prev(d)] - dx[prev(d)] - dy[prev(d)]; // 前一个方向上的墙壁向内收缩。例如当前方向向右，那么当向右走不了时，上侧墙壁向下移动。
            d = next(d); // 切换到下个方向。
        }

        return list;
    }

    private static int prev(int d)
    {
        return (d + -1 + 4) % 4;
    }

    private static int next(int d)
    {
        return (d + 1) % 4;
    }

    public static void main(String[] args)
    {
        System.out.println();

        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[i].length; j++)
            {
                System.out.printf("%3d", matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        System.out.println(printMatrix(matrix));
    }
}
