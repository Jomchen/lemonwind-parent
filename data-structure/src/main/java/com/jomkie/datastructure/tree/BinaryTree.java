package com.jomkie.datastructure.tree;

import com.jomkie.common.util.treeprint.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * 二叉树
 * @author Jomkie
 * @since 2021-08-15 23:20:48
 */
public abstract class BinaryTree<E> implements BinaryTreeInfo {

    protected Node<E> root;
    protected int size;

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public void clear() {
        size = 0;
        root = null;
    }

    public int height() {
        return height(root);
    }
    private int height(Node<E> node) {
        if (node == null) { return 0; }
        return 1 + Math.max(height(node.left), height(node.right));
    }
    public int height2() {
        if (null == root) { return 0; }

        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);
        int currentHeight = 0;
        int numberOfCurrentLayer = queue.size();
        while ( ! queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (null != node.left) {
                queue.add(node.left);
            }
            if (null != node.right) {
                queue.add(node.right);
            }

            -- numberOfCurrentLayer;
            if (numberOfCurrentLayer == 0) {
                currentHeight ++;
                numberOfCurrentLayer = queue.size();
            }
        }

        return currentHeight;
    }

    public boolean isComplete() {
        if (null == root) { return false; }
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);

        boolean existLeaf = false;
        while ( ! queue.isEmpty()) {
            Node<E> node = queue.poll();
            Node<E> left = node.left;
            Node<E> right = node.right;

            if (existLeaf && ! node.isLeaf()) { return false; }

            if (left != null) {
                queue.add(left);
            } else if (right != null) {
                return false;
            }

            if (right != null) {
                queue.add(right);
            } else { // right = null
                existLeaf = true;
            }


            /*这块代码不如以上优秀
                if (node.hasTwoChildren()) {
                queue.add(left);
                queue.add(right);
            } else if (null == left && null != right) {
                return false;
            } else {
                // 左右都为空，左不为空且右为空
                // 这两种情况出现一种，之后的元素度必须为 0 才是完全二叉树
                existLeaf = true;
                if (left != null) {
                    queue.add(left);
                }
            }*/
        }

        return true;
    }

    /** 翻转二叉树，任何一种遍历方式都可以翻转 */
    public void invertTree() {
        if (null == root) { return; }
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);

        while ( ! queue.isEmpty()) {
            Node<E> node = queue.poll();
            Node<E> temporary = node.left;
            node.left = node.right;
            node.right  = temporary;

            if (null != node.left) { queue.add(node.left); }
            if (null != node.right) { queue.add(node.right); }
        }
    }

    public void invertTree2() {
        invertTree2(root);
    }
    // 前序遍历方式翻转
    public void invertTree2(Node<E> node) {
        if (null == node) { return; }

        Node<E> tem = node.left;
        node.left = node.right;
        node.right = tem;

        invertTree2(node.left);
        invertTree2(node.right);
    }

    /** 前序遍历 Preorder Traversal */
    public void preorderTraversal(Consumer<E> consumer) {
        preorderTraversalTool(root, consumer);
    }
    private void preorderTraversalTool(Node<E> node, Consumer<E> consumer) {
        if (null == node) { return; }
        consumer.accept(node.element);
        preorderTraversalTool(node.left, consumer);
        preorderTraversalTool(node.right, consumer);
    }

    /**
     * 中序遍历 Inorder Traversal
     * 二叉搜索树的中序遍历是，升序或降序
     */
    public void inorderTraversal(Consumer<E> consumer) {
        inorderTraversalTool(root, consumer);
    }
    private void inorderTraversalTool(Node<E> node, Consumer<E> consumer) {
        if (null == node) { return; }
        inorderTraversalTool(node.left, consumer);
        consumer.accept(node.element);
        inorderTraversalTool(node.right, consumer);
    }

    /** 后序遍历 Postorder Traversal */
    public void postorderTraversal(Consumer<E> consumer) {
        postorderTraversalTool(root, consumer);
    }
    private void postorderTraversalTool(Node<E> node, Consumer<E> consumer) {
        if (null == node) { return; }
        postorderTraversalTool(node.left, consumer);
        postorderTraversalTool(node.right, consumer);
        consumer.accept(node.element);
    }

    /** 层序遍历 Level Order Traversal */
    public void levelOrderTraversal(int depth, Consumer<E> consumer) {
        Queue<Node<E>> queue = new LinkedList<>();

        int currentDepth = 1;
        queue.add(root);
        int numbersOfCurrentLayer = queue.size();
        int endDepth = depth + 1;

        while ( ! queue.isEmpty()) {
            if (currentDepth >= endDepth) { return; }
            Node<E> node = queue.poll();
            consumer.accept(node.element);

            Node<E> leftNode = node.left;
            Node<E> rightNode = node.right;
            if (null != leftNode) { queue.add(leftNode); }
            if (null != rightNode) { queue.add(rightNode); }

            -- numbersOfCurrentLayer;
            if (numbersOfCurrentLayer <= 0) {
                currentDepth ++;
                numbersOfCurrentLayer = queue.size();
            }
        }

    }

    /**
     * 前驱节点
     * @author Jomkie
     * @since 2021-08-11 21:40:52
     * @param node
     * @return com.jomkie.datastructure.tree.BinarySearchTree.Node<E>
     */
    protected Node<E> predecessor(Node<E> node) {
        if (null == node) { return null; }
        if (null != node.left) {
            Node<E> temporary = node.left;
            while (null != temporary.right) {
                temporary = temporary.right;
            }
            return temporary;
        }

        while (null != node.parent && node == node.parent.left) {
            node = node.parent;
        }

        // node.parent == null
        // node.parent.right == node
        return node.parent;
    }

    /**
     * 后继节点
     * @author Jomkie
     * @since 2021-08-11 21:40:52
     * @param node
     * @return com.jomkie.datastructure.tree.BinarySearchTree.Node<E>
     */
    protected Node<E> successor(Node<E> node) {
        if (null == node) { return null; }
        if (null != node.right) {
            Node<E> temporary = node.right;
            while (null != temporary.left) {
                temporary = temporary.left;
            }
            return temporary;
        }

        while (null != node.parent && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }



    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> wrapNode = ((Node<E>) node);
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(wrapNode.element)
                .append("_p(");
        if (null == wrapNode.parent) {
            resultBuilder.append("null");
        } else {
            resultBuilder.append(wrapNode.parent.element);
        }
        resultBuilder.append(")");

        return resultBuilder.toString();
    }

    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;
        int height;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }
        public boolean hasTwoChildren() {
            return left != null && right != null;
        }
    }

}
