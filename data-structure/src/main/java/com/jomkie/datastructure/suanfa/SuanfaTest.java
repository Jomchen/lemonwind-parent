package com.jomkie.datastructure.suanfa;

import com.jomkie.common.entity.bean.JoUser;
import com.jomkie.common.util.chain.Node;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.IntStream;

/**
 * @author jomkie
 */
public class SuanfaTest {
    
    /**
     * 二分搜索
     */
    public static int binarySearch(Integer[] array, Integer data) {
        if (array.length <= 1) {
            throw new RuntimeException("长度异常");
        }

        int left = 0;
        int right = array.length;
        while (left < right) {
            int mid = (left + right) >> 1;
            if (array[mid].equals(data)) {
                return mid;
            } else if (data < array[mid]) {
                right = mid;
            } else if (data > array[mid]) {
                left = mid + 1;
            }
        }

        return -1;
    }


    /**
     * 反转链表
     */
    public static void reverseChain() {
        List<Node<JoUser>> joUserList = IntStream.range(0, 10).boxed().map(e -> {
            JoUser joUser = new JoUser();
            joUser.setId(e);
            joUser.setName("李寻欢" + e);
            Node<JoUser> node = new Node<>();
            node.setData(joUser);
            return node;
        }).collect(toList());
        for (int i = 0; i < joUserList.size() - 1; i++) {
            Node<JoUser> node = joUserList.get(i);
            node.setNext(joUserList.get(i + 1));
        }

        Node<JoUser> firstNode = joUserList.get(0);
        Node<JoUser> lastNode = joUserList.get(joUserList.size() - 1);
        printChain(firstNode);
        Node<JoUser> node = joUserList.get(0);
        Node<JoUser> left = node;
        Node<JoUser> right = null;
        while (left != null) {
            Node<JoUser> next = left.getNext();
            left.setNext(right);
            right = left;
            left = next;
        }
        printChain(lastNode);
    }
    private static void printChain(Node<JoUser> node) {
        while (node != null) {
            System.out.println(node);
            node = node.getNext();
        }
    }
    
}
