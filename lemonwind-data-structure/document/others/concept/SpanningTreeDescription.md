## 生成树（Spanning Tree）

* 生成树，也称为支撑树
   - 连通图的极小连通子图，它含有图中全部的 n 个顶点，恰好只有 n - 1 条边
* 最小生成树（Minimum Spanning Tree，简称 MST）
   - 也称为最小权重生成树，最小支撑树
   - 是所有生成树中，总权值最小的那棵
   - 适用于有权的连通图（无向）
* 如果图的每一条边的权值都不相同，那么最小生成树将只有一个，否则可能会有多个最小生成树
* 应用场景举例
   - 多个城市要捕设光缆，以让每个城市之间都可以信息通信

* 求最小生成树的算法
   - Prim
      - 假设 G = (V, E) 是有权的连通图（无向），A 是 G 中最小生成树的边集
      - 算法从 S = {u0} (u0 包含于 A), A = {} 开始，重复执行下述操作，直到 S = V 为止
         - 找到切分 C = (S, V-S)的最小横切边 {u0, v0) 并入集合 A，同时将 V0 并入集合 S
      - 连通图的极小连通子图，它含有图中全部的 n 个顶点，恰好只有 n-1 条边
   - Kruskal
      - 按照边的权重顺序（从小到大）将边加入生成树中，直到树中含有 V-1 条边为止（V 是顶点数量）
         - 若加入该边会与生成树形成环，则不加入该边
         - 从第3条边开始，可能会与生成树形成环
