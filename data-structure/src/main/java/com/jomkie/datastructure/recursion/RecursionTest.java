package com.jomkie.datastructure.recursion;

public class RecursionTest {

    public static void main(String[] args) {
        System.out.println(test00(4));
    }

    /** 斐波那契数列 */
    public static int test00(int n) {
        // 1, 1, 2, 3, 5, 8, 13, 21, 34, ...
        if (n == 1 || n == 2) {
            return 1;
        } else {
            return test00(n - 1) + test00(n - 2);
        }
    }

    /** 测试 */
    public static void test01() {

    }

}
