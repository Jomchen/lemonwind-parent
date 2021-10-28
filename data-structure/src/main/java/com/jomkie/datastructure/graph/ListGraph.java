package com.jomkie.datastructure.graph;

import java.util.*;
import java.util.stream.Collectors;

public class ListGraph<V, E> implements Graph<V, E> {

    private Map<V, Vertex<V, E>> vertices = new HashMap<>();
    private Set<Edge<V, E>> edges = new HashSet<>();

    public void print() {
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            System.out.println(v);
            System.out.println("out------------");
            System.out.println(vertex.outEdges);
            System.out.println("in--------------");
            System.out.println(vertex.inEdges);
        });
        System.out.println("*************************************");
        edges.forEach(e -> {
            System.out.println(e);
        });
    }

    @Override
    public int edgesSize() {
        return edges.size();
    }

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    @Override
    public void addVertext(V v) {
        if (vertices.containsKey(v)) return;
        vertices.put(v, new Vertex<>(v));
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from, to, null);
    }

    @Override
    public void addEdge(V from, V to, E weight) {
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) {
            fromVertex = new Vertex(from);
            vertices.put(from, fromVertex);
        }

        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) {
            toVertex = new Vertex<>(to);
            vertices.put(to, toVertex);
        }

        // 此处逻辑相当于是仅用一个顶点判断有没有关于它的此边
        // 因为边的增加和删除对于两个顶点一定是都要维护的，所以此处仅判断一个顶点即可
        Edge<V, E> edge = new Edge<>(fromVertex, toVertex, weight);
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edges.add(edge);
    }

    @Override
    public void removeVertext(V v) {
        Vertex<V, E> vertex = vertices.remove(v);
        if (null == vertex) return;

        // 删除关于此顶点的出度
        Iterator<Edge<V, E>> iteratorOut = vertex.outEdges.iterator();
        while (iteratorOut.hasNext()) {
            Edge<V, E> edge = iteratorOut.next();
            edge.to.inEdges.remove(edge);
            iteratorOut.remove();
            edges.remove(edge);
        }

        // 删除关于此顶点的入度
        Iterator<Edge<V, E>> iteratorIn = vertex.inEdges.iterator();
        while (iteratorIn.hasNext()) {
            Edge<V, E> edge = iteratorIn.next();
            edge.from.outEdges.remove(edge);
            iteratorIn.remove();
            edges.remove(edge);
        }
    }

    @Override
    public void removeEdge(V from, V to) {
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) return;
        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) return;

        Edge<V, E> edge = new Edge<>(fromVertex, toVertex, null);
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
    }

    private static class Vertex<V, E> {
        /** 顶点值 */
        V value;
        /** 入度 */
        Set<Edge<V, E>> inEdges = new HashSet<>();
        /** 出度 */
        Set<Edge<V, E>> outEdges = new HashSet<>();

        public Vertex(V value) {
            super();
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            return Objects.equals(value, ((Vertex<V, E>) obj).value);
        }
        @Override
        public int hashCode() {
            return value == null ? 0 : value.hashCode();
        }

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }
    }

    private static class Edge<V, E> {
        Vertex<V, E> from;
        Vertex<V, E> to;
        E weight;

        public Edge(Vertex<V, E> from, Vertex<V, E> to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object obj) {
            Edge<V, E> edge = ((Edge<V, E>) obj);
            return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
        }
        @Override
        public int hashCode() {
            return from.hashCode() * 31 + to.hashCode();
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }


}
