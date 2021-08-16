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



}
