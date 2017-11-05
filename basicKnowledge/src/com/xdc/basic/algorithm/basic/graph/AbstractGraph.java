package com.xdc.basic.algorithm.basic.graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import com.xdc.basic.commons.ArrayUtil;

public abstract class AbstractGraph<T> implements Graph<T>
{
    /**
     * 深度优先遍历图。
     */
    @Override
    public void DFS(int v)
    {
        int vertexCount = getVertexCount();

        indexRangeCheck(v, 0, vertexCount);

        boolean[] visited = new boolean[vertexCount];

        for (int i = 0; i < vertexCount; i++)
        {
            int j = (v + i) % vertexCount;
            if (!visited[j])
            {
                DFS(j, visited);
            }
        }
    }

    /**
     * 深度优先遍历图的连通分量(递归实现)。
     */
    private void DFS(int v, boolean[] visited)
    {
        // 访问顶点。
        System.out.println(getVertex(v));
        visited[v] = true;

        for (int i = getFirstNeighbor(v); i > -1; i = getNextNeighbor(v, i))
        {
            if (!visited[i])
            {
                DFS(i, visited);
            }
        }
    }

    /**
     * 深度优先遍历图的连通分量(栈实现)。
     */
    @SuppressWarnings("unused")
    private void DFS2(int v, boolean[] visited)
    {
        // 存储已访问，但是可能存在未访问的邻接点的顶点的序号。
        Stack<Integer> stack = new Stack<Integer>();

        // 访问顶点。
        System.out.println(getVertex(v));
        visited[v] = true;

        stack.push(v);
        while (!stack.isEmpty())
        {
            int i = stack.pop();

            for (int j = getFirstNeighbor(i); j > -1; j = getNextNeighbor(i, j))
            {
                if (!visited[j])
                {
                    // 访问顶点。
                    System.out.println(getVertex(j));
                    visited[j] = true;

                    stack.push(i); // 可能存在未访问的邻接点，重新入栈。
                    stack.push(j);
                    break;
                }
            }
        }
    }

    /**
     * 深度优先遍历图的连通分量(栈实现)。
     */
    @SuppressWarnings("unused")
    private void DFS3(int v, boolean[] visited)
    {
        Stack<Integer> stack1 = new Stack<Integer>(); // 存储已访问，但是可能存在未访问的邻接点的顶点的序号。
        Stack<Integer> stack2 = new Stack<Integer>(); // 存储stack1中对应位置的顶点刚刚访问过的邻接点的序号。

        // 访问顶点。
        System.out.println(getVertex(v));
        visited[v] = true;

        stack1.push(v); // 可能存在未访问的邻接点，入栈。
        stack2.push(-1); // 入栈-1，代表还未访问i的任何邻接点。
        while (!stack1.isEmpty())
        {
            int i = stack1.pop();
            int j = stack2.pop();

            for (j = getNextNeighbor(i, j); j > -1; j = getNextNeighbor(i, j))
            {
                if (!visited[j])
                {
                    // 访问顶点。
                    System.out.println(getVertex(j));
                    visited[j] = true;

                    stack1.push(i); // 可能存在未访问的邻接点，重新入栈。
                    stack2.push(j); // 入栈刚刚访问过的邻接点。

                    stack1.push(j); // 入栈刚刚访问过的邻接点。
                    stack2.push(-1); // 入栈-1，代表还未访问j的任何邻接点。
                    break;
                }
            }
        }
    }

    /**
     * 深度优先遍历图的连通分量(栈实现)。
     */
    @SuppressWarnings("unused")
    private void DFS4(int v, boolean[] visited)
    {
        // 倒序存储未访问过的邻接点的序号。
        Stack<Integer> stack1 = new Stack<Integer>();

        stack1.push(v); // 初始入栈。
        visited[v] = true; // 提前标记访问标志，避免重复入栈，出栈后立刻访问。

        while (!stack1.isEmpty())
        {
            // 出栈并访问顶点。
            int i = stack1.pop();
            System.out.println(getVertex(i));

            // 使用stack2保证stack1中入栈的邻接点是倒序的。
            Stack<Integer> stack2 = new Stack<Integer>();
            for (int j = getFirstNeighbor(i); j > -1; j = getNextNeighbor(i, j))
            {
                if (!visited[j])
                {
                    stack2.push(j);
                    visited[j] = true;
                }
            }

            while (!stack2.isEmpty())
            {
                stack1.push(stack2.pop());
            }
        }
    }

