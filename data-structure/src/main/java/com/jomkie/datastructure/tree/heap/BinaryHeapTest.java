package com.jomkie.datastructure.tree.heap;

import com.jomkie.common.util.treeprint.BinaryTrees;

import java.util.Comparator;
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

    /** 测试批量创建建堆 */
    public static void test03() {
        Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};
        // 最大堆
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(data);
        BinaryTrees.println(binaryHeap);

        // 最小堆
        Comparator<Integer> comparator = Integer::compare;
        comparator = comparator.reversed();
        binaryHeap = new BinaryHeap<>(data, comparator);
        BinaryTrees.println(binaryHeap);
    }

}
