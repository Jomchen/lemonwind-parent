package com.jomkie.datastructure.tree.heap;

import com.jomkie.common.util.treeprint.BinaryTrees;

import java.util.stream.IntStream;

public class BinaryHeapTest {

    public static void main(String[] args) {
        test03();
    }

    /** 测试添加 */
    public static void test00() {
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>();
        binaryHeap.add(68);
        binaryHeap.add(72);
        binaryHeap.add(43);
        binaryHeap.add(50);
        binaryHeap.add(38);

        BinaryTrees.println(binaryHeap);
    }

    /** 测试删除 */
    public static void test01() {
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>();
        binaryHeap.add(68);
        binaryHeap.add(72);
        binaryHeap.add(43);
        binaryHeap.add(50);
        binaryHeap.add(38);
        binaryHeap.add(10);
        binaryHeap.add(90);
        binaryHeap.add(65);

        BinaryTrees.println(binaryHeap);
        binaryHeap.remove();
        BinaryTrees.println(binaryHeap);
    }

    /** 测试替换 */
    public static void test02() {
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>();
        binaryHeap.add(68);
        binaryHeap.add(72);
        binaryHeap.add(43);
        binaryHeap.add(50);
        binaryHeap.add(38);
        binaryHeap.add(10);
        binaryHeap.add(90);
        binaryHeap.add(65);

        BinaryTrees.println(binaryHeap);
        System.out.println(binaryHeap.replace(70) + "---");
        BinaryTrees.println(binaryHeap);
    }

    public static void test03() {
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>();
        // 待实现
        BinaryTrees.println(binaryHeap);
    }

}
