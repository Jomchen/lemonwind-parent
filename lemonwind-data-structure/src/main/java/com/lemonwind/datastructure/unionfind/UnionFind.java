package com.lemonwind.datastructure.unionfind;

/**
 * 并查集抽象
 * @author lemonwind
 * @since 2021-10-08 22:52:47
 */
public abstract class UnionFind {

	protected int[] parents;
	
	public UnionFind(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("capacity must be greater equals than 1");
		}
		
		parents = new int[capacity];
		for (int i = 0; i < parents.length; i++) {
			parents[i] = i;
		}
	}

	/**
	 * 查找v所属的集合（根节点）
	 * @author lemonwind
	 * @since 2021-10-08 23:24:01
	 * @param v
	 * @return
	 */
	public abstract int find(int v);
	
	/**
	 * 合并 v1 和 v2
	 * @author lemonwind
	 * @since 2021-10-08 23:24:18
	 * @param v1
	 * @param v2
	 */
	public abstract void union(int v1, int v2);
	
	/**
	 * 查看 v1 和 v2 是否属于同一个集合
	 * @author lemonwind
	 * @since 2021-10-08 23:24:28
	 * @param v1
	 * @param v2
	 * @return
	 */
	public boolean isSame(int v1, int v2) {
		return find(v1) == find(v2);
	}
	
	protected void rangeCheck(int v) {
		if (v < 0 || v >= parents.length) {
			throw new IllegalArgumentException("v is out of bounds");
		}
	}
	
}
