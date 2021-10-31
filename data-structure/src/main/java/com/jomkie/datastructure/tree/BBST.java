package com.jomkie.datastructure.tree;

import java.util.Comparator;

public class BBST<E> extends BST<E> {

    public BBST() {
        this(null);
    }

    public BBST(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 左旋转
     * @param grand
     */
    protected void rotateLeft(Node<E> grand) {
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
    protected void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
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
    /*protected void rotate(
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

        // e-f-g
        f.left = e;
        if (null != e) { e.parent = f; }
        f.right = g;
        if (null != g) { g.parent = f; }

        // b-d-f
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;
    }*/

    /**
     * 综合旋转方法，a 和 g 已经省略，但是此只适用于AVL树和红黑树
     * 如果要适用于其它类型的树，则应该使用上面被注掉的完全方法
     * @param r 根节点
     * @param b
     * @param c
     * @param d
     * @param e
     * @param f
     */
    protected void rotate(Node<E> r, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f) {
        // 让 d 成为子树的根节点
        d.parent = r.parent;
        if (r.isLeftChild()) {
            r.parent.left = d;
        } else if (r.isRightChild()) {
            r.parent.right = d;
        } else {
            root = d;
        }

        // b-c
        b.right = c;
        if (null != c) { c.parent = b; }

        // e-f
        f.left = e;
        if (null != e) { e.parent = f; }

        // b-d-f
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;
    }

}
