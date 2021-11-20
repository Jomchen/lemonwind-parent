package com.jomkie.datastructure.graph;


import com.jomkie.datastructure.tree.heap.BinaryHeap;
import com.jomkie.datastructure.unionfind.GenericUnionFind;

import java.util.*;
import java.util.stream.Collectors;

public class ListGraph<V, E> extends Graph<V, E> {

    private Map<V, Vertex<V, E>> vertices = new HashMap<>();
    private Set<Edge<V, E>> edges = new HashSet<>();
    private Comparator<Edge<V, E>> edgeComparator = (Edge<V, E> e1, Edge<V, E> e2) -> weightManager.compare(e1.weight, e2.weight);

    public ListGraph() {}

    public ListGraph(WeightManager<E> weightManager) {
        super(weightManager);
    }

    @Override
    public void anyTest(V v) {}

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
//        return prim();
        return kruskal();
    }

    /*@Override
    public Map<V, E> shortestPath(V begin) {
        return dijkstra(begin);
    }*/

    @Override
    public Map<V, PathInfo<V, E>> shortestPath(V begin) {
        //return dijkstra(begin);
        return bellmanFord(begin);
    }


    /** prim算法，必须是有权无向图 */
    public Set<EdgeInfo<V, E>> prim() {
        Vertex<V, E> vertex = vertices.isEmpty() ? null : vertices.values().iterator().next();
        if (null == vertex) return null;

        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        Set<Vertex<V, E>> visitedVertices = new HashSet<>();
        // 因为这个堆默认是大顶堆，所以需要转换为小顶堆
        BinaryHeap<Edge<V, E>> heap = new BinaryHeap<>(vertex.outEdges, edgeComparator.reversed());

        visitedVertices.add(vertex);
        int edgeSize = vertices.size() - 1;
        while ( !heap.isEmpty() && edgeInfos.size() < edgeSize) {
            Edge<V, E> edge = heap.remove();
            if (visitedVertices.contains(edge.to)) { continue; }

            edgeInfos.add(edge.info());
            visitedVertices.add(edge.to);
            for (Edge<V, E> e : edge.to.outEdges) {
                heap.add(e);
            }
        }
        return edgeInfos;
    }

    /** kruskal 算法 */
    public Set<EdgeInfo<V, E>> kruskal() {
        Vertex<V, E> vertex = vertices.isEmpty() ? null : vertices.values().iterator().next();
        if (null == vertex) return null;

        // 边按照权值从小到大排序（如果这里采用小顶堆，并在循环中采用 remove 的方式取当前堆最小值性能可能会更好）
        List<Edge<V, E>> sortedEdgeList = edges.stream().sorted(edgeComparator).collect(Collectors.toList());
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        GenericUnionFind<Vertex<V, E>> unionFind = new GenericUnionFind<>();
        vertices.forEach((V v, Vertex<V, E> theVertex) ->
            unionFind.makeSet(theVertex)
        );

        int endEdgeSize = vertices.size() - 1;
        if (endEdgeSize == -1) { return edgeInfos; }
        for (Edge<V, E> edge : sortedEdgeList) {
            if (edgeInfos.size() == endEdgeSize) { break; }

            if (unionFind.isSame(edge.from, edge.to)) { continue; }
            edgeInfos.add(edge.info());
            unionFind.union(edge.from, edge.to);
        }

        return edgeInfos;
    }


    /** 最短路径 Dijkstra 算法，不能有负权边 */
    /*private Map<V, E> dijkstra(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (null == beginVertex) { return null; }

        Map<V, E> selectedPaths = new HashMap<>();
        Map<Vertex<V, E>, E> paths= new HashMap<>();
        for (Edge<V, E> edge : beginVertex.outEdges) {
            paths.put(edge.to, edge.weight);
        }

        while ( !paths.isEmpty()) {
            Map.Entry<Vertex<V, E>, E> minEntry = getMinPath(paths);
            // minVertex 离开桌面
            Vertex<V, E> minVertex = minEntry.getKey();
            selectedPaths.put(minVertex.value, minEntry.getValue());
            paths.remove(minVertex);
            // 对它的 minVertex 的 outEdges 进行松弛操作
            for (Edge<V, E> edge : minVertex.outEdges) {
                // 如果顶点已经起来了，则不能作松弛操作
                if (selectedPaths.containsKey(edge.to.value) || edge.to.equals(beginVertex)) { continue; }

                // 以前的最短路径：beginVertex 到 edge.to 的最短路径
                // 新的可选择的最短路径：beginVertex 到 edge.from 的最短路径 + edge.weght
                // 如果新的最短路径比旧的短则更新最短路径
                E newWeight = weightManager.add(minEntry.getValue(), edge.weight);
                E oldWeight = paths.get(edge.to);
                if (oldWeight == null || weightManager.compare(newWeight, oldWeight) < 0) {
                    paths.put(edge.to, newWeight);
                }
            }
        }

        return selectedPaths;
    }*/


    private Map<V, PathInfo<V, E>> bellmanFord(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (null == beginVertex) { return null; }

        Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
        PathInfo<V, E> beginPath = new PathInfo<>();
        beginPath.weight = weightManager.zero();
        selectedPaths.put(begin, beginPath);

        int count = vertices.size() - 1;
        for (int i = 0; i < count; i++) {
            for (Edge<V, E> edge : edges) {
                PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
                if (null == fromPath) { continue; }
                relaxForBellmanFord(edge, fromPath, selectedPaths);
            }
        }

        selectedPaths.remove(begin);
        return selectedPaths;
    }

    private Map<V, PathInfo<V, E>> dijkstra(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (null == beginVertex) { return null; }

        Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
        Map<Vertex<V, E>, PathInfo<V, E>> paths= new HashMap<>();
        for (Edge<V, E> edge : beginVertex.outEdges) {
            PathInfo<V, E> pathInfo = new PathInfo<>();
            pathInfo.weight = edge.weight;
            pathInfo.edgeInfos.add(edge.info());
            paths.put(edge.to, pathInfo);
        }

        while ( !paths.isEmpty()) {
            Map.Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = getMinPath(paths);
            // minVertex 离开桌面
            Vertex<V, E> minVertex = minEntry.getKey();
            PathInfo<V, E> minPath = minEntry.getValue();
            selectedPaths.put(minVertex.value, minEntry.getValue());
            paths.remove(minVertex);
            // 对它的 minVertex 的 outEdges 进行松弛操作
            for (Edge<V, E> edge : minVertex.outEdges) {
                // 如果顶点已经起来了，则不能作松弛操作
                //if (selectedPaths.containsKey(edge.to.value) || edge.to.equals(beginVertex)) { continue; }
                if (selectedPaths.containsKey(edge.to.value)) { continue; }
                relaxForDijkstra(edge, minPath, paths);
            }
        }

        selectedPaths.remove(begin);
        return selectedPaths;
    }

    /**
     * 松弛
     * @param edge 需要进行松弛的边
     * @param fromPath edge的from的最短路径信息
     * @param paths 其它点（没有离开桌面的点）的最短路径信息
     */
    private void relaxForDijkstra(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<Vertex<V, E>, PathInfo<V, E>> paths) {
        // 以前的最短路径：beginVertex 到 edge.to 的最短路径
        // 新的可选择的最短路径：beginVertex 到 edge.from 的最短路径 + edge.weght
        // 如果新的最短路径比旧的短则更新最短路径
        E newWeight = weightManager.add(fromPath.weight, edge.weight);
        PathInfo<V, E> oldPath = paths.get(edge.to);
        if (oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) { return; }

        if (oldPath == null) {
            oldPath = new PathInfo<>();
            paths.put(edge.to, oldPath);
        } else {
            oldPath.edgeInfos.clear();
        }

        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());
    }

    private void relaxForBellmanFord(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<V, PathInfo<V, E>> paths) {
        E newWeight = weightManager.add(fromPath.weight, edge.weight);
        PathInfo<V, E> oldPath = paths.get(edge.to.value);
        if (oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) { return; }

        if (oldPath == null) {
            oldPath = new PathInfo<>();
            paths.put(edge.to.value, oldPath);
        } else {
            oldPath.edgeInfos.clear();
        }

        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());
    }

    /*private Map.Entry<Vertex<V, E>, E> getMinPath(Map<Vertex<V, E>, E> paths) {
        Iterator<Map.Entry<Vertex<V, E>, E>> iterator = paths.entrySet().iterator();
        Map.Entry<Vertex<V, E>, E> minEntry = iterator.next();

        while (iterator.hasNext()) {
            Map.Entry<Vertex<V, E>, E> entry = iterator.next();
            if (weightManager.compare(entry.getValue(), minEntry.getValue()) < 0) {
                minEntry = entry;
            }
        }
        return minEntry;
    }*/

    private Map.Entry<Vertex<V, E>, PathInfo<V, E>> getMinPath(Map<Vertex<V, E>, PathInfo<V, E>> paths) {
        Iterator<Map.Entry<Vertex<V, E>, PathInfo<V, E>>> iterator = paths.entrySet().iterator();
        Map.Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = iterator.next();
        while (iterator.hasNext()) {
            Map.Entry<Vertex<V, E>, PathInfo<V, E>> entry = iterator.next();
            if (weightManager.compare(entry.getValue().weight, minEntry.getValue().weight) < 0) {
                minEntry = entry;
            }
        }
        return minEntry;
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

        public EdgeInfo<V, E> info() {
            return new EdgeInfo<>(from.value, to.value, weight);
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
