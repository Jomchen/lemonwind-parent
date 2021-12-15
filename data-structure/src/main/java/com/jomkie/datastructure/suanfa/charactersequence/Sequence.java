package com.jomkie.datastructure.suanfa.charactersequence;

/**
 * 串
 * 比较著名的匹配算法有：
 *     Brute Force 蛮力算法
 *     KMP
 *     Boyer-Moore
 *     Rabin-Karp
 *     Sunday
 */
public class Sequence {

    public static void main(String[] args) {
        // 7
        String text = "Hello World";
        String pattern = "or";

        // -1
//        String text = "Hello World";
//        String pattern = "ldA";

        // -1
//        String text = "Hello World";
//        String pattern = "xy";
        System.out.println("test00: " + bruteForce(text, pattern));
    }

    /**
     * 蛮力算法
     * @param text 文本串
     * @param pattern 模式串
     */
    public static int bruteForce (String text, String pattern) {
        if (checkStr(text, pattern)) { return -1; }

        int tlen = text.length();
        int plen = pattern.length();
        int pi = 0, ti = 0, lenDelta = tlen - plen;
        while (pi < plen && ti - pi <= lenDelta) { // 右边一个条件是为了判断 text 如果要对比完 pattern 会不会越界
            if (text.charAt(ti) == pattern.charAt(pi)) {
                pi++;
                ti++;
            } else {
                ti -= pi - 1;
                pi = 0;
            }
        }

        return pi == pattern.length() ? ti - pi : -1;
    }

    /** 优化，pi 增加时，ti不增加，利用 ti+pi 得到 text 对应的字符进行比较 */
    public static int bruteForce2 (String text, String pattern) {
        return -1;
    }

    private static boolean checkStr(String text, String pattern) {
        if (null == text || text.length() == 0
                || null == pattern || pattern.length() == 0
                || text.length() < pattern.length()) {
            return true;
        }

        return false;
    }

}
