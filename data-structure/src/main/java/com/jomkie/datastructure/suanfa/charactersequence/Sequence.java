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
//        String text = "Hello World";
//        String pattern = "or";

        // lo W 2
//        String text = "Hello World";
//        String pattern = "lo W";

        // 22k 8
        String text = "abcddefg22k";
        String pattern = "22k";

        // -1
//        String text = "Hello World";
//        String pattern = "ldA";

        // -1
//        String text = "Hello World";
//        String pattern = "xy";
        System.out.println("test00: " + bruteForce2(text, pattern));
        System.out.println("kmp: " + kmp(text, pattern));
        System.out.println("kmpOpetimization: " + kmpOpetimization(text, pattern));
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
        while (pi < plen && ti - pi <= lenDelta) { // 右边一个条件是为了判断 text 如果要对比完 pattern 会不会越界；当前正在匹配的字符串的开始索引不能超过临界值
            // ti - pi 是指每一轮比较中 text 首个比较字符的位置
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
    public static int bruteForce2(String text, String pattern) {
        if (checkStr(text, pattern)) { return -1; }

        int tlen = text.length();
        int plen = pattern.length();
        int tiMax = tlen - plen;
        for (int ti = 0; ti <= tiMax; ti++) {
            int pi = 0;
            for (; pi < plen; pi++) {
                if (text.charAt(ti + pi) != pattern.charAt(pi)) {
                    break;
                }
            }
            if (pi == plen) { return ti; }
        }

        return -1;
    }

    /** KMP */
    public static int kmp(String text, String pattern) {
        if (checkStr(text, pattern)) { return -1; }

        int tlen = text.length();
        int plen = pattern.length();
        int[] next = next(pattern);
        int pi = 0, ti = 0, lenDelta = tlen - plen;
//        while (pi < plen && ti <= lenDelta) {
        // TODO 为什么不能有  && ti <= lenDelta
        while (pi < plen) {
            if (pi < 0 || text.charAt(ti) == pattern.charAt(pi)) {
                ti++;
                pi++;
            } else {
                pi = next[pi];
            }
        }

        return pi == plen ? (ti - pi) : -1;
    }

    private static int[] next(String pattern) {
        int len = pattern.length();
        char[] chars = pattern.toCharArray();
        int[] next = new int[chars.length];

        int i = 0;
        int n = next[i] = -1;
        int imax = len - 1;
        while (i < imax) {
            if (n < 0 || pattern.charAt(i) == chars[n]) {
                next[++i] = ++n;
            } else {
                n = next[n];
            }
        }

        return next;
    }


    /** KMP 优化 */
    public static int kmpOpetimization(String text, String pattern) {
        if (checkStr(text, pattern)) { return -1; }

        int tlen = text.length();
        int plen = pattern.length();
        int[] next = nextOpetimization(pattern);
        int pi = 0, ti = 0, lenDelta = tlen - plen;
//        while (pi < plen && ti <= lenDelta) {
        // TODO 为什么不能有  && ti <= lenDelta
        while (pi < plen) {
            if (pi < 0 || text.charAt(ti) == pattern.charAt(pi)) {
                ti++;
                pi++;
            } else {
                pi = next[pi];
            }
        }

        return pi == plen ? (ti - pi) : -1;
    }

    /** next 优化 */
    private static int[] nextOpetimization(String pattern) {
        int len = pattern.length();
        char[] chars = pattern.toCharArray();
        int[] next = new int[chars.length];

        int i = 0;
        int n = next[i] = -1;
        int imax = len - 1;
        while (i < imax) {
            if (n < 0 || pattern.charAt(i) == chars[n]) {
                i++;
                n++;
                if (pattern.charAt(i) == pattern.charAt(n)) {
                    next[i] = next[n];
                } else {
                    next[i] = n;
                }
            } else {
                n = next[n];
            }
        }

        return next;
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
