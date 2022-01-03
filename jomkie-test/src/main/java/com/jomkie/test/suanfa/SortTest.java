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
//        charu(source);
//        guibing(source);
        kuaipai(source);
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

    public static void guibing(int[] source) {
        int[] temporary = new int[source.length];
        guibing(source, 0, source.length, temporary);
    }
    public static void guibing(int[] source, int begin, int end, int[] temporary) {
        if ((end - begin) < 2) { return; }
        int mid = (begin + end) >> 1;
        guibing(source, begin, mid, temporary);
        guibing(source, mid, end, temporary);
        merge(source, begin, mid, end, temporary);
    }
    private static void merge(int[] source, int begin, int mid, int end, int[] temporary) {
        int firstIndex = begin;
        int secondIndex = mid;
        int temporaryIndex = begin;
        while (true) {
            if (firstIndex < mid && secondIndex < end) {
                if (compare(source, firstIndex, secondIndex) <= 0) {
                    temporary[temporaryIndex] = source[firstIndex];
                    ++firstIndex;
                    ++temporaryIndex;
                    continue;
                } else {
                    temporary[temporaryIndex] = source[secondIndex];
                    ++secondIndex;
                    ++temporaryIndex;
                    continue;
                }
            }

            while (firstIndex < mid) {
                temporary[temporaryIndex] = source[firstIndex];
                ++firstIndex;
                ++temporaryIndex;
            }
            while (secondIndex < end) {
                temporary[temporaryIndex] = source[secondIndex];
                ++secondIndex;
                ++temporaryIndex;
            }

            break;
        }

        for (int i = begin; i < end; i++) {
            source[i] = temporary[i];
        }
    }

    public static void kuaipai(int[] source) {
        kuaipai(source, 0, source.length);
    }
    public static void kuaipai(int[] source, int begin, int end) {
        if ((end - begin) < 2) { return; }
        int tem = kuaipaiTool(source, begin, end);
        kuaipai(source, begin, tem);
        kuaipai(source, tem + 1, end);
    }
    public static int kuaipaiTool(int[] source, int begin, int end) {
        int tem = (end + begin) >> 1;
        exchange(source, begin, tem);

        int lastData = source[begin];
        end--;

        while (begin < end) {
            while (begin < end) {
                if (source[end] > lastData) {
                    end--;
                } else {
                    source[begin] = source[end];
                    begin++;
                    break;
                }
            }

            while (begin < end) {
                if (source[begin] < lastData) {
                    begin++;
                } else {
                    source[end] = source[begin];
                    end--;
                    break;
                }
            }

            source[begin] = lastData;
        }

        return begin;
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
