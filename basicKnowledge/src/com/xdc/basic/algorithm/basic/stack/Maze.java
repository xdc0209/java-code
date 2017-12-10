package com.xdc.basic.algorithm.basic.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 迷宫路径求解：
 * 迷宫问题的求解可以抽象为连通图的遍历，因此主要有两种方法。
 * 
 * 第一种方法是：深度优先搜索(DFS)加回溯。
 * 其优点：无需像广度优先搜索那样(BFS)记录前驱结点。
 * 其缺点：找到的第一条可行路径不一定是最短路径，如果需要找到最短路径，那么需要找出所有可行路径后，再逐一比较，求出最短路径。
 * 
 * 第二种方法是：广度优先搜索(BFS)。
 * 其优点：找出的第一条路径就是最短路径。
 * 其缺点：需要记录结点的前驱结点，来形成路径。
 */
public class Maze
{
    public static final int    MAZE_PASSAGE   = -1;                        // 迷宫中通道的值。

    public static final int    MAZE_ROADBLOCK = -2;                        // 迷宫中障碍物的值。

    private static final int   DIRECTION_N    = 4;                         // 方向个数。

    private static final int[] DIRECTION_X    = new int[] { -1, 0, 1, 0 }; // 顺时针：上右下左。

    private static final int[] DIRECTION_Y    = new int[] { 0, 1, 0, -1 }; // 顺时针：上右下左。

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

