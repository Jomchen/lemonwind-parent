package com.lemonwind.datastructure.suanfa;

import com.lemonwind.common.entity.bean.User;
import com.lemonwind.common.util.chain.NodeChain;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @author lemonwind
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
        List<NodeChain<User>> joUserList = LongStream.range(0, 10).boxed().map(e -> {
            User user = new User();
            user.setId(e);
            user.setName("李寻欢" + e);
            NodeChain<User> node = new NodeChain<>();
            node.setData(user);
            return node;
        }).collect(toList());
        for (int i = 0; i < joUserList.size() - 1; i++) {
            NodeChain<User> node = joUserList.get(i);
            node.setNext(joUserList.get(i + 1));
        }

        NodeChain<User> firstNode = joUserList.get(0);
        NodeChain<User> lastNode = joUserList.get(joUserList.size() - 1);
        printChain(firstNode);
        NodeChain<User> node = joUserList.get(0);
        NodeChain<User> left = node;
        NodeChain<User> right = null;
        while (left != null) {
            NodeChain<User> next = left.getNext();
            left.setNext(right);
            right = left;
            left = next;
        }
        printChain(lastNode);
    }
    private static void printChain(NodeChain<User> node) {
        while (node != null) {
            System.out.println(node);
            node = node.getNext();
        }
    }
    
    /**
     * 楼梯有 n 阶台阶，每次可以上 1 阶 或 2 阶，问走完 n 阶台阶共多少种走法 
     * 假设一共有 f(n) 种走法，第 1 步有 2 种走法 
     * 如果上 1 阶，那就剩 n-1 阶，共 f(n-1)种走法 
     * 如果上 2 阶，那就剩 n-2 阶，共 f(n-2)种走法 
     * 所以 f(n) = f(n-1) + f(n-2)
     */
    public static int climbStairs(int n) {
        // 楼梯有 n 阶台阶，上楼可以一步上 1 阶，也可以一步上 2 阶，走完 n 阶台阶共有多少种不同的走法
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        // 因为一步只上 1 阶 或 2 阶，所以总共可能的走法应该是两种情况各自的走法相加
        return climbStairs(n - 1) + climbStairs(n - 2);
    }
    
}
