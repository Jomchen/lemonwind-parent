package com.jomkie.datastructure.tree;

import java.util.Comparator;

public class RBTree<E> extends Bst<E> {

    private static final boolean BLACK = true;
    private static final boolean RED = false;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    /** 染色 */
    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return node;
        ((RBNode<E>)node).color = color;
        return node;
    }

    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<E> node) {
        return null == node ? BLACK : ((RBNode<E>)node).color;
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    @Override
    protected void afterAdd(Node<E> node) {
        super.afterAdd(node);
    }

    @Override
    protected void afterRemove(Node<E> node) {
        super.afterRemove(node);
    }

    private static class RBNode<E> extends Node<E> {
        boolean color;
        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }
    }

}
