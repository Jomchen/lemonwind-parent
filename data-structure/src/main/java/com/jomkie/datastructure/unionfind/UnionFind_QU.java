package com.jomkie.datastructure.unionfind;

/**
 * QuickUnion 合并快，查询慢
 * 是将一个集合的根节点直接挂在另一个根节点下，缺点是极端情况会退化为链表
 * @author jomkie
 * @since 2021-10-08 23:05:04
 */
public class UnionFind_QU extends UnionFind {

	public UnionFind_QU(int capacity) {
		super(capacity);
	}

	@Override
	public int find(int v) {
		rangeCheck(v);
		while (parents[v] != v) {
			v = parents[v];
		}
		return v;
	}

	@Override
	public void union(int v1, int v2) {
		// 此种相当于每个索引存储的是父级元素的索引
		// 分别找到 v1 的根节点 rootIndex1，v2 的根节点 rootIndex2，然后把 root1 挂到 root2 下
		int root1 = find(v1);
		int root2 = find(v2);
		if (root1 == root2) { return; }
		parents[root1] = root2;
	}

}
