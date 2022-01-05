package com.jomkie.test.suanfa;

public class CharSequnceTest {

    public static void main(String[] args) {
        String text = "Abxfqed";
//        String pattern = "fqe";
        String pattern = "qex";
        System.out.println(indexOf(text, pattern));
    }

    public static int indexOf(String text, String pattern) {
        if (null == text || text.length() <= 0 || null == pattern || pattern.length() <= 0
            || text.length() < pattern.length()) {
            throw new IllegalArgumentException("参数不合法");
        }

        int boundLen = text.length() - pattern.length();
        for (int ti = 0; ti < text.length(); ti++) {
            int pi = 0;
            while (pi < pattern.length() && ti - pi <= boundLen) {
                if (text.charAt(ti + pi) == pattern.charAt(pi)) {
                    pi++;
                } else {
                    break;
                }
            }

            if (pi == pattern.length()) {
                return ti;
            }
        }

        return -1;
    }
}
