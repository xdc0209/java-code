package com.xdc.basic.algorithm.basic.graph;

import java.util.Objects;

/**
 * 图(邻接十字链表)。
 */
public class AdjacencyOrthogonalListGraph<T> extends AbstractGraph<T>
{
    class Edge
    {
        int  startvex;

        int  endvex;

        int  weight;

        Edge nextIn;

        Edge nextOut;

        public Edge(int startvex, int endvex, int weight)
        {
            this.startvex = startvex;
            this.endvex = endvex;
            this.weight = weight;
        }
    }

    class Vertex
    {
        T    data;

        Edge head;

        public Vertex(T data, Edge head)
        {
            this.data = data;
            this.head = head;
        }
    }

    private boolean  directed;

    private Object[] vertexes;

    private int      vertexCount;

    private int      edgeCount;

    public AdjacencyOrthogonalListGraph(int maxVertexNum, boolean directed)
    {
        this.directed = directed;

        vertexes = new Object[maxVertexNum];
        vertexCount = 0;
    }

    @Override
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
    @SuppressWarnings("unchecked")
    public int getVertexIndex(T data)
    {
        for (int i = 0; i < vertexCount; i++)
        {
            if (Objects.equals(data, ((Vertex) vertexes[i]).data))
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
        return ((Vertex) vertexes[v]).data;
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

        // 创建头结点，不存储数据，仅仅为了简化链表的添加和删除操作。
        Edge head = new Edge(-1, -1, Integer.MAX_VALUE);
        Vertex vertex = new Vertex(data, head);

        vertexes[vertexCount] = vertex;
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
    @SuppressWarnings("unchecked")
    public int getEdge(int v1, int v2)
    {
        indexRangeCheck(v1, 0, vertexCount);
        indexRangeCheck(v2, 0, vertexCount);

        if (v1 == v2)
        {
            return 0;
        }

        Edge edge = ((Vertex) vertexes[v1]).head.nextOut;
        while (edge != null)
        {
            if (edge.endvex == v2)
            {
                return edge.weight;
            }
            else if (edge.endvex > v2)
            {
                break;
            }

            edge = edge.nextOut;
        }

        return Integer.MAX_VALUE;
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

        addEdgeInternal(v1, v2, weight);

        // 无向图需要添加对称的边。
        if (!directed)
        {
            addEdgeInternal(v2, v1, weight);
        }
    }

    @SuppressWarnings("unchecked")
    private void addEdgeInternal(int v1, int v2, int weight)
    {
        Edge newEdge = new Edge(v1, v2, weight);

        Edge edge1 = ((Vertex) vertexes[v1]).head;
        Edge edge2 = edge1.nextOut;
        while (edge2 != null)
        {
            if (edge2.endvex == v2)
            {
                return;
            }
            else if (edge2.endvex > v2)
            {
                break;
            }

            edge1 = edge2;
            edge2 = edge1.nextOut;
        }
        newEdge.nextOut = edge2;
        edge1.nextOut = newEdge;

        Edge edge3 = ((Vertex) vertexes[v2]).head;
        Edge edge4 = edge3.nextIn;
        while (edge4 != null)
        {
            if (edge4.startvex == v1)
            {
                return;
            }
            else if (edge4.startvex > v1)
            {
                break;
            }

            edge3 = edge4;
            edge4 = edge3.nextIn;
        }
        newEdge.nextIn = edge4;
        edge3.nextIn = newEdge;

        edgeCount++;
    }

    @Override
    public void delEdge(int v1, int v2)
    {
        throw new UnsupportedOperationException("本代码只是算法演示程序，不支持删除操作。");
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getNextNeighbor(int v1, int v2)
    {
        indexRangeCheck(v1, 0, vertexCount);
        indexRangeCheck(v2, -1, vertexCount);

        Edge edge = ((Vertex) vertexes[v1]).head.nextOut;
        while (edge != null)
        {
            if (edge.endvex > v2)
            {
                return edge.endvex;
            }

            edge = edge.nextOut;
        }

        return -1;
    }
}
