package com.jomkie.datastructure.graph;

/**
 * 测试图
 */
public class TestGraph {
    public static void main(String[] args) {
        test00();
    }

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

    public static void test01() {

    }

    public static void test02() {

    }

}
