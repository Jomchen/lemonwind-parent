package com.jomkie.datastructure.tree;


import java.util.Comparator;

/**
 * 二叉搜索树
 * @author Jomkie
 * @since 2021-08-15 23:25:35
 */
public class Bst<E> extends BinaryTree<E> {

    private Comparator<E> comparator;

    public Bst() {
        this(null);
    }
    public Bst(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void add(E element) {
        elementNotNullCheck(element);
        if (null == root) {
            root = new Node<>(element, null);
            size ++;
            return;
        }

        Node<E> parent = root;
        Node<E> node = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node.element = element;
                return;
            }
        }

        Node<E> newNode = new Node(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else if (cmp < 0) {
            parent.left = newNode;
        } else {
            return;
        }

        size ++;
    }

    public void remove(E element) {
        remove(node(element));
    }
    private void remove(Node<E> node) {
        if (null == node) { return; }

        size --;
        if (node.hasTwoChildren()) {
            Node<E> s = successor(node);
            node.element = s.element;
            node = s;
        }

        // 这里 node 的度必然是 0 或 1
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (null != replacement) {
            // node 是度为1的节点
            replacement.parent = node.parent;
            if (node.parent == null) {
                root = replacement;
            } else if (node.parent.left == node) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
        } else if (node.parent == null) {
            // node 度为0 且是根节点
            root = null;
        } else {
            // node 度为0 但不是根节点
            if (node.parent.left == node) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }
    }

    public boolean contains(E element) {
        return node(element) != null;
    }



    private Node<E> node(E e) {
        if (null == e || null == root) { return null; }

        Node<E> node = root;
        while (null != node) {
            int cmp = compare(e, node.element);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }

        return null;
    }

    /**
     * 返回值大于0表示 e1 > e2，
     * 如果小于0表示 e1 < e2，
     * 否则 e1 = e2
     * */
    private int compare(E e1, E e2) {
        if (null == comparator) {
            return ((Comparable<E>) e1).compareTo(e2);
        } else {
            return comparator.compare(e1, e2);
        }
    }

    private void elementNotNullCheck(E element) {
        if (null == element) {
            throw new RuntimeException("element must be not null");
        }
    }



}
