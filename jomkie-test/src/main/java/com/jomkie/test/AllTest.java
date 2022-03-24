package com.jomkie.test;

import com.jomkie.common.entity.bean.JoUser;
import com.jomkie.test.datastruct.Node;

import java.util.*;

import static java.util.stream.Collectors.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AllTest {

    public static void main(String[] args) {
        /*String text = "ABCDzxf9XXEXL";
        String pattern = "zxf";
        System.out.println(indexOf(text, pattern));
        System.out.println(kmp(text, pattern));*/

//        reverseChain();

        System.out.println(binarySearch(new Integer[]{1,2,3,4,5,6,7,8,9}, -2));
        System.out.println(feibonaqi(4));
    }


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


    public static void test00(String[] args) {
        Optional<Integer> sumData = IntStream.range(0, 10).boxed().reduce(Integer::sum);
        Integer sumData2 = IntStream.range(0, 10).boxed().reduce(0, Integer::sum);
        Optional<Integer> maxData = IntStream.range(0, 10).boxed().reduce(Integer::max);
        Integer totalAge = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(Collectors.reducing(0, JoUser::getAge, Integer::sum));

        // 年龄分组，取得组中ID最大的对象
        Map<Integer, Optional<JoUser>> maxIdMap = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(groupingBy(JoUser::getAge, maxBy(Comparator.comparing(JoUser::getId))));

        // 年龄分组，取得组中ID最大的对象
        Map<Integer, JoUser> maxIdMap2 = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(
                        groupingBy(
                                JoUser::getAge,
                                collectingAndThen(maxBy(Comparator.comparing(JoUser::getId)), Optional::get)
                        )
                );

        // 年龄分组，结果映射为ID并收集为集合
        Map<Integer, List<Integer>> sumIdMap = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(
                        groupingBy(JoUser::getAge, mapping(user -> user.getId(), toList()))
                );

        // 分区，分区组的 KEY 是布尔型
        Map<Boolean, List<JoUser>> partitionMap = Stream.iterate(0, x -> x + 1).limit(10)
                .map(i -> new JoUser(i, String.valueOf(i), i % 3, "北京" + i, new Date()))
                .collect(partitioningBy(user -> user.getId() > 5, toList()));
        
    }


}