    /**
     * 广度优先遍历图。
     */
    @Override
    public void BFS(int v)
    {
        int vertexCount = getVertexCount();

        indexRangeCheck(v, 0, vertexCount);

        boolean[] visited = new boolean[vertexCount];

        for (int i = 0; i < vertexCount; i++)
        {
            int j = (v + i) % vertexCount;
            if (!visited[j])
            {
                BFS(j, visited);
            }
        }
    }

    /**
     * 广度优先遍历图的连通分量。
     */
    private void BFS(int v, boolean[] visited)
    {
        // 存储已访问顶点的序号。
        Queue<Integer> queue = new LinkedBlockingQueue<Integer>();

        // 访问顶点。
        System.out.println(getVertex(v));
        visited[v] = true;

        queue.add(v);
        while (!queue.isEmpty())
        {
            int i = queue.remove();
            for (int j = getFirstNeighbor(i); j > -1; j = getNextNeighbor(i, j))
            {
                if (!visited[j])
                {
                    // 访问顶点。
                    System.out.println(getVertex(j));
                    visited[j] = true;

                    queue.add(j);
                }
            }
        }
    }

    /**
     * 最小生成树(普里姆)：边权值的和最小的生成树。时间复杂度O(n*n)。
     */
    @SuppressWarnings("unchecked")
    public void minCostSpanTreePrim(int v)
    {
        // 定义辅助类，保存临时计算结果，避免重复计算。
        class MinEdge
        {
            int startvex; // 生成树中的顶点。
            int endvex;   // 不在生成树中的顶点，此值初始化后不在改变，始终保持与数组的下标一致。
            int weight;   // 最小花费。
            int index;    // 输出次序。正数代表输出次序，-1代表不在生成树中。

            public MinEdge(int startvex, int endvex, int weight, int index)
            {
                this.startvex = startvex;
                this.endvex = endvex;
                this.weight = weight;
                this.index = index;
            }
        }

        int vertexCount = getVertexCount();

        indexRangeCheck(v, 0, vertexCount);

        // 记录各个顶点到生成树中最近的顶点及其花费(即权值)。花费为0，则代表此顶点已在生成树中。
        MinEdge[] minEdges = ArrayUtil.newArray(MinEdge.class, vertexCount);

        int edgeindex = 0;
        minEdges[v] = new MinEdge(-1, v, 0, edgeindex++);
        for (int i = 0; i < vertexCount; i++)
        {
            if (i != v)
            {
                minEdges[i] = new MinEdge(v, i, getEdge(v, i), -1);
            }
        }

        for (int i = 0; i < vertexCount; i++)
        {
            int minWeight = Integer.MAX_VALUE;
            int minEndvex = -1;
            for (int j = 0; j < vertexCount; j++)
            {
                if (minEdges[j].index == -1 && minEdges[j].weight < minWeight)
                {
                    minWeight = minEdges[j].weight;
                    minEndvex = j;
                }
            }

            if (minEndvex == -1)
            {
                break;
            }

            System.out.println(getVertex(minEdges[minEndvex].startvex) + "--" + getVertex(minEdges[minEndvex].endvex));
            minEdges[minEndvex].index = edgeindex++;

            for (int j = 0; j < vertexCount; j++)
            {
                if (minEdges[j].index == -1 && getEdge(minEndvex, j) < minEdges[j].weight)
                {
                    minEdges[j].startvex = minEndvex;
                    minEdges[j].weight = getEdge(minEndvex, j);
                }
            }
        }
    }

