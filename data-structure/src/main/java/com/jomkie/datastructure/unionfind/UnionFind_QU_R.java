package com.jomkie.datastructure.unionfind;

import java.util.Arrays;

/**
 * 基于 QuickUnion 的 rank 优化
 * 将高度低的根节点挂在高度高的根节点下
 * @author jomkie
 * @since 2021-10-10 18:32:34
 */
public class UnionFind_QU_R extends UnionFind_QU {

	/** 记录当前节点作为根时本集合的高度 */
	private int[] ranks;
	
	public UnionFind_QU_R(int capacity) {
		super(capacity);
		
		ranks = new int[capacity];
		Arrays.fill(ranks, 1);
	}

	
	@Override
	public void union(int v1, int v2) {
		int root1 = find(v1);
		int root2 = find(v2);
		if (root1 == root2) { return; }

		// 当矮的挂在高的顶级节点下时，高的顶级节点高度不会变
		// 只有高度相同的顶级节点相互依挂时，才会发生高度的增加一
		if (ranks[root1] < ranks[root2]) {
			parents[root1] = root2;
		} else if (ranks[root1] > ranks[root2]) {
			parents[root2] = root1;
		} else {
			parents[root1] = root2;
			ranks[root2] += 1;
		}
	}
	
}
