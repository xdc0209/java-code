package com.xdc.basic.algorithm.sword4offer.question12;

import java.util.Stack;

public class Solution
{
    private static final int[] DIRECTION_X = new int[] { -1, 0, 1, 0 }; // 顺时针：上右下左。

    private static final int[] DIRECTION_Y = new int[] { 0, 1, 0, -1 }; // 顺时针：上右下左。

    /**
     * 获得当前方向的下个方向。
     */
    private static int nextDirection(int direction)
    {
        // 合法值：-1、0、1、2。
        if (direction < -1 || direction > 2)
        {
            // -1代表已经没有下个方向了。
            return -1;
        }

        return direction + 1;
    }

    /**
     * 获得当前位置的当前方向上的下个位置。
     */
    private static Point nextPoint(Point point, int direction)
    {
        // 合法值：0、1、2、3。
        if (direction < 0 || direction > 3)
        {
            throw new IllegalArgumentException(
                    "Direction [" + direction + "] is not leagal. Direction should be in [0, 3].");
        }

        int x = point.getX() + DIRECTION_X[direction];
        int y = point.getY() + DIRECTION_Y[direction];

        return new Point(x, y);
    }

    /**
     * 当前位置是否可通过。位置必须在迷宫内，且必须在通道块上。
     */
    private static boolean isPositionPassable(char[][] maze, Point point)
    {
        if (point.getX() < 0 || point.getX() > maze.length - 1)
        {
            return false;
        }

        if (point.getY() < 0 || point.getY() > maze[point.getX()].length - 1)
        {
            return false;
        }

        return true;
    }

    /**
     * 面试题12：矩阵中的路径。
     */
    public static boolean hasPath(char[] matrix, int rows, int cols, char[] str)
    {
        if (matrix == null || rows < 1 || cols < 1 || matrix.length != rows * cols || str == null || str.length == 0)
        {
            return false;
        }

        char[][] maze = new char[rows][cols];
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                maze[i][j] = matrix[i * cols + j];
            }
        }

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                Stack<Point> stack1 = new Stack<Point>();
                Stack<Integer> stack2 = new Stack<Integer>();

                if (maze[i][j] == str[0])
                {
                    stack1.push(new Point(i, j));
                    stack2.push(-1);

                    if (stack1.size() == str.length)
                    {
                        return true;
                    }
                }

                while (!stack1.isEmpty())
                {
                    Point point = stack1.pop();
                    int direction = stack2.pop();

                    for (int d = nextDirection(direction); d > -1; d = nextDirection(d))
                    {
                        Point next = nextPoint(point, d);
                        if (isPositionPassable(maze, next) && !stack1.contains(next) && stack1.size() + 1 < str.length
                                && str[stack1.size() + 1] == maze[next.getX()][next.getY()])
                        {
                            stack1.push(point);
                            stack2.push(d);

                            stack1.push(next);
                            stack2.push(-1);

                            if (stack1.size() == str.length)
                            {
                                return true;
                            }

                            break;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static void main(String[] args)
    {
        boolean hasPath = Solution.hasPath("ABCESFCSADEE".toCharArray(), 3, 4, "ABCCED".toCharArray());
        System.out.println(hasPath);
    }
}

class Point
{
    private int x;

    private int y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return String.format("(%s,%s)", x, y);
    }
}
