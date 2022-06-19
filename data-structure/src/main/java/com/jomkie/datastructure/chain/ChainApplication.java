package com.jomkie.datastructure.chain;

import com.jomkie.common.util.chain.Node;

public class ChainApplication {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        // 构建普通链表
        /*Node<Integer> bean6 = new Node<>(6, null);
        Node<Integer> bean5 = new Node<>(5, bean6);
        Node<Integer> bean4 = new Node<>(4, bean5);
        Node<Integer> bean3 = new Node<>(3, bean4);
        Node<Integer> bean2 = new Node<>(2, bean3);
        Node<Integer> bean1 = new Node<>(1, bean2);
        Node<Integer> bean0 = new Node<>(0, bean1);*/

        // 构建环型链表
        Node<Integer> bean6 = new Node<>(6, null);
        Node<Integer> bean5 = new Node<>(5, bean6);
        Node<Integer> bean4 = new Node<>(4, bean5);
        Node<Integer> bean3 = new Node<>(3, bean4);
        Node<Integer> bean2 = new Node<>(2, bean3);
        Node<Integer> bean1 = new Node<>(1, bean2);
        Node<Integer> bean0 = new Node<>(0, bean1);
        bean6.setNext(bean3);
        

        /*printDataNode(reverse(bean0));*/
        /*printDataNode(reverse2(bean0));*/
        System.out.println(findRing(bean0));
    }

    /**
     * 反转链表，递归版
     */
    public static <T> Node<T> reverse(Node<T> head) {
        if (null == head || head.getNext() == null) { return head; }
        Node<T> reverseHead = reverse(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);
        return reverseHead;
    }

    /**
     * 反转链表，非递归版
     */
    public static <T> Node<T> reverse2(Node<T> head) {
        if (null == head || head.getNext() == null) { return head; }

        Node<T> slow = null;
        Node<T> fast = head;
        while (fast != null) {
            Node<T> oldNext = fast.getNext();
            fast.setNext(slow);
            slow = fast;
            fast = oldNext;
        }

        return slow;
    }

    /**
     * 检查环型链表结点
     * @author Jomkie
     * @since 2021-07-04 20:34:47
     * @param dataNode
     * @return 环型点
     */
    public static <T> Node<T> findRing(Node<T> dataNode) {
        if (null == dataNode || dataNode.getNext() == null) { return null; }

        Node<T> slow = dataNode;
        Node<T> fast = dataNode.getNext();
        while (fast != null && fast.getNext() != null) {
            if (slow.equals(fast)) { return slow; }
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }

        return null;
    }

    /**
     * 打印链表
     */
    public static <T> void printDataNode(Node<T> dataNode) {
        if (null == dataNode) { return; }
        while (dataNode != null) {
            System.out.print(dataNode.getData());
            if (dataNode.getNext() != null) { System.out.print("->"); }
            dataNode = dataNode.getNext();
        }

        System.out.println();
    }

}
