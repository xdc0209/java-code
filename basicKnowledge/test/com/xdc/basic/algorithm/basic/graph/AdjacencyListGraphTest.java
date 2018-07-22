package com.xdc.basic.algorithm.basic.graph;

import org.junit.Test;

public class AdjacencyListGraphTest
{
    private <T> Graph<T> newGraph(boolean directed)
    {
        return new AdjacencyListGraph<T>(100, directed);
    }

    @Test
    public void testSearch()
    {
        Graph<String> g = newGraph(false);
        g.addVertex("V1");
        g.addVertex("V2");
        g.addVertex("V3");
        g.addVertex("V4");
        g.addVertex("V5");
        g.addVertex("V6");
        g.addVertex("V7");
        g.addVertex("V8");

        g.addEdge("V1", "V2", 1);
        g.addEdge("V1", "V3", 1);
        g.addEdge("V2", "V4", 1);
        g.addEdge("V2", "V5", 1);
        g.addEdge("V3", "V6", 1);
        g.addEdge("V3", "V7", 1);
        g.addEdge("V4", "V8", 1);
        g.addEdge("V5", "V8", 1);
        g.addEdge("V6", "V7", 1);

        System.out.println("深度遍历：");
        g.DFS(0);
        System.out.println();

        System.out.println("广度遍历：");
        g.BFS(0);
        System.out.println();
    }

    @Test
    public void testminCostSpanTree()
    {
        Graph<String> g = newGraph(false);
        g.addVertex("V1");
        g.addVertex("V2");
        g.addVertex("V3");
        g.addVertex("V4");
        g.addVertex("V5");
        g.addVertex("V6");

        g.addEdge("V1", "V2", 6);
        g.addEdge("V1", "V3", 1);
        g.addEdge("V1", "V4", 5);
        g.addEdge("V2", "V3", 5);
        g.addEdge("V2", "V5", 3);
        g.addEdge("V3", "V4", 5);
        g.addEdge("V3", "V5", 6);
        g.addEdge("V3", "V6", 4);
        g.addEdge("V4", "V6", 2);
        g.addEdge("V5", "V6", 6);

        System.out.println("最小生成树(普里姆)");
        g.minCostSpanTreePrim(0);
        System.out.println();

        System.out.println("最小生成树(克鲁斯卡尔)：");
        g.minCostSpanTreeKruskal();
        System.out.println();
    }

    @Test
    public void testToplogicalSort()
    {
        Graph<String> g = newGraph(true);
        g.addVertex("V1");
        g.addVertex("V2");
        g.addVertex("V3");
        g.addVertex("V4");
        g.addVertex("V5");
        g.addVertex("V6");

        g.addEdge("V1", "V2", 1);
        g.addEdge("V1", "V3", 1);
        g.addEdge("V1", "V4", 1);
        g.addEdge("V3", "V2", 1);
        g.addEdge("V3", "V5", 1);
        g.addEdge("V4", "V5", 1);
        g.addEdge("V6", "V4", 1);
        g.addEdge("V6", "V5", 1);

        System.out.println("拓扑排序：");
        g.toplogicalSort();
        System.out.println();
    }

    @Test
    public void testCriticalPath()
    {
        Graph<String> g = newGraph(true);
        g.addVertex("V1");
        g.addVertex("V2");
        g.addVertex("V3");
        g.addVertex("V4");
        g.addVertex("V5");
        g.addVertex("V6");

        g.addEdge("V1", "V2", 3);
        g.addEdge("V1", "V3", 2);
        g.addEdge("V2", "V4", 2);
        g.addEdge("V2", "V5", 3);
        g.addEdge("V3", "V4", 4);
        g.addEdge("V3", "V6", 3);
        g.addEdge("V4", "V6", 2);
        g.addEdge("V5", "V6", 1);

        System.out.println("关键路径：");
        g.criticalPath();
        System.out.println();
    }

    @Test
    public void testShortestPathDijkstra()
    {
        Graph<String> g = newGraph(true);
        g.addVertex("V0");
        g.addVertex("V1");
        g.addVertex("V2");
        g.addVertex("V3");
        g.addVertex("V4");
        g.addVertex("V5");

        g.addEdge("V0", "V2", 10);
        g.addEdge("V0", "V4", 30);
        g.addEdge("V0", "V5", 100);
        g.addEdge("V1", "V2", 5);
        g.addEdge("V2", "V3", 50);
        g.addEdge("V3", "V5", 10);
        g.addEdge("V4", "V3", 20);
        g.addEdge("V4", "V5", 60);

        System.out.println("单源最短路径(迪杰斯特拉)：");
        g.shortestPathDijkstra(0);
        System.out.println();
    }

    @Test
    public void testShortestPathFloyd()
    {
        Graph<String> g = newGraph(true);
        g.addVertex("V0");
        g.addVertex("V1");
        g.addVertex("V2");

        g.addEdge("V0", "V1", 4);
        g.addEdge("V0", "V2", 11);
        g.addEdge("V1", "V0", 6);
        g.addEdge("V1", "V2", 2);
        g.addEdge("V2", "V0", 3);

        System.out.println("多源最短路径(弗洛伊德)：");
        g.shortestPathFloyd();
        System.out.println();
    }
}