    /**
     * 最小生成树(普里姆)：边权值的和最小的生成树。时间复杂度O(n*n*n)。
     */
    public void minCostSpanTreePrim2(int v)
    {
        int vertexCount = getVertexCount();

        indexRangeCheck(v, 0, vertexCount);

        boolean[] visited = new boolean[vertexCount];

        visited[v] = true;

        for (int k = 0; k < vertexCount; k++)
        {
            int minWeight = Integer.MAX_VALUE;
            int minIIndex = -1;
            int minJIndex = -1;
            for (int i = 0; i < vertexCount; i++)
            {
                if (visited[i])
                {
                    for (int j = getFirstNeighbor(i); j > -1; j = getNextNeighbor(i, j))
                    {
                        if (!visited[j])
                        {
                            int weight = getEdge(i, j);
                            if (weight < minWeight)
                            {
                                minWeight = weight;
                                minIIndex = i;
                                minJIndex = j;
                            }
                        }
                    }
                }
            }

            if (minWeight == Integer.MAX_VALUE)
            {
                break;
            }

            System.out.println(getVertex(minIIndex) + "--" + getVertex(minJIndex));
            visited[minJIndex] = true;
        }
    }

    /**
     * 最小生成树(克鲁斯卡尔)：边权值的和最小的生成树。
     */
    @SuppressWarnings("unchecked")
    public void minCostSpanTreeKruskal()
    {
        class Edge
        {
            int startvex;
            int endvex;
            int weight;

            public Edge(int startvex, int endvex, int weight)
            {
                this.startvex = startvex;
                this.endvex = endvex;
                this.weight = weight;
            }
        }

        int vertexCount = getVertexCount();

        // 记录图的连通性，值一样的顶点在同一个连通分量内。
        int[] connVertexs = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++)
        {
            connVertexs[i] = i;
        }

        int edgeIndex = 0;
        int edgeCount = getEdgeCount();
        Edge[] edges = ArrayUtil.newArray(Edge.class, edgeCount);
        for (int i = 0; i < vertexCount; i++)
        {
            for (int j = getFirstNeighbor(i); j > -1; j = getNextNeighbor(i, j))
            {
                edges[edgeIndex++] = new Edge(i, j, getEdge(i, j));
            }
        }

        Arrays.sort(edges, new Comparator<Edge>()
        {
            @Override
            public int compare(Edge o1, Edge o2)
            {
                return o1.weight - o2.weight;
            }
        });