        return maze[point.getX()][point.getY()] == MAZE_PASSAGE;
    }

    /**
     * 打印迷宫及路径。
     */
    private static void printPath(int[][] maze, List<Point> path)
    {
        int[][] clone = new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++)
        {
            for (int j = 0; j < maze[i].length; j++)
            {
                clone[i][j] = maze[i][j];
            }
        }

        int step = 1;
        for (Point point : path)
        {
            clone[point.getX()][point.getY()] = step++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clone.length; i++)
        {
            for (int j = 0; j < clone[i].length; j++)
            {
                if (clone[i][j] == MAZE_PASSAGE)
                {
                    sb.append(".... ");
                }
                else if (clone[i][j] == MAZE_ROADBLOCK)
                {
                    sb.append("#### ");
                }
                else
                {
                    sb.append(String.format("%4d ", clone[i][j]));
                }
            }
            sb.append("\n");
        }
        sb.append("\n");

        System.out.println(sb.toString());
    }

    /**
     * 打印迷宫及路径。
     */
    private static void printPaths(int[][] maze, List<List<Point>> paths)
    {
        for (List<Point> path : paths)
        {
            printPath(maze, path);
        }
    }

    /**
     * 在所有路径中找到最短路径。
     */
    private static List<List<Point>> shortestPaths(List<List<Point>> paths)
    {
        List<List<Point>> shortestPaths = new ArrayList<List<Point>>();

        int minstep = Integer.MAX_VALUE;
        for (List<Point> path : paths)
        {
            if (path.size() < minstep)
            {
                minstep = path.size();
            }
        }

        for (List<Point> path : paths)
        {
            if (minstep == path.size())
            {
                shortestPaths.add(path);
            }
        }

        return shortestPaths;
    }

    /**
     * 迷宫的所有路径(递归DFS+回溯)
     * 
     * @param maze
     *            迷宫。
     * @param path
     *            递归时使用的临时缓存。
     * @param paths
     *            所有路径。
     * @param start
     *            开始位置。
     * @param end
     *            结束位置。
     */
    public static void paths1(int[][] maze, List<Point> path, List<List<Point>> paths, Point start, Point end)
    {
        if (!isPositionPassable(maze, start))
        {
            throw new IllegalArgumentException("Start point is not a passage.");
        }

        if (!isPositionPassable(maze, end))
        {
            throw new IllegalArgumentException("End point is not a passage.");
        }

        path.add(start); // 添加到路径。记住一个规则，最好在同一层方法中进行尝试和回溯，在代码上的特点是尝试和回溯的代码是对称的，但注意这个对称不是指的数量，而是在各个分支中对称。
        // printPath(maze, path);

        if (start.equals(end))
        {
            paths.add(new ArrayList<Point>(path));
            path.remove(path.size() - 1); // 从路径移除。
            // printPath(maze, path);
            return;
        }

        for (int d = 0; d < DIRECTION_N; d++)
        {
            Point next = nextPoint(start, d);
            if (isPositionPassable(maze, next) && !path.contains(next))
            {
                paths1(maze, path, paths, next, end);
            }
        }

        path.remove(path.size() - 1); // 从路径移除。
        // printPath(maze, path);
    }

    /**
     * 迷宫的所有路径(栈DFS+回溯)
     */
    public static List<List<Point>> paths2(int[][] maze, Point start, Point end)
    {
        if (!isPositionPassable(maze, start))
        {
            throw new IllegalArgumentException("Start point is not a passage.");
        }

        if (!isPositionPassable(maze, end))
        {
            throw new IllegalArgumentException("End point is not a passage.");
        }

        List<List<Point>> paths = new ArrayList<List<Point>>();
        if (start.equals(end))
        {
            List<Point> path = new ArrayList<Point>();
            path.add(start);
            paths.add(path);
            return paths;
        }

        Stack<Point> stack1 = new Stack<Point>(); // 存储已访问，但是可能存在未访问的方向的位置。
        Stack<Integer> stack2 = new Stack<Integer>(); // 存储stack1中对应位置的刚刚访问过的方向。

        stack1.push(start);
        stack2.push(-1); // 入栈-1，代表还未访问位置的任何方向。

        while (!stack1.isEmpty())
        {
            Point point = stack1.pop();
            Integer d = stack2.pop();

            for (d = nextDirection(d); d > -1; d = nextDirection(d))
            {
                Point next = nextPoint(point, d);
                if (isPositionPassable(maze, next) && !stack1.contains(next))
                {
                    stack1.push(point); // 可能存在未访问的方向，重新入栈。
                    stack2.push(d); // 入栈刚刚访问过的方向。

                    stack1.push(next); // 入栈刚刚访问过的位置。
                    stack2.push(-1); // 入栈-1，代表还未访问位置的任何方向。

                    if (next.equals(end))
                    {
                        List<Point> path = new ArrayList<Point>();
                        for (int i = 0; i < stack1.size(); i++)
                        {
                            path.add(stack1.get(i));
                        }
                        paths.add(path);

                        stack1.pop();
                        stack2.pop();
                    }

                    break;
                }
            }
        }

        return paths;
    }

    /**
     * 迷宫的所有路径(栈DFS+回溯)
     */
    public static List<List<Point>> paths3(int[][] maze, Point start, Point end)
    {
        if (!isPositionPassable(maze, start))
        {
            throw new IllegalArgumentException("Start point is not a passage.");
        }

        if (!isPositionPassable(maze, end))
        {
            throw new IllegalArgumentException("End point is not a passage.");
        }

        List<List<Point>> paths = new ArrayList<List<Point>>();
        if (start.equals(end))
        {
            List<Point> path = new ArrayList<Point>();
            path.add(start);
            paths.add(path);
            return paths;
        }

        Stack<Point> stack1 = new Stack<Point>();
        Stack<Integer> stack2 = new Stack<Integer>();

        stack1.push(start);
        stack2.push(-1);

        while (!stack1.isEmpty())
        {
            Point point = stack1.pop();
            Integer d = stack2.pop();

            // 循环直到找到下个未访问的方向，或者到所有方向都访问完。
            Point next = null;
            d = nextDirection(d);
            while (d > -1)
            {
                next = nextPoint(point, d);
                if (isPositionPassable(maze, next) && !stack1.contains(next))
                {
                    break;
                }

                d = nextDirection(d);
            }

            if (d > -1)
            {
                stack1.push(point);
                stack2.push(d);

                stack1.push(next);
                stack2.push(-1);

                if (next.equals(end))
                {
                    List<Point> path = new ArrayList<Point>();
                    for (int i = 0; i < stack1.size(); i++)
                    {
                        path.add(stack1.get(i));
                    }
                    paths.add(path);

                    stack1.pop();
                    stack2.pop();
                }
            }
        }

        return paths;
    }

    /**
     * 迷宫的所有路径(栈DFS+回溯)
     */
    public static List<List<Point>> paths4(int[][] maze, Point start, Point end)
    {
        if (!isPositionPassable(maze, start))
        {
            throw new IllegalArgumentException("Start point is not a passage.");
        }

        if (!isPositionPassable(maze, end))
        {
            throw new IllegalArgumentException("End point is not a passage.");
        }

        List<List<Point>> paths = new ArrayList<List<Point>>();
        if (start.equals(end))
        {
            List<Point> path = new ArrayList<Point>();
            path.add(start);
            paths.add(path);
            return paths;
        }

        Stack<Point> stack1 = new Stack<Point>();
        Stack<Integer> stack2 = new Stack<Integer>();

        stack1.push(start);
        stack2.push(-1);

        while (!stack1.isEmpty())
        {
            // 注意这里只是取栈顶元素，并不出栈。
            Point point = stack1.peek();
            Integer d = stack2.peek();

            // 循环直到找到下个未访问的方向，或者到所有方向都访问完。
            Point next = null;
            d = nextDirection(d);
            while (d > -1)
            {
                next = nextPoint(point, d);
                if (isPositionPassable(maze, next) && !stack1.contains(next))
                {
                    break;
                }

                d = nextDirection(d);
            }

            if (d > -1) // 还存在未曾访问过的方向。
            {
                // 更新顶点访问过的方向。
                stack2.pop();
                stack2.push(d);

                stack1.push(next);
                stack2.push(-1);

                if (next.equals(end))
                {
                    List<Point> path = new ArrayList<Point>();
                    for (int i = 0; i < stack1.size(); i++)
                    {
                        path.add(stack1.get(i));
                    }
                    paths.add(path);

                    stack1.pop();
                    stack2.pop();
                }
            }
            else // 不存在未曾访问过的方向，该顶点出栈。
            {
                stack1.pop();
                stack2.pop();
            }
        }

        return paths;
    }

    /**
     * 迷宫的第一条最短路径(BFS)
     */
    public static List<Point> shortestPath(int[][] maze, Point start, Point end)
    {
        if (!isPositionPassable(maze, start))
        {
            throw new IllegalArgumentException("Start point is not a passage.");
        }

        if (!isPositionPassable(maze, end))
        {
            throw new IllegalArgumentException("End point is not a passage.");
        }

        List<Point> path = new ArrayList<Point>();
        if (start.equals(end))
        {
            path.add(start);
            return path;
        }

        int[][] distances = new int[maze.length][maze[0].length]; // 存储各个位置到起始位置的最短距离。
        int[][] father = new int[maze.length][maze[0].length]; // 存储各个位置在BFS拓展过程中的父亲位置到此位置的方向。
        for (int i = 0; i < maze.length; i++)
        {
            for (int j = 0; j < maze[i].length; j++)
            {
                distances[i][j] = maze[i][j];
                father[i][j] = maze[i][j];
            }
        }

        // 使用BFS，计算距离矩阵。
        Queue<Point> queue = new LinkedBlockingQueue<Point>();

        queue.add(start);
        distances[start.getX()][start.getY()] = 0;
        father[start.getX()][start.getY()] = 8;

        while (!queue.isEmpty())
        {
            Point point = queue.remove();

            for (int d = 0; d < 4; d++)
            {
                Point next = nextPoint(point, d);
                if (isPositionPassable(maze, next) && distances[next.getX()][next.getY()] == Maze.MAZE_PASSAGE) // 下个位置可通过，且没有计算过最短距离。
                {
                    queue.add(next);
                    distances[next.getX()][next.getY()] = distances[point.getX()][point.getY()] + 1;
                    father[next.getX()][next.getY()] = d;

                    if (next.equals(end))
                    {
                        queue.clear();
                        break; // 遍历到终点就可以了，不用遍历整个迷宫。
                    }
                }
            }
        }

        // 打印迷宫中各个位置到起始位置的最短距离。
        // printPath(distances, new ArrayList<Point>());

        // 打印迷宫中各个位置在BFS拓展过程中的父亲位置到此位置的方向。
        // printPath(father, new ArrayList<Point>());

        // 如果终点没有计算过最短距离，则终点被障碍物包围，不可达。
        if (distances[end.getX()][end.getY()] == Maze.MAZE_PASSAGE)
        {
            return path;
        }

        // 根据记录的方向，逆向地从终点到起点。
        Stack<Point> stack = new Stack<Point>();
        Point point = end;
        while (father[point.getX()][point.getY()] >= 0 && father[point.getX()][point.getY()] <= 3)
        {
            stack.push(point);

            int x = point.getX() - DIRECTION_X[father[point.getX()][point.getY()]];
            int y = point.getY() - DIRECTION_Y[father[point.getX()][point.getY()]];

            point = new Point(x, y);
        }
        stack.push(start);

        while (!stack.isEmpty())
        {
            path.add(stack.pop());
        }

        return path;
    }

    /**
     * 迷宫的所有最短路径(BFS+栈DFS+回溯)
     */
    public static List<List<Point>> shortestPaths(int[][] maze, Point start, Point end)
    {
        if (!isPositionPassable(maze, start))
        {
            throw new IllegalArgumentException("Start point is not a passage.");
        }

        if (!isPositionPassable(maze, end))
        {
            throw new IllegalArgumentException("End point is not a passage.");
        }

        List<List<Point>> paths = new ArrayList<List<Point>>();
        if (start.equals(end))
        {
            List<Point> path = new ArrayList<Point>();
            path.add(start);
            paths.add(path);
            return paths;
        }

        // 存储各个位置到起始位置的最短距离。
        int[][] distances = new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++)
        {
            for (int j = 0; j < maze[i].length; j++)
            {
                distances[i][j] = maze[i][j];
            }
        }

        // 使用BFS，计算距离矩阵。
        Queue<Point> queue = new LinkedBlockingQueue<Point>();

        queue.add(start);
        distances[start.getX()][start.getY()] = 0;

        while (!queue.isEmpty())
        {
            Point point = queue.remove();

            for (int d = 0; d < 4; d++)
            {
                Point next = nextPoint(point, d);
                if (isPositionPassable(maze, next) && distances[next.getX()][next.getY()] == Maze.MAZE_PASSAGE) // 下个位置可通过，且没有计算过最短距离。
                {
                    queue.add(next);
                    distances[next.getX()][next.getY()] = distances[point.getX()][point.getY()] + 1;

                    if (next.equals(end))
                    {
                        queue.clear();
                        break; // 遍历到终点就可以了，不用遍历整个迷宫。
                    }
                }
            }
        }

        // 打印迷宫中各个位置到起始位置的最短距离。
        // printPath(distances, new ArrayList<Point>());

        // 如果终点没有计算过最短距离，则终点被障碍物包围，不可达。
        if (distances[end.getX()][end.getY()] == Maze.MAZE_PASSAGE)
        {
            return paths;
        }

        // 使用DFS，生成所有的最短路径。
        Stack<Point> stack1 = new Stack<Point>();
        Stack<Integer> stack2 = new Stack<Integer>();

        stack1.add(end);
        stack2.push(-1);

        while (!stack1.isEmpty())
        {
            Point point = stack1.pop();
            Integer d = stack2.pop();

            for (d = nextDirection(d); d > -1; d = nextDirection(d))
            {
                Point next = nextPoint(point, d);
                if (isPositionPassable(maze, next) && !stack1.contains(next)
                        && distances[next.getX()][next.getY()] < distances[point.getX()][point.getY()]) // 下个位置可通过，且下个位置的最短距离比当前位置的小。
                {
                    stack1.push(point);
                    stack2.push(d);

                    stack1.push(next);
                    stack2.push(-1);

                    if (next.equals(start))
                    {
                        List<Point> path = new ArrayList<Point>();
                        for (int i = stack1.size() - 1; i >= 0; i--)
                        {
                            path.add(stack1.get(i));
                        }
                        paths.add(path);

                        stack1.pop();
                        stack2.pop();
                    }

                    break;
                }
            }
        }

        return paths;
    }

    public static void main(String[] args)
    {
        MazeBuiler builer1 = MazeBuiler.newMazeBuiler();
        builer1.addRow(0, 0);
        builer1.addRow(0, 0);

        MazeBuiler builer2 = MazeBuiler.newMazeBuiler();
        builer2.addRow(0, 0, 0);
        builer2.addRow(0, 0, 0);
        builer2.addRow(0, 0, 0);

        MazeBuiler builer3 = MazeBuiler.newMazeBuiler();
        builer3.addRow(0, 0, 0, 0);
        builer3.addRow(0, 0, 0, 0);
        builer3.addRow(0, 0, 0, 0);
        builer3.addRow(0, 0, 0, 0);

        MazeBuiler builer4 = MazeBuiler.newMazeBuiler();
        builer4.addRow(0, 0, 0, 0, 0);
        builer4.addRow(0, 0, 0, 0, 0);
        builer4.addRow(0, 0, 0, 0, 0);
        builer4.addRow(0, 0, 0, 0, 0);
        builer4.addRow(0, 0, 0, 0, 0);

        MazeBuiler builer5 = MazeBuiler.newMazeBuiler();
        builer5.addRow(0, 0, 0, 0, 0);
        builer5.addRow(0, 1, 0, 1, 0);
        builer5.addRow(0, 0, 1, 0, 0);
        builer5.addRow(0, 1, 0, 0, 1);
        builer5.addRow(0, 0, 0, 0, 0);

        MazeBuiler builer6 = MazeBuiler.newMazeBuiler();
        builer6.addRow(0, 0, 1, 0, 0);
        builer6.addRow(0, 1, 0, 0, 0);
        builer6.addRow(0, 1, 0, 1, 1);
        builer6.addRow(0, 1, 0, 0, 0);
        builer6.addRow(0, 0, 0, 1, 0);
        builer6.addRow(0, 0, 0, 0, 0);

        int[][] maze = builer2.toMaze();

        List<List<Point>> paths = paths2(maze, new Point(0, 0), new Point(maze.length - 1, maze[0].length - 1));

        System.out.println("所有的路径的个数：");
        System.out.println(paths.size());
        System.out.println("所有的路径：");
        printPaths(maze, paths);
        System.out.println();

        List<List<Point>> shortestPaths = shortestPaths(paths);
        System.out.println("所有的最短路径的个数：");
        System.out.println(shortestPaths.size());
        System.out.println("所有的最短路径：");
        printPaths(maze, shortestPaths);
        System.out.println();
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

class MazeBuiler
{
    List<List<Integer>> maze = new ArrayList<List<Integer>>();

    private MazeBuiler()
    {
    }

    public static MazeBuiler newMazeBuiler()
    {
        return new MazeBuiler();
    }

    public int[][] toMaze()
    {
        int[][] clone = new int[maze.size()][maze.get(0).size()];
        for (int i = 0; i < maze.size(); i++)
        {
            for (int j = 0; j < maze.get(i).size(); j++)
            {
                if (maze.get(i).get(j) == 0)
                {
                    clone[i][j] = Maze.MAZE_PASSAGE;
                }
                else if (maze.get(i).get(j) == 1)
                {
                    clone[i][j] = Maze.MAZE_ROADBLOCK;
                }
            }
        }

        return clone;
    }

    public MazeBuiler addRow(Integer... row)
    {
        maze.add(Arrays.asList(row));
        return this;
    }
}
