package com.jomkie.datastructure.test;

public class TestDataStructure {

    public static void main(String[] args) {
//        String text = "Hello World";
//        String pattern = "or";
//        String text2 = "Hello World";
//        String pattern2 = "lo W";
//        String text3 = "Hello World";
//        String pattern3 = "lo J";
//        System.out.println("暴力匹配 案例1：" + match(pattern, text));
//        System.out.println("KMP匹配 案例1：" + kmp(pattern, text));
//        System.out.println("暴力匹配 案例2：" + match(pattern2, text2));
//        System.out.println("KMP匹配 案例2：" + kmp(pattern2, text2));
//        System.out.println("暴力匹配 案例3：" + match(pattern3, text3));
//        System.out.println("KMP匹配 案例3：" + kmp(pattern3, text3));

//        int[] data = new int[]{1120, 332, 9, 440, 6, 35, 20, 11, 520};
        int[] data = new int[]{1120, 332, 9, 332, 440, 6, 35, 20, 11, 520};
        printer(data, "排序前");
//        bubbuleSort(data);
//        selectedSort(data);
//        insertSort(data);
        quickSort(data);
//        heapSort(data);
//        mergeSort(data);
        printer(data, "排序后");

        System.out.println("\nLinux is a best operating system");
    }

    /** 冒泡排序 */
    public static void bubbuleSort(int[] data) {
        System.out.println("冒泡排序执行中 --------------------------");
        int len = data.length;
        for (int end = data.length - 1; end > 0; end--) {
            for (int i = 1; i <=end; i++) {
                if (cmp(data, i, i - 1) < 0) {
                    swap(data, i, i - 1);
                }
            }
        }
    }

    /** 选择排序 */
    public static void selectedSort(int[] data) {
        System.out.println("选择排序 --------------------------");
        int len = data.length;
        for (int end = len - 1; end > 0; end--) {
            int max = end;
            for (int i = 0; i < end; i++) {
                if (cmp(data, i, max) > 0) {
                    max = i;
                }
            }
            swap(data, max, end);
        }
    }

    /** 插入排序 */
    public static void insertSort(int[] data) {
        System.out.println("插入排序 --------------------------");
        int len = data.length;
        for (int end = 1; end < len; end++) {
            for (int i = end; i > 0; i--) {
                if (cmp(data, i, i - 1) < 0) {
                    swap(data, i, i - 1);
                } else {
                    break;
                }
            }
        }
    }
    
    public static void quickSort(int[] data) {
        System.out.println("快速排序 --------------------------");
        quickSort(data, 0, data.length);
    }
    public static void quickSort(int[] data, int begin, int end) {
        if (end - begin < 2) { return; }
        int mid = quickSortTool(data, begin, end);
        quickSort(data, begin, mid);
        quickSort(data, mid + 1, end);
    }
    public static int quickSortTool(int[] data, int begin, int end) {
        int temVal = data[begin];
        end--;// 为什么这里要作减

        while (begin < end) {
           while (begin < end) {
               if (data[end] - temVal > 0) {
                   end--;
               } else {
                   data[begin++] = data[end];
                   break;
               }
           }

           while (begin < end) {
               if (data[begin] - temVal < 0) {
                   begin++;
               } else {
                   data[end--] = data[begin];
                   break;
               }
           }
        }

       data[begin] = temVal;
       return begin;
    }

    /** 分治排序 */
    public static void mergeSort(int[] data) {
        System.out.println("分治排序 --------------------------");
        int[] temArray = new int[data.length];
        mergeSort(data, 0, data.length, temArray);
    }
    public static void mergeSort(int[] data, int begin, int end, int[] temArray) {
        if (end - begin < 2) { return; }
        int mid = (end + begin) >> 1;
        mergeSort(data, begin, mid, temArray);
        mergeSort(data, mid, end, temArray);
        mergeSortTool(data, begin ,mid, end, temArray);
    }
    public static void mergeSortTool(int[] data, int begin, int mid, int end, int[] temArray) {
        int i = begin;
        int j = mid;
        int index = begin;
        while (i < mid && j < end) {
            if (data[i] < data[j]) {
                temArray[index] = data[i];
                index++;
                i++;
            } else {
                temArray[index] = data[j];
                index++;
                j++;
            }
        }

        while (i < mid) {
            temArray[index] = data[i];
            index++;
            i++;
        }
        while (j < end) {
            temArray[index] = data[j];
            index++;
            j++;
        }

        while (begin < end) {
            data[begin] = temArray[begin];
            begin++;
        }
    }

    /** 堆排序 */
    public static void heapSort(int[] data) {
        System.out.println("堆排序 --------------------------");
        int len = data.length;
        for (int i = (len >> 1) - 1; i >= 0; i--) {
            siftDown(data, i, len);
        }

        while (len > 1) {
            swap(data, 0, --len);
            siftDown(data, 0, len);
        }
    }
    public static void siftDown(int[] data, int index, int heapSize) {
        int originalVal = data[index];
        int half = heapSize >> 1;
        while (index < half) {
            int childIndex = (index << 1) + 1;
            int rightIndex = childIndex + 1;
            if (rightIndex < heapSize && data[childIndex] - data[rightIndex] < 0) {
                childIndex = rightIndex;
            }

            if (originalVal - data[childIndex] >= 0) {
                break;
            }

            data[index] = data[childIndex];
            index = childIndex;
        }

        data[index] = originalVal;
    }

    /** 暴力匹配 */
    public static int match(String pattern, String text) {
        int tlen = text.length();
        int plen = pattern.length();
        int maxTlen = tlen - plen;
        for (int ti = 0; ti <= maxTlen; ti++) {
            int pi = 0;
            while (pi < plen && pattern.charAt(pi) == text.charAt(pi + ti)) {
                pi++;
            }

            if (pi == plen) { return ti; }
        }
        return -1;
    }

    /** KMP 匹配 */
    public static int kmp(String pattern, String text) {
        int plen = pattern.length();
        int tlen = text.length();
        int[] next = next(pattern);
        int pi = 0;
        int ti = 0;
        while (pi < plen && ti < tlen) {
            if (pi < 0 || pattern.charAt(pi) == text.charAt(ti)) {
                ti++;
                pi++;
            } else {
                pi = next[pi];
            }
        }

        return pi == plen ? ti - pi : -1;
    }

    private static int[] next(String pattern) {
        int[] next = new int[pattern.length()];
        int right = 0;
        int left = -1;
        next[right] = left;
        int max = pattern.length() - 1;
        while (right < max) {
            if (left < 0 || pattern.charAt(left) == pattern.charAt(right)) {
                next[++right] = ++left;
            } else {
                left = next[left];
            }
        }

        return next;
    }

    public static int cmp(int[] data, int i, int j) {
        return data[i] - data[j];
    }
    public static void swap(int[] data, int i, int j) {
        int t = data[i];
        data[i] = data[j];
        data[j] = t;
    }
    public static void printer(int[] data, String arg) {
        System.out.println(arg);
        for (int i = 0; i < data.length; i++) {
            if (i == data.length - 1) {
                System.out.print(data[i]);
            } else {
                System.out.print(data[i]);
                System.out.print(" ");
            }
        }
        System.out.println();
    }

}
