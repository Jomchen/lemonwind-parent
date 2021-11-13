package com.jomkie.datastructure.tree.heap;

import com.jomkie.common.util.treeprint.BinaryTrees;

import java.util.Comparator;
import java.util.stream.IntStream;

public class BinaryHeapTest {

    public static void main(String[] args) {
        test04();
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

    /** 在一组数据中取出最大的前 k 个数 */
    public static void test04() {
        BinaryHeap<Integer> heap = new BinaryHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        int k = 5;
        Integer[] data = {51, 30, 39, 92, 74, 25, 16, 93,
            91, 19, 54, 47, 73, 62, 76, 63, 35, 18,
            90, 6, 65, 49, 3, 26, 61, 21, 48};

        // 取最大的前 k 个元素，就要创建小顶堆
        for (int i = 0; i < data.length; i++) {
            if (heap.size() < k) {
                heap.add(data[i]);
                BinaryTrees.println(heap);
            } else {
                if (data[i] > heap.get()) {
                    heap.replace(data[i]);
                }
            }
        }

        // 93, 92, 91, 90
        BinaryTrees.println(heap);
    }
}
