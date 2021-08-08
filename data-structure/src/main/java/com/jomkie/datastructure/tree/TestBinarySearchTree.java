package com.jomkie.datastructure.tree;

import com.jomkie.common.util.treeprint.BinaryTrees;

import java.util.Arrays;
import java.util.List;

public class TestBinarySearchTree {

    public static void main(String[] args) {
        test00();
    }

    /** 测试二叉树打印 */
    public static void test00() {
        BinarySearchTree bst = new BinarySearchTree();
        List<Integer> dataList = Arrays.asList(7, 4, 9, 2, 5, 8, 11, 3, 12, 1);
        dataList.forEach(bst::add);
        BinaryTrees.println(bst);

        //bst.preorderTraversal(System.out::println);
        //bst.inorderTraversal(System.out::println);
        //bst.postorderTraversal(System.out::println);
        bst.levelOrderTraversal(5, System.out::println);
    }

}
