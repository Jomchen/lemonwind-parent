package com.jomkie.datastructure.graph;

/**
 * 测试图
 */
public class TestGraph {

    static Graph.WeightManager<Double> weightManager = new Graph.WeightManager<Double>() {
        @Override
        public int compare(Double w1, Double w2) {
//            return w1.compareTo(w2);
            return w2.compareTo(w1);
        }
        @Override
        public Double add(Double w1, Double w2) {
            return w1 + w2;
        }
    };

    public static void main(String[] args) {
        test08();
    }

    /** 测试打印 */
    public static void test00() {
        Graph<String, Integer> graph = new ListGraph(weightManager);
        graph.addEdge("v1", "v0", 9);
        graph.addEdge("v1", "v2", 3);
        graph.addEdge("v2", "v0", 2);
        graph.addEdge("v2", "v3", 5);
        graph.addEdge("v3", "v4", 1);
        graph.addEdge("v0", "v4", 6);
        graph.print();
    }

    /** 测试删除边 */
    public static void test01() {
//        Graph<String, Integer> graph = new ListGraph(weightManager);
//        graph.addEdge("v1", "v0", 9);
//        graph.addEdge("v1", "v2", 3);
//        graph.addEdge("v2", "v0", 2);
//        graph.addEdge("v2", "v3", 5);
//        graph.addEdge("v3", "v4", 1);
//        graph.addEdge("v0", "v4", 6);
//
//        graph.removeEdge("v0", "v4");
//        graph.print();
    }

    /** 测试删除顶点 */
    public static void test02() {
//        Graph<String, Integer> graph = new ListGraph(weightManager);
//        graph.addEdge("v1", "v0", 9);
//        graph.addEdge("v1", "v2", 3);
//        graph.addEdge("v2", "v0", 2);
//        graph.addEdge("v2", "v3", 5);
//        graph.addEdge("v3", "v4", 1);
//        graph.addEdge("v0", "v4", 6);
//
//        graph.removeEdge("v0", "v4");
//        graph.print();
    }

    /** 测试无向图 */
    public static void test03() {
//        Graph<String, Integer> graph = new ListGraph(weightManager);
//        graph.addEdge("v0", "v1");
//        graph.addEdge("v1", "v0");
//
//        graph.addEdge("v0", "v2");
//        graph.addEdge("v2", "v0");
//
//        graph.addEdge("v0", "v3");
//        graph.addEdge("v3", "v0");
//
//        graph.addEdge("v1", "v2");
//        graph.addEdge("v2", "v1");
//
//        graph.addEdge("v2", "v3");
//        graph.addEdge("v3", "v2");
//
//        graph.print();
    }

    /** 测试图的广度优先遍历 */
    public static void test04() {
        Graph<Object, Double> graph2 = directedGraph(Data.BFS_02);
        graph2.bfs(5, null);
    }

    /** 测试图的 递归版 深度优先遍历 */
    public static void test05() {
        Graph<Object, Double> graph2 = unDirectedGraph(Data.DFS_01);
        graph2.dfs(1, null);
    }

    /** 测试图的 非递归版 深度优先遍历 */
    public static void test06() {
        Graph<Object, Double> graph2 = directedGraph(Data.DFS_02);
        graph2.dfs2("a", null);
    }

    /** 拓扑排序，判断是否是有向无环图 */
    public static void test07() {
        Graph<Object, Double> graph2 = directedGraph(Data.TOPO);
        graph2.topologicalSort().forEach(System.out::println);
    }

    /** prim 算法 */
    public static void test08() {
        Graph<Object, Double> graph = unDirectedGraph(Data.MST_01);
        graph.mst().forEach(System.out::println);
    }








    /** 添加有向图 */
    private static Graph<Object, Double> directedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertext(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
            }
        }
        return graph;
    }

    /** 添加无向图 */
    private static Graph<Object, Double> unDirectedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertext(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
                graph.addEdge(edge[1], edge[0]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
                graph.addEdge(edge[1], edge[0], weight);
            }
        }
        return graph;
    }
}
