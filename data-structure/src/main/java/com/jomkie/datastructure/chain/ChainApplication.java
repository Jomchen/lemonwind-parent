package com.jomkie.datastructure.chain;

import com.jomkie.datastructure.model.chain.DataNode;

public class ChainApplication {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        /*DataNode bean6 = new DataNode(6, null);
        DataNode bean5 = new DataNode(5, bean6);
        DataNode bean4 = new DataNode(4, bean5);
        DataNode bean3 = new DataNode(3, bean4);
        DataNode bean2 = new DataNode(2, bean3);
        DataNode bean1 = new DataNode(1, bean2);
        DataNode bean0 = new DataNode(0, bean1);*/

        DataNode bean6 = new DataNode(6);
        DataNode bean5 = new DataNode(5);
        DataNode bean4 = new DataNode(4);
        DataNode bean3 = new DataNode(3);
        DataNode bean2 = new DataNode(2);
        DataNode bean1 = new DataNode(1);
        DataNode bean0 = new DataNode(0);

        bean0.next = bean1;
        bean1.next = bean2;
        bean2.next = bean3;
        bean3.next = bean4;
        bean4.next = bean5;
        bean5.next = bean6;
        bean6.next = bean3;

        /*printDataNode(reverse(bean6));*/
        /*printDataNode(reverse2(bean6));*/
        System.out.println(findRing(bean0));
    }

    /**
     * 反转链表，递归版
     */
    public static DataNode reverse(DataNode head) {
        if (null == head || head.next == null) { return head; }
        DataNode reverseHead = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return reverseHead;
    }

    /**
     * 反转链表，非递归版
     */
    public static DataNode reverse2(DataNode head) {
        if (null == head || head.next == null) { return head; }

        DataNode slow = null;
        DataNode fast = head;
        while (fast != null) {
            DataNode oldNext = fast.next;
            fast.next = slow;
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
    public static DataNode findRing(DataNode dataNode) {
        if (null == dataNode || dataNode.next == null) { return null; }

        DataNode slow = dataNode;
        DataNode fast = dataNode.next;
        while (fast != null && fast.next != null) {
            if (slow.equals(fast)) { return slow; }
            slow = slow.next;
            fast = fast.next.next;
        }

        return null;
    }

    /**
     * 打印链表
     */
    public static void printDataNode(DataNode dataNode) {
        if (null == dataNode) { return; }
        while (dataNode != null) {
            System.out.print(dataNode.data);
            if (dataNode.next != null) { System.out.print("->"); }
            dataNode = dataNode.next;
        }

        System.out.println();
    }

}
