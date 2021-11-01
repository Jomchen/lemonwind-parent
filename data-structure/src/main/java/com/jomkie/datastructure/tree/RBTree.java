package com.jomkie.datastructure.tree;

import java.util.Comparator;

public class RBTree<E> extends BBST<E> {

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
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;

        // 如果是根节点或上溢到达了根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack((parent))) { return; }

        // 叔父节点，祖父节点
        Node<E> uncle = parent.sibling();
        Node<E> grand = red(parent.parent);
        // 叔父节点是红色
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            afterAdd(grand);
            return;
        }

        // 叔父节点是黑色
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                black(parent);
            } else { // LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (node.isLeftChild()) { // RL
                black(node);
                rotateRight(parent);
            } else { // RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    @Override
    protected void afterRemove(Node<E> node, Node<E> replacement) {
        // 删除节点是红色
        if (isRed(node)) { return; }

        // 用以取代 node 的子节点是红色
        if (isRed(replacement)) {
            black(replacement);
            return;
        }

        Node<E> parent =node.parent;
        // 删除的是根节点
        if (parent == null) { return; }

        // 删除的是黑色叶子节点[下溢]
        // 判断被删除的node是左还是右（这种判断才是真正正确的）
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左色，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有一个子节点
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent, null);
                }
            } else { // 兄弟节点至少有 1 个子节点
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有一个子节点
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent, null);
                }
            } else { // 兄弟节点至少有 1 个子节点
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    private static class RBNode<E> extends Node<E> {
        boolean color = RED;
        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + element.toString();
        }
    }

}
