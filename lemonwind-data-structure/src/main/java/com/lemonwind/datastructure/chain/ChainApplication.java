package com.lemonwind.datastructure.chain;

import com.lemonwind.common.util.chain.NodeChain;

public class ChainApplication {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        // 构建普通链表
        /*NodeChain<Integer> bean6 = new NodeChain<>(6, null);
        NodeChain<Integer> bean5 = new NodeChain<>(5, bean6);
        NodeChain<Integer> bean4 = new NodeChain<>(4, bean5);
        NodeChain<Integer> bean3 = new NodeChain<>(3, bean4);
        NodeChain<Integer> bean2 = new NodeChain<>(2, bean3);
        NodeChain<Integer> bean1 = new NodeChain<>(1, bean2);
        NodeChain<Integer> bean0 = new NodeChain<>(0, bean1);*/

        // 构建环型链表
        NodeChain<Integer> bean6 = new NodeChain<>(6, null);
        NodeChain<Integer> bean5 = new NodeChain<>(5, bean6);
        NodeChain<Integer> bean4 = new NodeChain<>(4, bean5);
        NodeChain<Integer> bean3 = new NodeChain<>(3, bean4);
        NodeChain<Integer> bean2 = new NodeChain<>(2, bean3);
        NodeChain<Integer> bean1 = new NodeChain<>(1, bean2);
        NodeChain<Integer> bean0 = new NodeChain<>(0, bean1);
        bean6.setNext(bean3);
        

        /*printDataNode(reverse(bean0));*/
        /*printDataNode(reverse2(bean0));*/
        System.out.println(findRing(bean0));
    }

    /**
     * 反转链表，递归版
     * @param <T>
     * @param head
     * @return 
     */
    public static <T> NodeChain<T> reverse(NodeChain<T> head) {
        if (null == head || head.getNext() == null) { return head; 
        }
        NodeChain<T> reverseHead = reverse(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);
        return reverseHead;
    }

    /**
     * 反转链表，非递归版
     * @param <T>
     * @param head
     * @return 
     */
    public static <T> NodeChain<T> reverse2(NodeChain<T> head) {
        if (null == head || head.getNext() == null) { return head; }

        NodeChain<T> slow = null;
        NodeChain<T> fast = head;
        while (fast != null) {
            NodeChain<T> oldNext = fast.getNext();
            fast.setNext(slow);
            slow = fast;
            fast = oldNext;
        }

        return slow;
    }

    /**
     * 检查环型链表结点（循环链表）
     * @author lemonwind
     * @param <T>
     * @since 2021-07-04 20:34:47
     * @param dataNode 起始结点
     * @return 环型点
     */
    public static <T> NodeChain<T> findRing(NodeChain<T> dataNode) {
        if (null == dataNode || dataNode.getNext() == null) { return null; }

        NodeChain<T> slow = dataNode;
        NodeChain<T> fast = dataNode.getNext();
        while (fast != null && fast.getNext() != null) {
            if (slow.equals(fast)) { return slow; }
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }

        return null;
    }

    /**
     * 打印链表
     * @param <T>
     * @param dataNode
     */
    public static <T> void printDataNode(NodeChain<T> dataNode) {
        if (null == dataNode) { return; }
        while (dataNode != null) {
            System.out.print(dataNode.getData());
            if (dataNode.getNext() != null) { System.out.print("->"); }
            dataNode = dataNode.getNext();
        }

        System.out.println();
    }

}
