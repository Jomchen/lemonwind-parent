package com.lemonwind.datastructure.bloomfilter;

/**
 * 布隆过滤器
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

    /** 二进制向量，这里存放有区别，如图示： */
    /** 63-0, 127-64, 191-128, ...,... */
    private long[] bits;

    /** Hash函数的个数 */
    private int hashSize;

    /**
     * @param n 数据规模
     * @param p 误判率
     */
    public BloomFilter(int n, double p) {
        if (n <= 0 || p <= 0 || p >= 1) {
            throw new IllegalArgumentException("wrong n or p");
        }

        double ln2 = Math.log(2);
        // 求出二进制向量的长度
        bitSize = (int) (-(n * Math.log(p)) / (ln2 * ln2));
        // 求出哈希函数的个数
        hashSize = (int) (bitSize * ln2  / n);
        // bits 数组的长度
        bits = new long[(bitSize  + Long.SIZE - 1)/ Long.SIZE];

        System.out.println(bitSize);
        System.out.println(hashSize);
    }


    /**
     * 添加元素
     * @param value
     */
    public boolean put(T value) {
        nullCheck(value);

        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        boolean result = false;
        for (int i = 1; i <= hashSize; i++) {
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            // 生成一个二进位的索引，并设置相应位置为 1
            int index = combinedHash % bitSize;
            // 设置 index 位置的二进位为 1
            if (set(index)) {
                result = true;
            }
        }

        return result;
    }

    /**
     * 设置 index 位置的二进制位为 1
     * @param index
     */
    private boolean set(int index) {
        long value = bits[index / Long.SIZE];
        int bitValue = 1 << (index % Long.SIZE);
        bits[index / Long.SIZE] = value | bitValue;
        return (value & bitValue) == 0;
    }

    /**
     * 查看 index 二进制位置的值
     * @param index 索引位置
     * 如果为 1 返回 true，否则返回 false
     */
    private boolean get(int index) {
        long data = bits[index / Long.SIZE];
        return (data & (1 << (index % Long.SIZE))) != 0;
    }

    /**
     * 是否存在
     * @param value
     * @return
     */
    public boolean contains(T value) {
        nullCheck(value);

        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        for (int i = 1; i <= hashSize; i++) {
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            // 生成一个二进位的索引
            int index = combinedHash % bitSize;
            if (! get(index)) { return false; }
        }

        return true;
    }

    private void nullCheck(T value) {
        if (value == null) {
            throw new IllegalArgumentException("the value is null");
        }
    }

    public static void main(String[] args) {
//        BloomFilter<Integer> bf = new BloomFilter<>(1_000_000, 0.01);
//        for (int i = 0; i <= 50; i++) {
//            bf.put(i);
//        }
//        for (int i = 0; i <= 50; i++) {
//            System.out.println(bf.contains(i));
//        }

        BloomFilter<String> bf2 = new BloomFilter<>(1_000_000, 0.01);
        bf2.put("123123");
        System.out.println(bf2.contains("123123"));;
    }

}
