package com.lemonwind.datastructure.unionfind;

import com.lemonwind.common.entity.bean.JoUser;

import java.util.Date;

/**
 * 并查集测试
 * @author lemonwind
 * @since 2021-10-08 22:51:27
 */
@SuppressWarnings("unchecked")
public class UnionFindTest {

	public static void main(String[] args) {
		test01();
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

	public static void test01() {
		GenericUnionFind<JoUser> uf = new GenericUnionFind<>();
		JoUser user1 = new JoUser(1, "jack", 20, "长安", new Date());
		JoUser user2 = new JoUser(2, "jane", 22, "Canada", new Date());
		JoUser user3 = new JoUser(3, "jack", 22, "Canada", new Date());
		JoUser user4 = new JoUser(4, "jane", 22, "Canada", new Date());
		uf.makeSet(user1);
		uf.makeSet(user2);
		uf.makeSet(user3);
		uf.makeSet(user4);

		uf.union(user1, user2);
		uf.union(user3, user4);

		// true
		System.out.println(uf.isSame(user1, user2));
		// true
		System.out.println(uf.isSame(user3, user4));
		// false
		System.out.println(uf.isSame(user1, user3));

		// true
		/*uf.union(user1, user4);
		System.out.println(uf.isSame(user2, user3));*/
	}
	
}
