package com.jomkie.datastructure.tree;

import com.jomkie.common.util.treeprint.BinaryTrees;

import java.util.Arrays;
import java.util.List;

public class TestBinarySearchTree {

    public static void main(String[] args) {
        //test00();
        //test01();
        //test02();
        test03();
    }

    /** 测试二叉树打印 */
    public static void test00() {
        Bst bst = new Bst();
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
        Bst bst = new Bst();
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
        Bst bst = new Bst();
        List<Integer> dataList = Arrays.asList(7, 4, 9, 2, 5);
        dataList.forEach(bst::add);
        BinaryTrees.println(bst);
        bst.invertTree();
        BinaryTrees.println(bst);
    }

    /** 前驱节点 或 后继节点 */
    public static void test03() {
        Bst bst = new Bst();
        List<Integer> dataList = Arrays.asList(7, 4, 9, 2, 5, 8, 11, 3, 12, 1);
        dataList.forEach(bst::add);
        BinaryTrees.println(bst);
        bst.remove(9);
        /*bst.remove(7);*/
        BinaryTrees.println(bst);
    }
    
    /** 测试平衡二叉树的添加 */
    public static void test04() {
    	List<Integer> dataList = Arrays.asList(7, 4, 9, 2, 5, 8, 11, 3, 12, 1);
    }

}
