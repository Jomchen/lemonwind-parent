package com.jomkie.datastructure.tree;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

@Data
@Getter
@Setter
public class BinarySearchTree<E> {

    private int size;
    private Node<E> root;
    private Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }
    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    int size() {
        return size;
    }

    boolean isEmpty() {
        return size == 0;
    }

    void clear() {

    }
    void add(E element) {
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
    void remove(E element) {

    }
    boolean contains(E element) {
        return false;
    }

    /** 返回值大于0表示 e1 > e2，如果小于0表示 e1 < e2，否则 e1 = e2 */
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

    private static class Node<E> {
        E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }
    }

}
