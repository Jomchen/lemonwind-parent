package com.jomkie.datastructure.graph;


import java.util.*;
import java.util.stream.Collectors;

public class ListGraph<V, E> implements Graph<V, E> {

    private Map<V, Vertex<V, E>> vertices = new HashMap<>();
    private Set<Edge<V, E>> edges = new HashSet<>();

    @Override
    public void anyTest(V v) {

    }

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

    @Override
    public void bfs(V from, VertexVisitor<V> visitor) {
        if (null == visitor) {
            visitor = e -> {System.out.println(e);; return false; };
        }
        Vertex<V, E> beginVertex = vertices.get(from);
        if (null == beginVertex) return;

        Set<Vertex<V, E>> visitedSet = new HashSet<>();
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        // 添加时刻1
        queue.offer(beginVertex);
        visitedSet.add(beginVertex);

        while ( !queue.isEmpty()) {
            Vertex<V, E> vertex = queue.poll();
            visitor.visitor(vertex.value);

            for (Edge<V, E> edge : vertex.outEdges) {
                if (visitedSet.contains(edge.to)) continue;
                queue.offer(edge.to);
                // 添加时刻2
                visitedSet.add(edge.to);
            }
        }

        // 添加时刻解析：如果有 A 直连 B 和 C，B 和 C 分别直连 D，那么需要入队时就算遍历过才能防重复
    }

    @Override
    public void dfs(V from, VertexVisitor<V> visitor) {
        if (null == visitor) {
            visitor = e -> {System.out.println(e);; return false; };
        }
        Vertex<V, E> beginVertex = vertices.get(from);
        if (beginVertex == null) return;
        dfsTool(beginVertex, new HashSet<>(), visitor);
    }
    private void dfsTool(
            Vertex<V, E> beginVertex,
            Set<Vertex<V, E>> visitedSet,
            VertexVisitor<V> visitor) {
        visitor.visitor(beginVertex.value);
        visitedSet.add(beginVertex);
        if (beginVertex.outEdges.isEmpty()) return;
        for (Edge<V, E> edge : beginVertex.outEdges) {
            if (visitedSet.contains(edge.to)) continue;
            dfsTool(edge.to, visitedSet, visitor);
        }
    }

    @Override
    public void dfs2(V from, VertexVisitor<V> visitor) {
        if (null == visitor) {
            visitor = e -> {System.out.println(e);; return false; };
        }
        Vertex<V, E> beginVertex = vertices.get(from);
        if (null == beginVertex) return;

        Set<Vertex<V, E>> visitedSet = new HashSet<>();
        Stack<Vertex<V, E>> stack = new Stack<>();

        stack.push(beginVertex);
        visitedSet.add(beginVertex);
        if(visitor.visitor(beginVertex.value)) return;

        while ( !stack.isEmpty()) {
            Vertex<V, E> vertex = stack.pop();
            for (Edge<V, E> edge : vertex.outEdges) {
                if (visitedSet.contains(edge.to)) continue;

                stack.push(edge.from);
                stack.push(edge.to);
                visitedSet.add(edge.to);
                if (visitor.visitor(edge.to.value)) return;

                break;
            }
        }
    }


    @Override
    public List<V> topologicalSort() {
        Map<Vertex<V, E>, Integer> vertexMap = new HashMap<>();
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        List<Vertex<V, E>> resultList = new LinkedList<>();
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            if (vertex.inEdges.isEmpty()) {
                queue.add(vertex);
            } else {
                vertexMap.put(vertex, vertex.inEdges.size());
            }
        });

        while ( !queue.isEmpty()) {
            Vertex<V, E> vertex = queue.poll();
            resultList.add(vertex);
            for (Edge<V, E> edge : vertex.outEdges) {
                Integer inDegree = vertexMap.get(edge.to) - 1;
                if (inDegree.equals(0)) {
                    queue.offer(edge.to);
                } else {
                    vertexMap.put(edge.to, inDegree);
                }
            }
        }

        return resultList.stream().map(e -> e.value).collect(Collectors.toList());

        /*Set<Vertex<V, E>> visitedSet = new HashSet<>();
        Queue<Vertex<V, E>> queue = new LinkedList<>();

        while (true) {
            List<Vertex<V, E>> vertexWithoutInEdge = vertices.values().stream().filter(vertexObj -> {
                if (visitedSet.contains(vertexObj)) return false;
                if (vertexObj.inEdges.isEmpty()) return true;
                return vertexObj.inEdges.stream().allMatch(edge -> visitedSet.contains(edge.from));
            }).collect(Collectors.toList());

            if (vertexWithoutInEdge.isEmpty()) {
                break;
            } else {
                vertexWithoutInEdge.forEach(queue::offer);
                visitedSet.addAll(vertexWithoutInEdge);
            }
        }

        if (queue.size() != vertices.size()) {
            return new LinkedList<>();
        }

        return queue.stream().map(vertex -> vertex.value).collect(Collectors.toList());*/
    }

    @Override
    public Set<EdgeInfo<V, E>> mst() {
        return prim();
    }

    /** prim算法，必须是有权无向图 */
    public Set<EdgeInfo<V, E>> prim() {
        Vertex<V, E> beginVertex = vertices.isEmpty() ? null : vertices.values().iterator().next();
        if (null == beginVertex) return null;

        Set<Vertex<V, E>> visitedVertices = new HashSet<>();
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        visitedVertices.add(beginVertex);

        while (visitedVertices.size() != vertices.size()) {
            Edge<V, E> minimumEdge = null;
            for (Vertex<V, E> vertex : visitedVertices) {
                for (Edge<V, E> edge : vertex.outEdges) {
                    boolean existsFrom = visitedVertices.contains(edge.from);
                    boolean existsTo = visitedVertices.contains(edge.to);
                    if (existsFrom && existsTo) { continue; }

                    if (minimumEdge == null) {
                        minimumEdge = edge;
                    } else {
                        // 如果权值比较小则替换掉 minimumEdge
                        // 添加 edge.to 到 visitedVertices
                        // 转换 edge 为 edgeInfo 并加入到 edgeInfoSet
                        minimumEdge = null;
                    }
                }
            }
        }


        return edgeInfos;
    }

    /** kruskal 算法 */
    public Set<EdgeInfo<V, E>> kruskal() {
        return null;
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
