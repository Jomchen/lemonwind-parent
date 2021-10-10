package com.jomkie.datastructure.unionfind;

/**
 * 基于 UnionFind_QU_R 的路径减半（Path Halving）
 * 使路径上每隔一个节点就指向其祖父节点（父亲的父亲）
 * @author Jomkie
 * @since 2021-10-10 19:2:56
 * @param
 * @return
 */
public class UnionFind_QU_R_PH extends UnionFind_QU_R {

    public UnionFind_QU_R_PH(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v != parents[v]) {
            parents[v] = parents[parents[v]];
            v = parents[v];
        }
        return v;
    }

}
