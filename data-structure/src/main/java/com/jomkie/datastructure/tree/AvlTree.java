package com.jomkie.datastructure.tree;

import java.util.Comparator;

/**
 * AVL-Tree
 * @author Jomkie
 * @since 2021-08-16 21:11:18
 */
public class AvlTree<E> extends Bst<E> {

    public AvlTree() { this(null); }
    public AvlTree(Comparator<E> comparator) {
        super(comparator);
    }

    private void ll(Node<E> node) {
        Node<E> parent = node.parent;
    }

    private void rr(Node<E> node) {

    }

    private void lr(Node<E> node) {

    }

    private void rl(Node<E> node) {

    }


    private static class AVLNode<E> extends Node<E> {
        int height;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }
    }

}
