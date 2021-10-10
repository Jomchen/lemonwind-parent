package com.jomkie.datastructure.unionfind;

/**
 * 并查集测试
 * @author jomkie
 * @since 2021-10-08 22:51:27
 */
public class UnionFindTest {

	public static void main(String[] args) {
		testUnionFind();
	}
	
	public static void testUnionFind() {
		//UnionFind uf = new UnionFind_QF(12);
		//UnionFind uf = new UnionFind_QU(12);
		//UnionFind uf = new UnionFind_QU_S(12);
		//UnionFind uf = new UnionFind_QU_R(12);
		//UnionFind uf = new UnionFind_QU_R_PC(12);
		//UnionFind uf = new UnionFind_QU_R_PS(12);
		UnionFind uf = new UnionFind_QU_R_PH(12);
		uf.union(0, 1);
		uf.union(0, 3);
		uf.union(0, 4);
		uf.union(2, 3);
		uf.union(2, 5);
		
		uf.union(6, 7);
		
		uf.union(8, 10);
		uf.union(9, 10);
		uf.union(9, 11);
		
		// false
		System.out.println(uf.isSame(0, 6));
		// true
		System.out.println(uf.isSame(0, 5));
		
		uf.union(4, 6);
		// true
		System.out.println(uf.isSame(2, 7));
	}
	
}
