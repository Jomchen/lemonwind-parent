package com.lemonwind.datastructure.unionfind;

/**
 * 基于 QuickUnion_QU_R 的路径压缩(Path Compression)
 * 在执行 union(v1, v2)，将 root1 整个集合都直接挂在 root2 上 
 * @author lemonwind
 * @since 2021-10-10 16:27:17
 */
public class UnionFind_QU_R_PC extends UnionFind_QU_R {

	public UnionFind_QU_R_PC(int capacity) {
		super(capacity);
	}

	@Override
	public int find(int v) {
		// 递归处理把根节点赋给整个集合
		rangeCheck(v);
		if (parents[v] != v) {
			parents[v] = find(parents[v]);
		}
		
		return parents[v];
	}
	
}