        for (int i = 0, connEdgeCount = 0; i < edgeCount && connEdgeCount < vertexCount - 1; i++)
        {
            int startvex = edges[i].startvex;
            int endvex = edges[i].endvex;

            if (connVertexs[startvex] == connVertexs[endvex])
            {
                continue;
            }

            int startConn = connVertexs[startvex];
            int endConn = connVertexs[endvex];
            for (int j = 0; j < vertexCount; j++)
            {
                if (connVertexs[j] == endConn)
                {
                    connVertexs[j] = startConn;
                }
            }

            System.out.println(getVertex(startvex) + "--" + getVertex(endvex));
            connEdgeCount++;
        }
    }

    /**
     * 拓扑排序：有向无环图排序。
     */
    public void toplogicalSort()
    {
        int vertexCount = getVertexCount();

        int[] indegrees = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++)
        {
            for (int j = getFirstNeighbor(i); j > -1; j = getNextNeighbor(i, j))
            {
                indegrees[j]++;
            }
        }

        // 存储入度为0的顶点。
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < vertexCount; i++)
        {
            if (indegrees[i] == 0)
            {
                stack.push(i);
            }
        }

        int sortedVertexCount = 0;
        while (!stack.isEmpty())
        {
            int i = stack.pop();
            System.out.println(getVertex(i));
            sortedVertexCount++;

            for (int j = getFirstNeighbor(i); j > -1; j = getNextNeighbor(i, j))
            {
                indegrees[j]--;
                if (indegrees[j] == 0)
                {
                    stack.push(j);
                }
            }
        }

        if (sortedVertexCount < vertexCount)
        {
            throw new RuntimeException("此图中存在回路！");
        }
    }

    /**
     * 关键路径：有向无环图中，源点(图中唯一的入度为0的顶点)到汇点(图中唯一的出度为0的顶点)的关键路径。图中可能存在多条关键路径。
     */
    public void criticalPath()
    {
        int vertexCount = getVertexCount();

        int[] indegrees = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++)
        {
            for (int j = getFirstNeighbor(i); j > -1; j = getNextNeighbor(i, j))
            {
                indegrees[j]++;
            }
        }

        Stack<Integer> stack1 = new Stack<Integer>(); // 存储入度为0的顶点。
        Stack<Integer> stack2 = new Stack<Integer>(); // 存储拓扑序列。

        for (int i = 0; i < vertexCount; i++)
        {
            if (indegrees[i] == 0)
            {
                stack1.push(i);
            }
        }

        // 各个顶点事件的最早发生时间。
        int[] ve = new int[vertexCount];

        int sortedVertexCount = 0;
        while (!stack1.isEmpty())
        {
            int i = stack1.pop();
            stack2.push(i);
            sortedVertexCount++;

            for (int j = getFirstNeighbor(i); j > -1; j = getNextNeighbor(i, j))
            {
                if (ve[j] < getEdge(i, j) + ve[i])
                {
                    ve[j] = getEdge(i, j) + ve[i];
                }

                indegrees[j]--;
                if (indegrees[j] == 0)
                {
                    stack1.push(j);
                }
            }
        }

        if (sortedVertexCount < vertexCount)
        {
            throw new RuntimeException("此图中存在回路！");
        }

        // 各个顶点事件的最迟发生时间。
        int[] vl = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++)
        {
            vl[i] = ve[stack2.peek()];
        }

        while (!stack2.isEmpty())
        {
            int i = stack2.pop();

            for (int j = getFirstNeighbor(i); j > -1; j = getNextNeighbor(i, j))
            {
                if (vl[i] > vl[j] - getEdge(i, j))
                {
                    vl[i] = vl[j] - getEdge(i, j);
                }
            }
        }

        System.out.println("关键路径上的顶点：");
        for (int i = 0; i < vertexCount; i++)
        {
            if (vl[i] == ve[i])
            {
                System.out.println(getVertex(i));
            }
        }
        System.out.println();

        System.out.println("关键路径上的边：");
        for (int i = 0; i < vertexCount; i++)
        {
            for (int j = getFirstNeighbor(i); j > -1; j = getNextNeighbor(i, j))
            {
                int ee = ve[i];
                int el = vl[j] - getEdge(i, j);

                System.out.println(String.format("edge：%s--%s, weight：%s, ve：%s, vl：%s, critical：%s.", getVertex(i),
                        getVertex(j), getEdge(i, j), ee, el, (el - ee == 0)));
            }
        }
        System.out.println();
    }

    /**
     * 单源最短路径(迪杰斯特拉)。
     */
    public void shortestPathDijkstra(int v)
    {
        final int NO_PRE = -1;

        int vertexCount = getVertexCount();

        indexRangeCheck(v, 0, vertexCount);

        int[] pre = new int[vertexCount]; // pre[i]表示从顶点v到顶点i的最短路径中顶点i的前一个顶点。
        int[] weight = new int[vertexCount]; // weight[i]表示从顶点v到顶点i的最小权值。
        boolean[] visited = new boolean[vertexCount]; // 记录此顶点是否计算过了最小路径。
        for (int i = 0; i < vertexCount; i++)
        {
            pre[i] = NO_PRE;
            weight[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        // 初始化源点。
        int minWeight = 0;
        int vsrc = v;
        weight[vsrc] = 0;
        while (minWeight < Integer.MAX_VALUE)
        {
            // 更新源点到其他各个顶点的权值。
            visited[vsrc] = true;
            for (int vdest = getFirstNeighbor(vsrc); vdest > -1; vdest = getNextNeighbor(vsrc, vdest))
            {
                if (!visited[vdest] && weight[vsrc] + getEdge(vsrc, vdest) < weight[vdest])
                {
                    weight[vdest] = weight[vsrc] + getEdge(vsrc, vdest);
                    pre[vdest] = vsrc;
                }
            }

            // 查找下一个源点。
            minWeight = Integer.MAX_VALUE;
            for (int i = 0; i < vertexCount; i++)
            {
                if (!visited[i] && weight[i] < minWeight)
                {
                    minWeight = weight[i];
                    vsrc = i;
                }
            }
        }

        System.out.println("源顶点为：" + getVertex(v));
        System.out.println("到其他顶点的权值：");
        for (int i = 0; i < vertexCount; i++)
        {
            if (weight[i] == Integer.MAX_VALUE)
            {
                System.out.printf("%-5s", "--");
            }
            else
            {
                System.out.printf("%-5d", weight[i]);
            }
        }
        System.out.println();

        System.out.println("到其他顶点的最短路径：");
        for (int i = 0; i < vertexCount; i++)
        {
            if (pre[i] == NO_PRE)
            {
                continue;
            }

            int k = i;
            while (pre[k] != NO_PRE)
            {
                System.out.printf("%s<--", getVertex(k));
                k = pre[k];
            }
            System.out.printf("%s\n", getVertex(k));
        }
        System.out.println();
    }

    /**
     * 多源最短路径(弗洛伊德)。
     */
    public void shortestPathFloyd()
    {
        int vertexCount = getVertexCount();

        int[][] distanceMatrix = new int[vertexCount][vertexCount]; // 记录从顶点i到顶点j的最小花费。
        int[][] pathMatrix = new int[vertexCount][vertexCount]; // 记录顶点i到顶点j的最短路径中，除了顶点i，经过的第一个顶点。

        for (int i = 0; i < vertexCount; i++)
        {
            for (int j = 0; j < vertexCount; j++)
            {
                int weight = getEdge(i, j);
                distanceMatrix[i][j] = weight;
                pathMatrix[i][j] = (weight > 0 && weight < Integer.MAX_VALUE) ? j : -1;
            }
        }

        for (int k = 0; k < vertexCount; k++)
        {
            for (int i = 0; i < vertexCount; i++)
            {
                for (int j = 0; j < vertexCount; j++)
                {
                    // 从i经k到j的一条路径更短。
                    if (distanceMatrix[i][k] < Integer.MAX_VALUE && distanceMatrix[k][j] < Integer.MAX_VALUE
                            && distanceMatrix[i][k] + distanceMatrix[k][j] < distanceMatrix[i][j])
                    {
                        distanceMatrix[i][j] = distanceMatrix[i][k] + distanceMatrix[k][j];
                        pathMatrix[i][j] = pathMatrix[i][k];
                    }
                }
            }
        }

        System.out.println("各顶点最短路径如下：");
        for (int i = 0; i < vertexCount; i++)
        {
            for (int j = 0; j < vertexCount; j++)
            {
                System.out.printf("edge：%s-->%s, weight：%-10d, ", getVertex(i), getVertex(j), distanceMatrix[i][j]);
                if (distanceMatrix[i][j] > 0 && distanceMatrix[i][j] < Integer.MAX_VALUE)
                {
                    System.out.printf("path：%s", getVertex(i)); // 打印源点。
                    int k = pathMatrix[i][j]; // 经过顶点下标。
                    while (k != j) // 若经过顶点不等于终点。
                    {
                        System.out.printf("-->%s", getVertex(k)); // 则打印经过顶点。
                        k = pathMatrix[k][j]; // 获取下一个经过顶点。
                    }
                    System.out.printf("-->%s\n", getVertex(j));
                }
                else
                {
                    System.out.printf("path：无\n");
                }
            }
            System.out.println();
        }

        System.out.println("d矩阵：");
        for (int i = 0; i < vertexCount; i++)
        {
            for (int j = 0; j < vertexCount; j++)
            {
                System.out.printf("%11d", distanceMatrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("p矩阵：");
        for (int i = 0; i < vertexCount; i++)
        {
            for (int j = 0; j < vertexCount; j++)
            {
                System.out.printf("%11d", pathMatrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void addEdge(T data1, T data2, int weight)
    {
        int v1 = getVertexIndex(data1);
        int v2 = getVertexIndex(data2);

        addEdge(v1, v2, weight);
    }

    public int getFirstNeighbor(int v)
    {
        return getNextNeighbor(v, -1);
    }

    protected void indexRangeCheck(int index, int start, int end)
    {
        if (index < start || index >= end)
        {
            throw new IndexOutOfBoundsException("Index:" + index + ", Range:[" + start + "," + end + ").");
        }
    }

    protected void weightRangeCheck(int weight)
    {
        if (weight <= 0 || weight == Integer.MAX_VALUE)
        {
            throw new IllegalArgumentException("Weight:" + weight + ", Range:(" + 0 + "," + Integer.MAX_VALUE + ").");
        }
    }
}
