package com.xdc.basic.algorithm.sword4offer.question13;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Solution
{
    private static final int[] DIRECTION_X = new int[] { -1, 0, 1, 0 }; // 顺时针：上右下左。

    private static final int[] DIRECTION_Y = new int[] { 0, 1, 0, -1 }; // 顺时针：上右下左。

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
    private static boolean isPositionPassable(int[][] maze, Point point)
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
     * 面试题13：机器人的运动范围。
     */
    public static int movingCount(int threshold, int rows, int cols)
    {
        if (threshold < 0 || rows <= 0 || cols <= 0)
        {
            return 0;
        }

        int[][] maze = new int[rows][cols];

        boolean[][] visited = new boolean[rows][cols];
        int movingCount = 0;

        Queue<Point> queue = new LinkedBlockingQueue<Point>();

        queue.add(new Point(0, 0));
        visited[0][0] = true;
        movingCount++;

        while (!queue.isEmpty())
        {
            Point point = queue.remove();

            for (int d = 0; d < 4; d++)
            {
                Point next = nextPoint(point, d);
                if (isPositionPassable(maze, next) && !visited[next.getX()][next.getY()]
                        && digitalSum(next) <= threshold)
                {
                    queue.add(next);
                    visited[next.getX()][next.getY()] = true;
                    movingCount++;
                }
            }
        }

        return movingCount;
    }

    public static int digitalSum(Point point)
    {
        int sum = 0;

        int x = point.getX();
        int y = point.getY();

        while (x > 0)
        {
            sum += x % 10;
            x = x / 10;
        }

        while (y > 0)
        {
            sum += y % 10;
            y = y / 10;
        }

        return sum;
    }

    public static void main(String[] args)
    {
        int movingCount = Solution.movingCount(18, 50, 50);
        System.out.println(movingCount);
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
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Point other = (Point) obj;
        if (x != other.x)
        {
            return false;
        }
        if (y != other.y)
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return String.format("(%s,%s)", x, y);
    }
}
