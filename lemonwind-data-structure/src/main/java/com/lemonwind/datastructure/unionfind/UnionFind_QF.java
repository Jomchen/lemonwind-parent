package com.lemonwind.datastructure.unionfind;

/**
 * QuickFind 查询快，合并慢
 * 树的高度最多只有两级，相当于是一个集合的所有元素直接挂在另一个集合的根节点下
 * 查询快，但是在合并时比较耗性能
 * @author jomkie
 * @since 2021-10-08 23:02:57
 */
public class UnionFind_QF extends UnionFind {

	public UnionFind_QF(int capacity) {
		super(capacity);
	}

	@Override
	public int find(int v) {
		rangeCheck(v);
		return parents[v];
	}

	@Override
	public void union(int v1, int v2) {
		// 此种相当于每个元素存储的就是索引所属集合的根节点
		// 得到 v1 的根节点 root1，得到 v2 的根节点 root2，把 root1 这个集合的所有元素直接挂在 root2 下
		int root1 = find(v1);
		int root2 = find(v2);
		if (root1 == root2) { return; }
		
		for (int i = 0; i < parents.length; i++) {
			if (parents[i] == root1) {
				parents[i] = root2;
			}
		}		
	}

}
