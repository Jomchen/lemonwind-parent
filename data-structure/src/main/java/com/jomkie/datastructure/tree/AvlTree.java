package com.jomkie.datastructure.tree;

import java.util.Comparator;

/**
 * AVL-Tree
 * 添加可能导致所有的祖先节点失衡，只要主高度最低的失衡节点恢复平衡，整棵树就恢复平衡，仅需 O(1) 次调整
 * 删除只可能会导致父节点失衡，恢复父节点平衡后可能导致更高层的祖先节点失衡，最多需求O(logn)次调整
 * @author Jomkie
 * @since 2021-08-16 21:11:18
 */
public class AvlTree<E> extends Bst<E> {

    public AvlTree() { this(null); }
    public AvlTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        // 新添加的节点一定是叶子节点且高度一定为 1
    	while ((node = node.parent) != null) {
    		if (isBalance(node)) {
    			// 更新高度
    			updateHeight(node);
    		} else {
    			// 恢复平衡
                rebalance(node);
                break;
    		}
    	}
    }

    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalance(node)) {
                // 更新高度
                updateHeight(node);
            } else {
                // 恢复平衡
                rebalance(node);
            }
        }
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
    	return new AVLNode<>(element, parent);
    }

    /**
     * 是否平衡
     * @param node
     * @return
     */
    private boolean isBalance(Node<E> node) {
    	AVLNode<E> avlNode = (AVLNode<E>) node;
    	int balance = avlNode.balanceFactor();
    	return Math.abs(balance) <= 1;
    }

    /**
     * 恢复平衡
     * @param grand 高度最低的那个不平衡节点
     */
    private void rebalance(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>)grand).tallerChild();
        Node<E> node = ((AVLNode<E>)parent).tallerChild();
        /*if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                rotateRight(grand);
            } else { // LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        } else { // R
            if (node.isLeftChild()) { // RL
                rotateRight(parent);
                rotateLeft(grand);
            } else { // RR
                rotateLeft(grand);
            }
        }*/

        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                rotate(grand, node.left, node, node.right, parent, parent.right, grand, grand.right);
            } else { // LR
                rotate(grand, parent.left, parent, node.left, node, node.right, grand, grand.right);
            }
        } else { // R
            if (node.isLeftChild()) { // RL
                rotate(grand, grand.left, grand, node.left, node, node.right, parent, parent.right);
            } else { // RR
                rotate(grand, grand.left, grand, parent.left, parent, node.left, node, node.right);
            }
        }
    }

    /**
     * 综合旋转方法
     * @param r 根节点
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     * @param f
     * @param g
     */
    private void rotate(
            Node<E> r,
            Node<E> a, Node<E> b, Node<E> c,
            Node<E> d,
            Node<E> e, Node<E> f, Node<E> g) {

        // 如果 7 个节点的二叉树举例通过中序遍历进行标号
        // 不管是四种旋转中的任何旋转，最终的结果都是一样的

        // 让 d 成为子树的根节点
        d.parent = r.parent;
        if (r.isLeftChild()) {
            r.parent.left = d;
        } else if (r.isRightChild()) {
            r.parent.right = d;
        } else {
            root = d;
        }

        // a-b-c
        b.left = a;
        if (null != a) { a.parent = b; }
        b.right = c;
        if (null != c) { c.parent = b; }
        updateHeight(b);

        // e-f-g
        f.left = e;
        if (null != e) { e.parent = f; }
        f.right = g;
        if (null != g) { g.parent = f; }
        updateHeight(f);

        // b-d-f
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;
        updateHeight(d);
    }

    /**
     * 左旋转
     * @param grand
     */
    private void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 右旋转
     * @param grand
     */
    private void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    private void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
    // 更新 parent 的 parent
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            root = parent;
        }

        // 更新 child 的 parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新 grand 的 parent
        grand.parent = parent;

        // 更新高度
        updateHeight(grand);
        updateHeight(parent);
    }

    /**
     * 更新高度
     * @param node
     */
    private void updateHeight(Node<E> node) {
    	AVLNode<E> avlNode = (AVLNode<E>) node;
    	avlNode.updateHeight();
    }



    private static class AVLNode<E> extends Node<E> {
        int height = 1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        /**
         * 计算平衡因子
         * @return
         */
        public int balanceFactor() {
        	int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
        	int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
        	return leftHeight - rightHeight;
        }

        /**
         * 更新高度
         */
        public void updateHeight() {
        	int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
        	int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
        	height = 1 + Math.max(leftHeight, rightHeight);
        }

        /**
         * 比较高的子节点
         * @return
         */
        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            if (leftHeight > rightHeight) return left;
            if (leftHeight < rightHeight) return right;
            return isLeftChild() ? left : right;
        }

        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return element + "_p(" + parentString + ")_h(" + height + ")";
        }
    }

}
