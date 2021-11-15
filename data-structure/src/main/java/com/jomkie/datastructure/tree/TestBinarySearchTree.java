package com.jomkie.datastructure.tree;

import com.jomkie.common.util.treeprint.BinaryTrees;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 二叉树的前，中，后，层序遍历
 * 二叉树的最大深度
 */
public class TestBinarySearchTree {

    public static void main(String[] args) {
        //test00();
        //test01();
        //test02();
        //test03();
        //test04();
        //test05();
        //test06();
        //test07();
        test08();
    }

    /** 测试二叉树打印 */
    public static void test00() {
        BST bst = new BST();
        List<Integer> dataList = Arrays.asList(7, 4, 9, 2, 5, 8, 11, 3, 12, 1);
        dataList.forEach(bst::add);
        BinaryTrees.println(bst);

        //bst.preorderTraversal(System.out::println);
        //bst.inorderTraversal(System.out::println);
        //bst.postorderTraversal(System.out::println);
        //bst.levelOrderTraversal(5, System.out::println);
        //System.out.println("高度" + bst.height2());
        System.out.println(bst.isComplete());
    }

    /** 测试是否是完全二叉树 */
    public static void test01() {
        BST bst = new BST();
        // false
        //List<Integer> dataList = Arrays.asList(7, 4, 9, 2, 5, 8, 11, 1, 3, 10, 12);

        // true
        List<Integer> dataList = Arrays.asList(7, 4, 9, 2, 5);

        // false
        //List<Integer> dataList = Arrays.asList(7, 4, 9, 2, 1);
        //System.out.println(bst.isComplete());

        dataList.forEach(bst::add);
        BinaryTrees.println(bst);
        bst.invertTree();
        BinaryTrees.println(bst);
    }

    /** 翻转二叉树 */
    public static void test02() {
        BST bst = new BST();
        List<Integer> dataList = Arrays.asList(7, 4, 9, 2, 5);
        dataList.forEach(bst::add);
        BinaryTrees.println(bst);
        bst.invertTree();
        BinaryTrees.println(bst);
    }

    /** 前驱节点 或 后继节点 */
    public static void test03() {
        BST bst = new BST();
        List<Integer> dataList = Arrays.asList(7, 4, 9, 2, 5, 8, 11, 3, 12, 1);
        dataList.forEach(bst::add);
        BinaryTrees.println(bst);
        bst.remove(9);
        /*bst.remove(7);*/
        BinaryTrees.println(bst);
    }
    
    /** 测试平衡二叉树的添加 */
    public static void test04() {
        //List<Integer> dataList = Arrays.asList(7, 4, 9, 2, 5, 8, 11, 3, 12, 1);
        List<Integer> dataList = Arrays.asList(
                67, 52, 92, 96, 53, 95, 13, 63, 34, 82, 76, 54, 9, 68, 39
        );
        AvlTree<Integer> avlTree = new AvlTree<>();
        dataList.forEach(e -> {
            System.out.println("<" + e + "> -------------------------------------");
            avlTree.add(e);
            BinaryTrees.println(avlTree);
        });
    }

    /** 测试平衡二叉树的删除 */
    public static void test05() {
        //Integer[] dataSource = new Integer[] {85,19,69,3,7,99,95};
        //Integer[] dataSource = {85, 19, 69, 3, 7, 99, 95, 2, 1, 70, 44, 58, 11, 21, 14, 93, 57, 4, 56};
        Integer[] dataSource = {
                67, 52, 92, 96, 53, 95, 13, 63, 34, 82, 76, 54, 9, 68, 39
        };
        AvlTree<Integer> avlTree = new AvlTree<>();
        Arrays.stream(dataSource).forEach(avlTree::add);
        System.out.println("初始化：---------------------------------------");
        BinaryTrees.println(avlTree);
        for (int i = 0; i < dataSource.length; i++) {
            avlTree.remove(dataSource[i]);
            System.out.println("删除了：" + dataSource[i] + "---------------------------------------");
            BinaryTrees.println(avlTree);
        }


        /*avlTree.remove(99);
        BinaryTrees.println(avlTree);
        System.out.println("---------------------------------------");
        avlTree.remove(85);
        BinaryTrees.println(avlTree);
        System.out.println("---------------------------------------");
        avlTree.remove(95);
        BinaryTrees.println(avlTree);
        System.out.println("---------------------------------------");*/

    }

    /** 测试红黑树的添加 */
    public static void test06() {
        Integer[] dataSource = {
                55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
        };
        RBTree<Integer> rbTree = new RBTree<>();
        for (int i = 0; i < dataSource.length; i++) {
            rbTree.add(dataSource[i]);
            System.out.println("添加了：" + dataSource[i] + "--------------------------------------");
            BinaryTrees.println(rbTree);
        }
    }

    /** 测试红黑树的删除 */
    public static void test07() {
        Integer[] dataSource = {
                55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
        };
        RBTree<Integer> rbTree = new RBTree<>();
        Arrays.stream(dataSource).forEach(rbTree::add);
        System.out.println("初始化：-----------------------------------");
        BinaryTrees.println(rbTree);
        Arrays.stream(dataSource).forEach(e -> {
        System.out.println("删除" + e + " -----------------------------------");
            rbTree.remove(e);
            BinaryTrees.println(rbTree);
        });
    }

    /** 测试非递归先序遍历 */
    public static void test08() {
        BST<Integer> tree = new BST();
        Stream.of(7, 4, 9, 2, 5, 8, 11).forEach(tree::add);
        // 7 4 2 5 9 8 11
        tree.preorderNoRecursion(System.out::println);
    }

    /** 测试非递归中序遍历 */
    public static void test09() {
        BST<Integer> tree = new BST();
        Stream.of(7, 4, 9, 2, 5, 8, 11).forEach(tree::add);
        // 2 4 5 7 8 9 11
    }

    /** 测试非递归后序遍历 */
    public static void test10() {
        BST<Integer> tree = new BST();
        Stream.of(7, 4, 9, 2, 5, 8, 11).forEach(tree::add);
        // 2 5 4 8 11 9 7
    }

}
