package com.lemonwind.datastructure.tree.hafuman;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 哈夫曼树
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HuffmanTree<T> {

    private TreeNodeData<T> root;

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        /**
         * 一棵树中，从一个结点往下可以达到的孩子或孙子结点之间的通路，称为路径
         * 通路中分支的数目称为路径长度。若规定根结点的层数为1,则从根结点到第 L 层结点的路径长度为 L - 1
         * 结点的带权路长度：从根结点到该结点之间的路径长度 * 该结点的权
         *
         * 树的带权长度：树的带权路径长度规定为所有叶子结点的带权路径长度之和
         * 记为 WPL(weighted path length)，权值越大的结点离根结点越近的二叉树才最优
         * WPL 最小的就是赫夫曼树
         *          5
         *        /   \
         *       3     8
         *      / \   / \
         *     2   4 7  9
         *
         *     结点 5 到 4 的 带权路径长度为 2 * 4 = 8
         *       2：结点 5 到 4 经过的层数
         *       4: 为结点本身赋予的权为 4
         *       8: 带权路径长度 = 2 * 4
         *     树的带权路径长度为：2*2+4*2+7*2+9*2 = 44
         *
         */

        Integer[] array = {13, 7, 8, 3, 29, 6, 1};
        HuffmanTree<String> huffmanTree = new HuffmanTree<>();
        List<TreeNodeData<String>> list = Arrays.stream(array)
                .map(i -> new StringDataTree(i, String.valueOf(i)))
                .collect(Collectors.toList());
        TreeNodeData<String> root = huffmanTree.create(list);
        root.xianxubianli();

    }

    public TreeNodeData<T> create(List<TreeNodeData<T>> baseList) {
        if (null == baseList || baseList.size() < 2) {
            throw new RuntimeException("集合不能为null 以及 元素个数最小为2");
        }
        LinkedList<TreeNodeData<T>> linkedList = new LinkedList<>();
        baseList.stream().forEach(linkedList::add);

        while (linkedList.size() > 1) {
            // 这个排序不稳定
            // list.stream().sorted(Comparator.comparing(TreeNodeData::getCode));
            Collections.sort(linkedList);

            TreeNodeData<T> left = linkedList.pop();
            TreeNodeData<T> right = linkedList.pop();
            int newCode = left.getCode() + right.getCode();
            TreeNodeData newNode = new StringDataTree(newCode, String.valueOf(newCode));
            newNode.setLeft(left);
            newNode.setRight(right);

            linkedList.addLast(newNode);
        }

        TreeNodeData<T> rootNode = linkedList.get(0);
        this.root = rootNode;
        return root;
    }

}
