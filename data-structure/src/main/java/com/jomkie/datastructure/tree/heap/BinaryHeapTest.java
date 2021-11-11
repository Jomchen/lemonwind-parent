package com.jomkie.datastructure.tree.heap;

import com.jomkie.common.util.treeprint.BinaryTrees;

public class BinaryHeapTest {

    public static void main(String[] args) {
        test00();
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

}
