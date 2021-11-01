package com.jomkie.datastructure.graph;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public interface Graph<V, E> {

    /** 任何测试 */
    void anyTest(V v);

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

    /** 广度优化算法 breadthFirstSearch */
    void bfs(V from, VertexVisitor<V> visitor);

    /** 深度优化算法 depthFirstSearch */
    void dfs(V from, VertexVisitor<V> visitor);

    /** 非递归深度优化算法 depthFirstSearch */
    void dfs2(V from, VertexVisitor<V> visitor);

    /** 遍历，返回值判断是否结果遍历 */
    interface VertexVisitor<V> {
        boolean visitor(V v);
    }

    /** AOV拓扑排序（判断是否是有向无环图，且源数据必须为有向图） */
    List<V> topologicalSort();

    /** 最小生成树 */
    Set<EdgeInfo<V, E>> mst();

    /// AOE 网络

    class EdgeInfo<V, E> {
        V from;
        V to;
        E weight;
        public EdgeInfo(V from, V to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

}
