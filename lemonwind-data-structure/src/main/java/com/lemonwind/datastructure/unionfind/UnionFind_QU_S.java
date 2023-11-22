package com.lemonwind.datastructure.unionfind;

import java.util.Arrays;

/**
 * 基于 QuickUnion 的 size 优化，union(v1, v2)
 * v1 的根节点为 root1，v2 的根节点为 root2
 * 如果 root1 所在集合的元素少，root2集合所在的元素多
 * 则 root1 挂在 root2 下
 * 即元素少的集合根节点 挂在 元素多的集合根节点下
 * @author jomkie
 * @since 2021-10-10 10:52:52
 */
public class UnionFind_QU_S extends UnionFind_QU {

	/** 记录相应元素作为根时，这个集合的元素数量，初始为1 */
	private int[] sizes;
	
	public UnionFind_QU_S(int capacity) {
		super(capacity);
		sizes = new int[capacity];
		Arrays.fill(sizes, 1);
	}

	@Override
	public void union(int v1, int v2) {
		int root1 = find(v1);
		int root2 = find(v2);
		if (root1 == root2) { return; }
		
		if (sizes[root1] < sizes[root2]) {
			parents[root1] = root2;
			sizes[root2] += sizes[root1];
		} else {
			// 如果元素数量相等则也认为 root2 的元素少
			parents[root2] = root1;
			sizes[root1] += sizes[root2];
		}
	}

}
