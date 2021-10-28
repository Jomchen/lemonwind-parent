package com.jomkie.datastructure.graph;

/**
 * 测试图
 */
public class TestGraph {
    public static void main(String[] args) {
        test04();
    }

    /** 测试打印 */
    public static void test00() {
        Graph<String, Integer> graph = new ListGraph<>();
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
        Graph<String, Integer> graph = new ListGraph<>();
        graph.addEdge("v1", "v0", 9);
        graph.addEdge("v1", "v2", 3);
        graph.addEdge("v2", "v0", 2);
        graph.addEdge("v2", "v3", 5);
        graph.addEdge("v3", "v4", 1);
        graph.addEdge("v0", "v4", 6);

        graph.removeEdge("v0", "v4");
        graph.print();
    }

    /** 测试删除顶点 */
    public static void test02() {
        Graph<String, Integer> graph = new ListGraph<>();
        graph.addEdge("v1", "v0", 9);
        graph.addEdge("v1", "v2", 3);
        graph.addEdge("v2", "v0", 2);
        graph.addEdge("v2", "v3", 5);
        graph.addEdge("v3", "v4", 1);
        graph.addEdge("v0", "v4", 6);

        graph.removeEdge("v0", "v4");
        graph.print();
    }

    /** 测试无向图 */
    public static void test03() {
        Graph<String, Integer> graph = new ListGraph<>();
        graph.addEdge("v0", "v1");
        graph.addEdge("v1", "v0");

        graph.addEdge("v0", "v2");
        graph.addEdge("v2", "v0");

        graph.addEdge("v0", "v3");
        graph.addEdge("v3", "v0");

        graph.addEdge("v1", "v2");
        graph.addEdge("v2", "v1");

        graph.addEdge("v2", "v3");
        graph.addEdge("v3", "v2");

        graph.print();
    }

    /** 测试图的遍历 */
    public static void test04() {
        /*Graph<String, Integer> graph = new ListGraph<>();
        graph.addEdge("v1", "v0", 9);
        graph.addEdge("v1", "v2", 3);
        graph.addEdge("v2", "v0", 2);
        graph.addEdge("v2", "v3", 5);
        graph.addEdge("v3", "v4", 1);
        graph.addEdge("v0", "v4", 6);
        graph.bfs("v1");*/
        Graph<Object, Double> graph2 = directedGraph(Data.BFS_02);
        graph2.bfs(5);
    }

    /** 添加有向图 */
    private static Graph<Object, Double> directedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>();
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
        Graph<Object, Double> graph = new ListGraph<>();
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
