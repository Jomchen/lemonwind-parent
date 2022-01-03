package com.jomkie.test.suanfa;

import java.util.Arrays;

/**
 * 排序算法
 */
public class SortTest {
    public static void main(String[] args) {
        // 0,1,2,3,4,5,6,7,8,9
//        int[] source = new int[] { 3, 2, 7, 5, 8, 9, 0, 1, 4, 6 };
        // 0,1,2,2,3,3,4,4,5,5
        int[] source = new int[] { 3, 2, 3, 5, 2, 4, 0, 1, 4, 5 };
        // 6,9,11,20,35,332,440,520,1120
//        int[] source = new int[] {1120, 332, 9, 440, 6, 35, 20, 11, 520};
        test(source);
    }
    public static void test(int[] source) {
        System.out.println("排序前：<<<<<<<<<<<<<<<");
        System.out.println(Arrays.toString(source));
//        maopao(source);
//        xuanze(source);
        charu(source);
        System.out.println("排序后：>>>>>>>>>>>>>>>");
        System.out.println(Arrays.toString(source));
    }

    public static void maopao(int[] source) {
        for (int end = source.length - 1; end > 0; end--) {
            int sortedIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                if (compare(source, begin - 1, begin) > 0) {
                    exchange(source, begin - 1, begin);
                    sortedIndex = begin;
                }
            }
            end = sortedIndex;
        }
    }

    /**
     * 选择排序
     * @param source
     */
    public static void xuanze(int[] source) {
        for (int i = source.length - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (compare(source, i, j) < 0) {
                    exchange(source, i, j);
                }
            }
        }
    }

    /**
     * 插入排序
     * @param source
     */
    public static void charu(int[] source) {
        for (int end = 1; end < source.length; end++) {
            int begin = end;
            int temporary = source[begin];
            while (begin > 0 && compare(source, begin - 1, begin) > 0) {
                exchange(source, begin - 1, begin);
                begin -= 1;
            }
            source[begin] = temporary;
        }
    }

    /**
     * 交换两索引位置的值
     * @param source 数组
     * @param i 索1
     * @param j 索引2
     */
    private static void exchange(int[] source, int i, int j) {
        int temporary = source[i];
        source[i] = source[j];
        source[j] = temporary;
    }

    /**
     * 比较两索引位置数据的大小
     * @param source 数组
     * @param i 索引1
     * @param j 索引2
     */
    private static int compare(int[] source, int i, int j) {
        int result = source[i] - source[j];
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        } else {
            return 0;
        }
    }

}
