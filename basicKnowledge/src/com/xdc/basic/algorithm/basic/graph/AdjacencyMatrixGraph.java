package com.xdc.basic.algorithm.basic.graph;

import java.util.Objects;

/**
 * 图(邻接矩阵)。
 */
public class AdjacencyMatrixGraph<T> extends AbstractGraph<T>
{
    private boolean  directed;

    private Object[] vertexes;

    private int      vertexCount;

    private int[][]  edges;

    private int      edgeCount;

    public AdjacencyMatrixGraph(int maxVertexCount, boolean directed)
    {
        this.directed = directed;

        vertexes = new Object[maxVertexCount];
        vertexCount = 0;

        edges = new int[maxVertexCount][maxVertexCount];
        for (int i = 0; i < maxVertexCount; i++)
        {
            for (int j = 0; j < maxVertexCount; j++)
            {
                edges[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
            }
        }
    }

    public boolean isDirected()
    {
        return directed;
    }

    @Override
    public int getVertexCount()
    {
        return vertexCount;
    }

    @Override
    public int getVertexIndex(T data)
    {
        for (int i = 0; i < vertexCount; i++)
        {
            if (Objects.equals(data, vertexes[i]))
            {
                return i;
            }
        }

        return -1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getVertex(int v)
    {
        indexRangeCheck(v, 0, vertexCount);
        return (T) vertexes[v];
    }

    @Override
    public int addVertex(T data)
    {
        if (vertexCount == vertexes.length)
        {
            throw new IllegalStateException("Graph is full.");
        }

        for (int i = 0; i < vertexCount; i++)
        {
            if (Objects.equals(data, vertexes[i]))
            {
                return i;
            }
        }

        vertexes[vertexCount] = data;
        vertexCount++;

        return vertexCount;
    }

    @Override
    public void delVertex(int v)
    {
        throw new UnsupportedOperationException("本代码只是算法演示程序，不支持删除操作。");
    }

    @Override
    public int getEdgeCount()
    {
        return edgeCount;
    }

    @Override
    public int getEdge(int v1, int v2)
    {
        indexRangeCheck(v1, 0, vertexCount);
        indexRangeCheck(v2, 0, vertexCount);
        return edges[v1][v2];
    }

    @Override
    public void addEdge(int v1, int v2, int weight)
    {
        indexRangeCheck(v1, 0, vertexCount);
        indexRangeCheck(v2, 0, vertexCount);
        weightRangeCheck(weight);

        if (v1 == v2)
        {
            throw new IllegalArgumentException("Index of vertex repeat.");
        }

        edges[v1][v2] = weight;
        edgeCount++;

        // 无向图需要添加对称的边。
        if (!directed)
        {
            edges[v2][v1] = weight;
            edgeCount++;
        }
    }

    @Override
    public void delEdge(int v1, int v2)
    {
        throw new UnsupportedOperationException("本代码只是算法演示程序，不支持删除操作。");
    }

    @Override
    public int getNextNeighbor(int v1, int v2)
    {
        indexRangeCheck(v1, 0, vertexCount);
        indexRangeCheck(v2, -1, vertexCount);

        for (int i = v2 + 1; i < vertexCount; i++)
        {
            if (edges[v1][i] > 0 && edges[v1][i] < Integer.MAX_VALUE)
            {
                return i;
            }
        }

        return -1;
    }
}
