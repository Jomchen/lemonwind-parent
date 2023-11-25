package com.lemonwind.test.run;

import com.lemonwind.common.entity.bean.JoUser;
import com.lemonwind.test.run.datastruct.Node;

import java.util.*;

import static java.util.stream.Collectors.*;

import java.util.stream.IntStream;

public class AllTest {

    /** 斐波那契 */
    public static int feibonaqi(int num) {
        // 1-1-2-3-5-8
        // 1-2-3-4-5-6
        if (num < 1) { throw new RuntimeException("序号错误"); }

        int left = 0;
        int right = 1;
        int i = 1;
        while (i < num) {
            int tmp = left + right;
            left = right;
            right = tmp;
            i++;
        }

        return right;
    }

    /** 暴力求索引 */
    public static int indexOf(String text, String pattern) {
        int tlen = text.length();
        int plen = pattern.length();
        int boundIndex = tlen - plen;
        for (int ti = 0; ti <= boundIndex; ti++) {
            int pi = 0;
            while (pi < plen && text.charAt(ti + pi) == pattern.charAt(pi)) {
                pi++;
            }

            if (pi == plen) { return ti; }
        }

        return -1;
    }

    /** KMP 算法 */
    public static int kmp(String text, String pattern) {
        int[] next = next(pattern);
        int tlen = text.length();
        int plen = pattern.length();
        int maxIndex = tlen - plen;
        int pi = 0, ti = 0;
        while (pi < plen && ti <= maxIndex) {
            if (pi < 0 || text.charAt(ti) == pattern.charAt(pi)) {
                ti++;
                pi++;
            } else {
                pi = next[pi];
            }

            if (pi == plen) { return ti - pi; }
        }

        return -1;
    }
    private static int[] next(String pattern) {
        int[] next = new int[pattern.length()];
        int i = 0;
        int n = -1;
        next[i] = n;
        int maxLen = pattern.length() - 1;
        while (i < maxLen) {
            if (n < 0 || pattern.charAt(n) == pattern.charAt(i)) {
                next[++n] = ++i;
            } else {
                n = next[n];
            }
        }

        return next;
    }

    /** 二分搜索 */
    public static int binarySearch(Integer[] array, Integer data) {
        if (array.length <= 1) { throw new RuntimeException("长度异常"); }

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

    /** 反转链表 */
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
            node.setNext(joUserList.get(i +1));
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

    public static int coin(Integer[] faces, Integer money) {
        if (null == faces || faces.length == 0 || money == null || money == 0) {
            throw new RuntimeException("数据异常");
        }
        Integer[] dp = new Integer[money + 1];
        dp[0] = 0;
        for (int current = 1; current <= money; current++) {
            int min = Integer.MAX_VALUE;
            for (int fi = 0; fi < faces.length; fi++) {
                // 零钱比找补值大
                if (faces[fi] > current) { continue; }
                // 选择这个硬币后，不足以找补 | 找补需要的数量比其它找补的数量多
                if (dp[current - faces[fi]] == -1 || dp[current - faces[fi]] >= min) { continue; }
                min = dp[current - faces[fi]] + 1;
            }

            if (min == Integer.MAX_VALUE) {
                dp[current] = -1;
            } else {
                dp[current] = min;
            }
        }

        return dp[money];
    }


    /** 0-1 背包问题 */
    public static Integer packageValue( Integer[] values, Integer[] weights, Integer capacity) {
        if (null == values || weights == null || values.length != weights.length || capacity == 0) { throw new RuntimeException("数据异常"); }

        Integer[][] dp = new Integer[values.length + 1][capacity + 1];

        return null;
    }




    public static void main(String[] args) {
        // 暴力搜索 和 kmp 搜索
        /*String text = "ABCDzxf9XXEXL";
        String pattern = "zxf";
        System.out.println(indexOf(text, pattern));
        System.out.println(kmp(text, pattern));*/

        // 反转链表
        //reverseChain();

        // 二分查询
        //System.out.println(binarySearch(new Integer[]{1,2,3,4,5,6,7,8,9}, -2));

        // 斐波那契
        //System.out.println(feibonaqi(4));

        // 找零算法
        //Integer money = 41; // 不够找补
        //Integer[] faces = {25, 20, 5, 5};
        /*Integer money = 41; // 3 枚
        Integer[] faces = {25, 20, 5, 1};*/
        //System.out.println(coin(faces, money));

        // 0-1 背包问题
        // 15
//        Integer[] values = {6, 3, 5, 4, 6};
//        Integer[] weights = {2, 2, 6, 5, 4};
//        Integer capacity = 10;
//        System.out.println(packageValue(weights, values, capacity));

        /*String text = "abcddefg22k";
        String pattern = "22k";
        System.out.println(indexOf2(text, pattern));
        System.out.println(kmp2(text, pattern));*/

    }

    public static Object indexOf2(String text, String pattern) {
        int tlen = text.length();
        int plen = pattern.length();
        int maxTlen = tlen - plen;
        for (int ti = 0; ti <= maxTlen; ti++) {
            int pi = 0;
            while (pi < plen && text.charAt(ti + pi) == pattern.charAt(pi)) {
                pi++;
            }

            if (pi == plen) { return ti; }
        }
        return  -1;
    }
    public static Object kmp2(String text, String pattern) {
        int[] next = next2(pattern);
        int tlen = text.length();
        int plen = pattern.length();
        int ti = 0, pi = 0;
        while (ti < tlen && pi < plen) {
            if (pi < 0 || pattern.charAt(pi) == text.charAt(ti)) {
                pi++;
                ti++;
            } else {
                pi = next[pi];
            }
        }

        return pi == plen ? ti - pi : -1;
    }
    public static int[] next2(String pattern) {
        int[] next = new int[pattern.length()];
        int left = -1;
        int right = 0;
        next[right] = left;
        int maxIndex = pattern.length() - 1;
        while (right < maxIndex) {
            if (left < 0 || next[left] == next[right]) {
                next[++right] = ++left;
            } else {
                left = next[left];
            }
        }
        return next;
    }
    public static Object erfen(Integer[] array, int capacity) {
        return -1;
    }

}
