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

    private void rl(Node<E> node)  {

    }

    @Override
    protected void afterAdd(Node<E> node) {
    	while ((node = node.parent) != null) {
    		if (isBalance(node)) {
    			// 更新高度
    			updateHeight(node);
    		} else {
    			// 恢复平衡
    		}
    	}
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
    	return new AVLNode<>(element, parent);
    }
    
    private boolean isBalance(Node<E> node) {
    	AVLNode<E> avlNode = (AVLNode<E>) node;
    	int balance = avlNode.balanceFactor();
    	return Math.abs(balance) <= 1;
    }
    
    private void updateHeight(Node<E> node) {
    	AVLNode<E> avlNode = (AVLNode<E>) node;
    	avlNode.updateHeight();
    }
    
    private static class AVLNode<E> extends Node<E> {
        int height = 1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }
     
        public int balanceFactor() {
        	int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
        	int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
        	return leftHeight - rightHeight;
        }
        
        public void updateHeight() {
        	int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
        	int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
        	height = 1 + Math.max(leftHeight, rightHeight);
        }
        
    }

}
