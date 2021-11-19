package com.jomkie.datastructure.graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Graph<V, E> {

    protected WeightManager<E> weightManager;

    public Graph() {}

    public Graph(WeightManager<E> weightManager) {
        this.weightManager = weightManager;
    }

    /** 任何测试 */
    public abstract void anyTest(V v);

    /** 打印 */
    public abstract void print();

    /** 边的数量 */
    public abstract int edgesSize();

    /** 顶点的数量 */
    public abstract int verticesSize();

    /** 添加顶点 */
    public abstract void addVertext(V v);

    /** 添加边 */
    public abstract void addEdge(V from, V to);

    /** 添加有权值边 */
    public abstract void addEdge(V from, V to, E weight);

    /** 删除顶点 */
    public abstract void removeVertext(V v);

    /** 删除边 */
    public abstract void removeEdge(V from, V to);

    /** 广度优化算法 breadthFirstSearch */
    public abstract void bfs(V from, VertexVisitor<V> visitor);

    /** 深度优化算法 depthFirstSearch */
    public abstract void dfs(V from, VertexVisitor<V> visitor);

    /** 非递归深度优化算法 depthFirstSearch */
    public abstract void dfs2(V from, VertexVisitor<V> visitor);

    public interface WeightManager<E> {
        int compare(E w1, E w2);
        E add(E w1, E w2);
    }

    /** 遍历，返回值判断是否结果遍历 */
    public interface VertexVisitor<V> {
        boolean visitor(V v);
    }

    /** AOV拓扑排序（判断是否是有向无环图，且源数据必须为有向图） */
    public abstract List<V> topologicalSort();

    /** 最小生成树 */
    public abstract Set<EdgeInfo<V, E>> mst();

    public abstract Map<V, E> shortestPath(V begin);

    /// AOE 网络

    public static class EdgeInfo<V, E> {
        private V from;
        private V to;
        private E weight;
        public EdgeInfo(V from, V to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public V getFrom() {
            return from;
        }

        public void setFrom(V from) {
            this.from = from;
        }

        public V getTo() {
            return to;
        }

        public void setTo(V to) {
            this.to = to;
        }

        public E getWeight() {
            return weight;
        }

        public void setWeight(E weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "EdgeInfo{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }

}
