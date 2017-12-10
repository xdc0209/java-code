package com.xdc.basic.algorithm.basic.graph;

/**
 * 图接口： 一般情况下，图中的顶点都是以顺序表存储的，为了使接口更加统一，此接口中的方法都是以顶点的序号(从0开始)进行设计的。
 */
public interface Graph<T>
{
    // 是否是有向图。
    boolean isDirected();

    // 顶点个数：返回顶点个数。
    int getVertexCount();

    // 顶点序号：返回数据元素data所在的顶点的序号。
    // 如果不存在，则返回-1。
    int getVertexIndex(T data);

    // 顶点操作-查询：返回顶点v的数据元素。
    // 如果序号超出范围，则抛异常IndexOutOfBoundsException。序号范围为v(0~n-1)。
    T getVertex(int v);

    // 顶点操作-添加：添加数据元素data的顶点，返回顶点序号。
    // 如果已存在，则直接返回序号，不进行其他操作。
    int addVertex(T data);

    // 顶点操作-删除：删除顶点v及其关联的边。
    // 如果序号超出范围，则抛异常IndexOutOfBoundsException。序号范围为v(0~n-1)。
    void delVertex(int v);

    // 边个数：返回边个数。
    int getEdgeCount();

    // 边操作-查询：返回边<v1,v2>的权值。
    // 如果序号超出范围，则抛异常IndexOutOfBoundsException。序号范围为v1(0~n-1)，v2(0~n-1)。
    int getEdge(int v1, int v2);

    // 边操作-添加： 添加一条权值为weight的边<v1,v2>。
    // 如果已存在，则直接返回，不进行其他操作。
    // 如果序号超出范围，则抛异常IndexOutOfBoundsException。序号范围为v1(0~n-1)，v2(0~n-1)。
    void addEdge(int v1, int v2, int weight);

    // 边操作-添加： 添加一条权值为weight的边<v1,v2>。
    // 如果已存在，则直接返回，不进行其他操作。
    // 根据顶点查询序号，如果序号超出范围，则抛异常IndexOutOfBoundsException。序号范围为v1(0~n-1)，v2(0~n-1)。
    void addEdge(T data1, T data2, int weight);

    // 边操作-删除：删除边<v1,v2>。
    // 如果序号超出范围，则抛异常IndexOutOfBoundsException。序号范围为v1(0~n-1)，v2(0~n-1)。
    void delEdge(int v1, int v2);

    // 第一个邻接点：返回顶点v的第一个邻接顶点的序号。
    // 如果不存在下一个邻接顶点，则返回-1。
    // 如果序号超出范围，则抛异常IndexOutOfBoundsException。序号范围为v(0~n-1)。
    int getFirstNeighbor(int v);

    // 下个邻接点：返回顶点v1在顶点v2后的下一个邻接顶点的序号。
    // 如果v2等于-1，则返回第一个邻接顶点的序号。
    // 如果不存在下一个邻接顶点，则返回-1。
    // 如果序号超出范围，则抛异常IndexOutOfBoundsException。序号范围为v1(0~n-1)，v2(-1~n-1)。
    int getNextNeighbor(int v1, int v2);

    // 深度优先遍历(DeepFirstSearch)：从顶点v出发对图进行一次深度优先遍历。
    // 如果序号超出范围，则抛异常IndexOutOfBoundsException。序号范围为v(0~n-1)。
    void DFS(int v);

    // 广度优先遍历(BreadthFirstSearch)：从顶点v出发对图进行一次广度优先遍历。
    // 如果序号超出范围，则抛异常IndexOutOfBoundsException。序号范围为v(0~n-1)。
    void BFS(int v);

    // 最小生成树(普里姆)：边权值的和最小的生成树。
    // 如果序号超出范围，则抛异常IndexOutOfBoundsException。序号范围为v(0~n-1)。
    void minCostSpanTreePrim(int v);

    // 最小生成树(克鲁斯卡尔)：边权值的和最小的生成树。
    void minCostSpanTreeKruskal();

    // 拓扑排序：有向无环图排序。
    void toplogicalSort();

    // 关键路径：有向无环图中，源点(图中唯一的入度为0的顶点)到汇点(图中唯一的出度为0的顶点)的关键路径。图中可能存在多条关键路径。
    void criticalPath();

    // 单源最短路径(迪杰斯特拉)。
    void shortestPathDijkstra(int v);

    // 多源最短路径(弗洛伊德)。
    void shortestPathFloyd();
}
