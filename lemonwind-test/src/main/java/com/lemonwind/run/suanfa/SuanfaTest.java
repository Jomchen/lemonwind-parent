package com.lemonwind.run.suanfa;

import java.util.Arrays;

public class SuanfaTest {

    public static void main(String[] args) {
        Object data;

        // 0-1 背包问题
//        data = "背包问题，最大价值为：" + beibao();
        // 零钱找补问题
//        data = "零钱找补，最小找补硬币数为：" + lingqian();
        // 最大连续子序列和
//        data = "最大连续子序列和为：" + mls();
        // 最长上升子序列长度
//        data = "最长上升子序列长度为：" + lis();
        // 最长公共子序列长度
//        data = "最长公共子序列长度为：" + lcs();
        // 最长公共子串
        data = "最长公共子串长度为：" + lcss();
        System.out.println(data);
    }

    /**
     * 零钱找补问题
     * @return
     */
    public static int lingqian() {
        // 结果 -1
//        int money = 41;
//        int[] faces = {25, 20, 5, 5};

        // 结果 3
        int money = 41;
        int[] faces = {25, 20, 5, 1};
        return lingqian(faces, money);
    }
    public static int lingqian(int[] faces, int capacity) {
        if (null == faces || faces.length == 0 || capacity <= 0) { return 0; }
        int[] dp = new int[capacity + 1];
        for (int money = 1; money <= capacity; money++) {
            int minCount = Integer.MAX_VALUE;
            for (int i = 0; i < faces.length; i++) {
                if (faces[i] > money) { continue; }
                int temporary = dp[money - faces[i]];
                if (temporary == -1 || temporary >= minCount) { continue; }

                minCount = temporary;
            }

            if (minCount == Integer.MAX_VALUE) {
                dp[money] = -1;
            } else {
                dp[money] = minCount + 1;
            }
        }

        return dp[capacity];
    }

    /**
     * 0-1 背包问题
     * @return
     */
    public static int beibao() {
        // 结果 15
        int[] values = {6, 3, 5, 4, 6};
        int[] weights = {2, 2, 6, 5, 4};
        int capacity = 10;
        return beibao(values, weights, capacity);
    }
    private static int beibao(int[] values, int[] weights, int capacity) {
        if (null == values || values.length == 0
                || null == weights || weights.length == 0
                || values.length != weights.length
                || capacity <= 0) {
            return -1;
        }

        // i 为有前 i 件物品；j 为承重为 j 时
        int[][] dp = new int[values.length + 1][capacity + 1];
        for (int zhong = 1; zhong <= capacity; zhong++) {
            for (int i = 1; i <= values.length; i++) {
                if (zhong < weights[i - 1]) {
                    dp[i][zhong] = 0;
                } else {
                    int worthFirst = dp[i - 1][zhong];
                    int worthSecond = values[i - 1] + dp[i - 1][zhong - weights[i - 1]];
                    dp[i][zhong] = Math.max(worthFirst, worthSecond);
                }
            }
        }

        return dp[values.length][capacity];
    }

    /**
     * 最大连续子序列和
     * @return
     */
    public static int mls() {
        // 4,-1,2,1 结果为 6
        int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        return mls(array);
    }
    public static int mls(int[] array) {
        if (null == array || array.length == 0) { return Integer.MIN_VALUE; }
        int[] dp = new int[array.length];
        dp[0] = array[0];
        for (int  i = 1; i < array.length; i++) {
            int pre = dp[i - 1];
            if (pre <= 0) {
                dp[i] = array[i];
            } else {
                dp[i] = array[i] + pre;
            }
        }

        return Arrays.stream(dp).max().orElse(Integer.MIN_VALUE);
    }

    /**
     * 最长上升子序列
     * @return
     */
    public static int lis() {
        // {2,5,7,101} 或 {2,5,7,18} 最长上升子序列长度为 4
        int[] array = {10, 2, 2, 5, 1, 7, 101, 18};
        return lis(array);
    }
    public static int lis(int[] array) {
        if (null == array || array.length == 0) { return -1; }
        int[] dp = new int[array.length];
        Arrays.fill(dp, 1);

        for (int i = 1; i < array.length; i++) {
            int count = Integer.MIN_VALUE;
            for (int j = 0; j < i; j++) {
                if (array[j] < array[i]) {
                    count = Math.max(count, dp[j]);
                }
            }
            if (count != Integer.MIN_VALUE) {
                dp[i] = count + 1;
            }
        }

        return Arrays.stream(dp).max().orElse(-1);
    }

    /**
     * 最长公共子序列
     * @return
     */
    public static int lcs() {
        // 1, 9, 10
//        int[] nums1 = {1, 3, 5, 9, 10};
//        int[] nums2 = {1, 4, 9, 10};

        // 1, 4, 9, 10
//        int[] nums1 = {1, 4, 5, 9, 10};
//        int[] nums2 = {1, 4, 9, 10};

        // 2, 3, 8, 10, 13
        int[] nums1 = {1, 2, 3, 6, 8, 10, 7, 13};
        int[] nums2 = {2, 3, 8, 10, 13};

        return lcs(nums1, nums2);
    }
    public static int lcs(int[] nums1, int[] nums2) {
        if (null == nums1 || nums1.length == 0 || null == nums2 || nums2.length == 0) { return 0; }

        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    int first = dp[i][j - 1];
                    int second = dp[i - 1][j];
                    dp[i][j] = Math.max(first, second);
                }
            }
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                max = Math.max(dp[i][j], max);
            }
        }

        return max;
    }

    /**
     * 最长公共子串
     * @return
     */
    public static int lcss() {
        // AB 或 BA
//        String str1 = "ABDCBA";
//        String str2 = "ABBA";

        // C B A
        String str1 = "ABDCBA";
        String str2 = "HGCBA";
        return lcss(str1, str2);
    }
    public static int lcss(String str1, String str2) {
        if (null == str1 || str1.length() == 0 || null == str2 || str2.length() == 0) { return 0; }

        int[][] dp = new int[str1.length() +  1][str2.length() +  1];
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                max = Math.max(dp[i][j], max);
            }
        }

        return max;
    }


}
