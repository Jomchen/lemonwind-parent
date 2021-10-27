package com.jomkie.datastructure.graph;

import java.util.HashSet;
import java.util.Set;

public class ListGraph<V, E> implements Graph<V, E> {

    @Override
    public int edgesSize() {
        return 0;
    }

    @Override
    public int verticesSize() {
        return 0;
    }

    @Override
    public void addVertext(Object o) {

    }

    @Override
    public void addEdge(Object from, Object to) {

    }

    @Override
    public void addEdge(Object from, Object to, Object weight) {

    }

    @Override
    public void removeVertext(Object o) {

    }

    @Override
    public void removeEdge(Object from, Object to) {

    }

    private static class Vertex<V, E> {
        /** 顶点 */
        V value;
        /** 入度 */
        Set<Edge<V, E>> inEdges = new HashSet<>();
        /** 出度 */
        Set<Edge<V, E>> outEdges = new HashSet<>();
    }

    private static class Edge<V, E> {
        Vertex<V, E> from;
        Vertex<V, E> to;
        E weight;
    }

}
