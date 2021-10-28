package com.jomkie.datastructure.graph;

public interface Graph<V, E> {

    /** 打印 */
    void print();

    /** 边的数量 */
    int edgesSize();

    /** 顶点的数量 */
    int verticesSize();

    /** 添加顶点 */
    void addVertext(V v);

    /** 添加边 */
    void addEdge(V from, V to);

    /** 添加有权值边 */
    void addEdge(V from, V to, E weight);

    /** 删除顶点 */
    void removeVertext(V v);

    /** 删除边 */
    void removeEdge(V from, V to);

}
