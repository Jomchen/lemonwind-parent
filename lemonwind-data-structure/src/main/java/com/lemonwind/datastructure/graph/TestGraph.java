package com.lemonwind.datastructure.graph;

import java.util.Map;

/**
 * 测试图
 */
public class TestGraph {

    static Graph.WeightManager<Double> weightManager = new Graph.WeightManager<Double>() {
        @Override
        public int compare(Double w1, Double w2) {
            return w1.compareTo(w2);
        }
        @Override
        public Double add(Double w1, Double w2) {
            return w1 + w2;
        }

        @Override
        public Double zero() {
            return 0.0;
        }
    };

    public static void main(String[] args) {
        test12();
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

    /** 最小生成树 prim 算法 */
    public static void test08() {
        // 9, 2, 4, 4, 3, 5, 1
        Graph<Object, Double> graph = unDirectedGraph(Data.MST_01);
        graph.mst().forEach(System.out::println);
    }

    /** 最小生成树 kruskal 算法 */
    public static void test09() {
        // 9, 2, 4, 4, 3, 5, 1
        Graph<Object, Double> graph = unDirectedGraph(Data.MST_01);
        graph.mst().forEach(System.out::println);
    }

    /** Dijkstra 最短路径 */
    public static void test10() {
        Graph<Object, Double> graph =directedGraph(Data.SP);
        Graph<Object, Double> graph2 = unDirectedGraph(Data.SP);

        graph.shortestPath("A").forEach((Object o, Graph.PathInfo<Object, Double> path) -> System.out.println(o + "---" + path));
        System.out.println("------------------------------------");
        graph2.shortestPath("A").forEach((Object o, Graph.PathInfo<Object, Double> path) -> System.out.println(o + "---" + path));
    }

    /** BellmanFord 最短路径 */
    public static void test11() {
         Graph<Object, Double> graph =directedGraph(Data.SP);
         Graph<Object, Double> graph2 = unDirectedGraph(Data.SP);

        graph.shortestPath("A").forEach((Object o, Graph.PathInfo<Object, Double> path) -> System.out.println(o + "---" + path));
        System.out.println("------------------------------------");
        graph2.shortestPath("A").forEach((Object o, Graph.PathInfo<Object, Double> path) -> System.out.println(o + "---" + path));
        System.out.println("------------------------------------");

        // 有负权边，不支持 Dijkstra
        Graph<Object, Double> graph3 = directedGraph(Data.NEGATIVE_WEIGHT1);
        graph3.shortestPath("A").forEach((Object o, Graph.PathInfo<Object, Double> path) -> System.out.println(o + "---" + path));

        // 有负权环，不支持 Dijkstra
        /*Graph<Object, Double> graph4 = directedGraph(Data.NEGATIVE_WEIGHT2);
        graph4.shortestPath(0);*/
    }

    /** */
    public static void test12() {
        Graph<Object, Double> graph = directedGraph(Data.SP);
//        Graph<Object, Double> graph = directedGraph(Data.NEGATIVE_WEIGHT1);
        Map<Object, Map<Object, Graph.PathInfo<Object, Double>>> sp = graph.shortestPath();
        sp.forEach((Object from, Map<Object, Graph.PathInfo<Object, Double>> paths) -> {
            System.out.println(from + "作为起点：--------------------------------");
            paths.forEach((Object to, Graph.PathInfo<Object, Double> path) -> {
                System.out.println(to + "--" + path);
            });
        });
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
