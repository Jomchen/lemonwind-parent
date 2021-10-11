package com.jomkie.datastructure.unionfind;

/**
 * 基于 UnionFind_QU_R 的路径分裂（Path Spliting）
 * 在 find(v) 时，让v以上直到根的所有每个节点都指向对应的祖父节点（即父亲的父亲）
 * @author Jomkie
 * @since 2021-10-10 18:49:38
 */
public class UnionFind_QU_R_PS extends UnionFind_QU_R {

    public UnionFind_QU_R_PS(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (parents[v] != v) {
            int p = parents[v];
            parents[v] = parents[parents[v]];
            v = p;
        }

        return v;
    }

}
