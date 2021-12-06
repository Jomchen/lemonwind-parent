package com.jomkie.datastructure.bloomfilter;

/**
 * 布点隆过滤器
 * 它是一个空间效率高的概率型数据结构，可以告诉你：一个元素一定不存在或者可能存在
 * 优缺点：
 *     优点：空间效率和查询时间都远远超过一般的算法
 *     缺点：有一定的误判率，删除困难
 * 它实质上是一个很长的二进制向量和一系列随机映射函数（Hash函数）
 * 添加，查询的时间复杂度都是：O(k)，是哈希函数的个数，空间复杂度是：O(m)，m是二进制位的个数
 *
 * 误判断率 p 受 3 个因素影响：二进制位的个数 m，哈希函数的个数 k，数据规模 n
 * p = (1 - e^(-k(n + 0.5)/(m - 1))^k
 * 因为 n 的数据规模很大，通过高等数学求极限的原理，常数基本可以忽略，最终公式可以简化为：
 *     p = (1 - e^(-kn/m))^k 进一步推导得到如下公式：
 *     m= (-nln(p))/(ln(2))^2
 *     k = (m/n) * ln(2)
 *     k = (-ln(p))/(ln(2)) = -log2为底p为真数
 *
 * Guava: Google Core Libraries For Java
 *     这是 Google 实现的一个Java开源版本
 *     https://mvnrepository.com/artifact/com.google.guava/guava
 */
public class BloomFilter<T> {

    /** 二进制向量的长度（一共有多少个二进制位）*/
    private int bitSize;

    /** 二进制向量 */
    private long[] bits;

    /**
     * @param n 数据规模
     * @param p 误判率
     */
    public BloomFilter(int n, double p) {
        if (n <= 0 || p <= 0 || p >= 1) {
            throw new IllegalArgumentException("wrong n or p");
        }

        double ln2 = Math.log(2);

    }


    /**
     * 添加元素
     * @param value
     */
    public void put(T value) {

    }

    /**
     * 是否存在
     * @param value
     * @return
     */
    public boolean contains(T value) {
        return false;
    }

}
