package com.jomkie.test.suanfa;

public class CharSequnceTest {

    public static void main(String[] args) {
        String text = "Abxfqed";
        String pattern = "fqe";
//        String pattern = "qex";
        System.out.println(indexOf(text, pattern));
        System.out.println(kmp(text, pattern));
    }

    public static int indexOf(String text, String pattern) {
    	check(text, pattern);

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
    
    public static int kmp(String text, String pattern) {
    	check(text, pattern);
    	int[] next = next(pattern);
    	int tlen = text.length();
    	int plen = pattern.length();
    	int cmpLen = tlen - plen;
    	int pi = 0, ti = 0;
    	while (pi < plen && ti <= cmpLen) {
    		if (pi < 0 || text.charAt(ti) == pattern.charAt(pi)) {
    			pi++;
    			ti++;
    		} else {
    			pi = next[pi];
    		}
    	}
    	
    	return pi == plen ? ti - pi : -1;
    }

    public static int[] next(String pattern) {
    	int[] next = new int[pattern.length()];
    	if (null == pattern || pattern.length() == 0) {
    		throw new IllegalAccessError("模式串非法");
    	}
    	
    	int i = 0;
    	int n = next[i] = -1;
    	int condition = pattern.length() - 1;
    	while (i < condition) {
    		if (n < 0 || pattern.charAt(i) == pattern.charAt(n)) {
    			next[++i] = ++n;
    		} else {
    			n = next[n];
    		}
    	}
    	
    	return next;
    }
    
    public static void check(String text, String pattern) {
    	if (null == text || text.length() <= 0 || null == pattern || pattern.length() <= 0
                || text.length() < pattern.length()) {
                throw new IllegalArgumentException("参数不合法");
        }    	
    }
    
}
