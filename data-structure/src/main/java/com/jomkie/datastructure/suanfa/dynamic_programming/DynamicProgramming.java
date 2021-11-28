package com.jomkie.datastructure.suanfa.dynamic_programming;

import java.util.Arrays;

/**
 * 动态规划
 * @author Jomkie
 * @since 2021-11-23 21:37:44
 */
public class DynamicProgramming {

    public static void main(String[] args) {
//        System.out.println(coinChange());
//        System.out.println(maxSubArray());
//        lis();
        System.out.println(lcs());
    }

    /**
     * 零钱兑换-找补个数最小
     * @author Jomkie
     * @since 2021-11-23 21:38:20
     * @return void
     */
    public static String coinChange() {
        // 20, 20, 1
//        int money = 41;
//        int[] faces = {25, 20, 5, 1};
//        coinChangeOptimization(faces, money);

        // 7
//        int money = 19;
//        int[] faces = {25, 20, 5, 1};
        // 3
//        int money = 41;
//        int[] faces = {25, 20, 5, 1};
//        int[] dp = new int[money + 1];
//        for (int i = 0; i < faces.length; i++) {
//            if (faces[i] <= money) { dp[faces[i]] = 1; }
//        }
//        coinChangeOptimization2(faces, dp, money);

        // 3
//        int money = 41;
//        Integer[] faces = {25, 20, 5, 1};

        // -1 表示不足以找补
//        int money = 41;
//        Integer[] faces = {25, 20, 5, 5};
//        return money + " 分在面额为" + Arrays.toString(faces) +  "中找补需要 " + coinChangeOptimization4(faces, money) + " 枚";


        return "";
    }
    /** 暴力递归 */
    private static int coinChangeOptimization(int[] faces, int money) {
        if (money < 1) { return Integer.MAX_VALUE; }
        if (money == 25 || money == 20 || money == 5 || money == 1) { return 1; }
        int min1 = Math.min(coinChangeOptimization(faces, money - 25), coinChangeOptimization(faces, money - 20));
        int min2 = Math.min(coinChangeOptimization(faces, money - 5), coinChangeOptimization(faces, money - 1));
        return Math.min(min1, min2) + 1;
    }
    /** 记忆化搜索-优化，此算法目前限定了面值 */
    private static int coinChangeOptimization2(int[] faces, int[] dp, int money) {
        if (money < 1) { return Integer.MAX_VALUE; }
        if (dp[money] == 0) {
            int min1 = Math.min(coinChangeOptimization2(faces, dp, money - 25), coinChangeOptimization2(faces, dp, money - 20));
            int min2 = Math.min(coinChangeOptimization2(faces, dp, money - 5), coinChangeOptimization2(faces, dp, money - 1));
            dp[money] = Math.min(min1, min2) + 1;
        }
        return dp[money];
    }
    /** 递推（自底向上），此算法不足以处理不够补零的情况 */
    private static int coinChangeOptimization3(Integer[] faces, int money) {
        if (faces == null || faces.length == 0 || money < 1) { return -1; }
        Integer[] dp = new Integer[money + 1];
        Integer[] lastFace = new Integer[money + 1];
        Arrays.fill(dp, 0);
        for (int currentFace = 1; currentFace <= money; currentFace++) {
            int min = Integer.MAX_VALUE;
            for (int index = 0; index < faces.length; index++) {
                if (faces[index] <= currentFace && dp[currentFace - faces[index]] < min) {
                    min = dp[currentFace - faces[index]];
                    lastFace[currentFace] = faces[index];
                }
            }

            dp[currentFace] = min + 1;
        }

        print(lastFace, money);
        return dp[money];
    }
    private static void print(Integer[] lastFace, int money) {
        while (money > 0) {
            Integer theFace = lastFace[money];
            System.out.print(theFace + " ");
            money -= theFace;
        }
        System.out.println();
    }

    /** 动态规划，最终解法算法（解决不能找补的情况 */
    private static int coinChangeOptimization4(Integer[] faces, int money) {
        if (faces == null || faces.length == 0 || money < 1) { return -1; }
        Integer[] dp = new Integer[money + 1];
        Arrays.fill(dp, 0);
        for (int currentFace = 1; currentFace <= money; currentFace++) {
            int min = Integer.MAX_VALUE;
            for (int index = 0; index < faces.length; index++) {
                if (faces[index] > currentFace) { continue; }
                int temporary = dp[currentFace - faces[index]];
                // 减去当前面额后不能找补，找补结果比之前的找补结果需要硬币更多
                // 这两种情况皆不满足找补最少硬币条件
                if (temporary == -1 || temporary >= min) { continue; }

                min = dp[currentFace - faces[index]];
            }

            // 硬币面额循环后，都不足以得到可以找补的情况
            if (min == Integer.MAX_VALUE) {
                dp[currentFace] = -1;
            } else {
                dp[currentFace] = min + 1;
            }
        }

        return dp[money];
    }

    /* ---------------------------------------------------------------------------------------------- */

    /** 最大连续子序列和 */
    public static int maxSubArray() {
        /*
          * int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4}
          * dp(i)) 表示 array[i] 结尾的最大连续子序列和
          * array(0) -2 结尾的最大连续子序列和为 -2                        => dp(0) = -2
          * array(1)  1 结尾的最大连续子序列和为   1                        => dp(1) = 1
          * array(2) -3 结尾的最大连续子序列和为  1, -3                   => dp(2) = dp(1) + (-3) = -2
          * array(3)  4 结尾的最大连续子序列和为   4                        => dp(3) = 4
          * array(4) -1 结尾的最大连续子序列和为  4, -1                   => dp(4) = dp(3) + (-1) = 3
          * array(5)  2 结尾的最大连续子序列和为   4, -1, 2              => dp(5) = dp(4) + 2 = 5
          * array(6)  1 结尾的最大连续子序列和为   4, -1, 2, 1          => dp(6) = dp(5) + 1 = 6
          * array(7) -5 结尾的最大连续子序列和为  4, -1, 2, 1, -5     => dp(7) = dp(6) + (-5)) = 1
          * array(8)  4 结尾的最大连续子序列和为  4, -1, 2, 1, -5, 4 => dp(8) = dp(7) + 4 = 5
          * 此数组的最大连续子序列和为 6
         */
        int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        return maxSubArrayOptimization2(array);
    }
    private static int maxSubArrayOptimization(int[] array) {
        if (null == array || array.length == 0) { return 0; }
        int[] dp = new int[array.length];
        dp[0] = array[0];
        for (int i = 1; i < array.length; i++) {
            int prev = dp[i - 1];
            if (prev <= 0) {
                // 因为一定以当前值 y 结尾，如果之前的结果 x 就小于0了，之前结果加上当前结果 x+y
                // 一定小于等于当前值 y，所以当前结果直接定为当前值 y
                dp[i] = array[i];
            } else {
                dp[i] = prev + array[i];
            }
        }

        System.out.println(Arrays.toString(dp));
        return Arrays.stream(dp).max().orElse(0);
    }

    /** 优化空间 */
    private static int maxSubArrayOptimization2(int[] array) {
        if (null == array || array.length == 0) { return 0; }
        int dp = array[0];
        int max = dp;
        for (int i = 1; i < array.length; i++) {
            dp = dp <= 0 ? array[i] : dp + array[i];
            max = Math.max(max, dp);
        }

        return max;
    }

    /* ---------------------------------------------------------------------------------------------- */

    /** LIS Longest Increasing Subsequence 最长上升子序列 */
    public static void lis() {
        // 给定一个无序的整数序列，求出最长上升子序列的长度（要求严格上升）
        // ijt[] nums = [10, 2, 2, 5, 1, 7, 101, 18] 的结果为 [2, 5, 7, 101], [2, 5, 7, 18]，长度是 4
        // dp(i) 是以nums[i] 结尾的最长上升子序列的长度，初始值为 1, 1 属于 [0, nums.length)
        // 以 nums[0]   10 结尾的最长上升子序列是  10              =>  所以 dp(0) = 1
        // 以 nums[1]     2 结尾的最长上升子序列是     2              =>  所以 dp(0) = 1
        // 以 nums[2]     2 结尾的最长上升子序列是     2              =>  所以 dp(0) = 1
        // 以 nums[3]     5 结尾的最长上升子序列是 2, 5              =>  所以 dp(0) = 1
        // 以 nums[4]     1 结尾的最长上升子序列是     1              =>  所以 dp(0) = 1
        // 以 nums[5]     7 结尾的最长上升子序列是 2, 5, 7         =>  所以 dp(0) = 1
        // 以 nums[6] 101 结尾的最长上升子序列是 2, 5,7, 101 => 所以 dp(0) = 1
        // 以 nums[7]   18 结尾的最长上升子序列是  2, 5, 7, 18 => 所以 dp(0) = 1
        int[] array = {10, 2, 2, 5, 1, 7, 101, 18};
        lisOptimization(array);
    }
    private static int lisOptimization(int[] array) {
        if (null == array || array.length == 0) { return 0; }
        int[] dp = new int[array.length];
        int max = dp[0] = 1;
        for (int i = 1; i < array.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (array[i] <= array[j]) { continue; }
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            max = Math.max(dp[i], max);
        }

        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(dp));
        return max;
    }
    /** 二分法优化 */
    private static int licOptimization2(int[] array) {
        if (array == null || array.length == 0) { return 0; }
        int[] top = new int[array.length];
        int len = 0;
        for (int num : array) {
            int begin = 0, end = len;
            while (begin < end) {
                int mid = (begin + end) >> 1;
                if (num <= top[mid]) {
                    end = mid;
                } else {
                    begin = mid + 1;
                }
            }
            top[begin] = num;
            if (begin == len) { len++; }
        }
        return len;
    }

    /* ---------------------------------------------------------------------------------------------- */

    /** LCS Long Common Subsequence 最长公共子序列 */
    public static int lcs() {
        // 假设 2 个序列分别是 nums1, nums2
        // i 属于 [1, nums1.length]     j 属于 [1, nums2.lemgth]
        // 假设 dp(i, j) 是 nums1 前 i 个元素 与 nums2 前 j 个元素 的最长公共子序列长度
        // dp(i, 0) 和 dp(0, j) 初始值为 0
        // 如果 nums1[i - 1] = nums2[j - 1]，那么 dp(i, j) = dp(i - 1, j - 1) + 1
        // 如果 nums1[i - 1] != nums2[j - 1]，那么 dp(i, j) = max {dp(i - 1, j), dp(i, j - 1)}
        // 求 dp(i, j) 时相当于是以下两列的最大公共子序列
        //     前 i - 1 个元素 | nums[i - 1]
        //     前 j - 1 个元素 | nums[j - 1]
        //     如果 nums[i - 1] = nums[j - 1]，那么 dp(i, j) = dp(i - 1, j - 1) + 1
        //     如果 nums[i - 1] != nums[j - 1]，那么 dp(i, j) = max {dp(i - 1, j), dp(i, j - 1)}

        // 案例
        // nums1[i - 1] 和 nums2[j - 1] 比较情况
        //     1. 相同时 => 1, 2, 3, 5<-->7 与 2, 5 <-->7，刚好最后一位元素相同，则结果为 2 + 1
        //     2. 不同时 => 1, 2, 3, 6<-->7 与 2, 5 <--> 6，最后一位元素不同，但是nums1中前 i - 1 部分最后元素刚好和nums2中 j - 1 索引位置元素相同
        //     3. 不同时 => 1, 2, 3, 5<-->7 与 2, 5 <--> 6，最后一位元素不同，但是nums2中前 j - 1 部分最后元素刚好和nums1中 i - 1 索引位置元素相同
        //     4. 不同时 => 1, 2, 3, 6<-->5 与 2, 5 <--> 6，nums1中前 i - 1 部分最后元素刚好和nums2中 j - 1 索引位置元素相同，并且nums2中前 j - 1 部分最后元素刚好和 nums1中 i - 1 索引位置元素相同
        // 所以 (i, j) 的 nums1[i - 1] != nums2[j - 1] 时，则取 2 和 3 两种情况的最大值

        // 1, 9, 10
        int[] nums1 = {1, 3, 5, 9, 10};
        int[] nums2 = {1, 4, 9, 10};

        // 1, 4, 9, 10
//        int[] nums1 = {1, 4, 5, 9, 10};
//        int[] nums2 = {1, 4, 9, 10};

        // 2, 3, 8, 10, 13
//        int[] nums1 = {1, 2, 3, 6, 8, 10, 7, 13};
//        int[] nums2 = {2, 3, 8, 10, 13};

        return lcsTool(nums1, nums2);
    }


    private static int lcsTool(int[] nums1, int[] nums2) {
        if (null == nums1 || nums1.length == 0 || null == nums2 || nums2.length == 0) { return 0; }
//        return longestCommonSubsequenceOptimization(nums1, nums1.length, nums2, nums2.length);
//        return longestCommonSubsequenceOptimization2(nums1, nums2);
//        return longestCommonSubsequenceOptimization3(nums1, nums2);
        return longestCommonSubsequenceTool2Optimization4(nums1, nums2);
    }
    /**
     * 求 nums1 前 i 个元素 和 nums2 前 j 个元素的最长公共子序列长度
     * 递归方式
     * @param nums1
     * @param i
     * @param nums2
     * @param j
     * @return
     */
    private static int longestCommonSubsequenceOptimization(int[] nums1, int i, int[] nums2, int j) {
        if (i == 0 || j == 0) { return 0; }
        if (nums1[i - 1] == nums2[j - 1]) {
            return longestCommonSubsequenceOptimization(nums1, i - 1, nums2, j - 1) + 1;
        }
        return Math.max(
                longestCommonSubsequenceOptimization(nums1, i - 1, nums2, j),
                longestCommonSubsequenceOptimization(nums1, i, nums2, j - 1)
        );
    }

    /** 动态规划方式，dp的结果展示为一个二维数组 */
    private static int longestCommonSubsequenceOptimization2(int[] nums1, int[] nums2) {
        if (null == nums1 || nums1.length == 0 || null == nums2 || nums2.length == 0) { return 0; }
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[nums1.length][nums2.length];
    }
    /** 优化数组空间为两行 */
    private static int longestCommonSubsequenceOptimization3(int[] nums1, int[] nums2) {
        // 如果把 dp 的结果显示成二维数组，分析得知从上到下，从左至右，
        // 每个元素在计算时需要参考：斜左上方元素，正上方元素，相邻左边元素
        //     当计算dp(i,j)，nums1[i] 等于 nums2[j] 则得出 nums[i - 1][j - 1] + 1
        //     当nums1[i] != nums2[j] 时则取 max{dp(i, j - 1), dp(i - 1, j)}
        //     所以优化结果为数组只使用到了两行
        if (null == nums1 || nums1.length == 0 || null == nums2 || nums2.length == 0) { return 0; }
        int[][] dp = new int[2][nums2.length + 1];
        for (int i = 1; i <= nums1.length; i++) {
            int row = i % 2;
            // int row = i & 1;    i % 2 可以优化为此行，在hash计算的readme.txt有解释原理，在递归计算斐波那契数时有提到优化
            int prevRow = (i - 1) % 2;
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[row][j] = dp[prevRow][j - 1] + 1;
                } else {
                    dp[row][j] = Math.max(dp[prevRow][j], dp[row][j - 1]);
                }
            }
        }

        return dp[nums1.length % 2][nums2.length];
    }
    /** 优化二维数组空间为一行*/
    private static int longestCommonSubsequenceTool2Optimization4(int[] nums1, int[] nums2) {
        // dp[x] 记录的是上层结果，随着当前位置 (i, j) 的遍历，需要注意以下几点
        //     需要考虑之前的 leftTop 作比较（如果 nums1[i - 1] == nums[j - 1]）
        //     并将当前的 dp[j] 作为 leftTop 备份
        //     将当前位置计算结果覆盖 dp[j]
        if (null == nums1 || nums1.length == 0 || null == nums2 || nums2.length == 0) { return 0; }

        // 让长度小的作为 dp 的长度，进一步优化空间
        int[] rowsNums = nums1, colsNums = nums2;
        if (nums1.length < nums2.length) {
            colsNums = nums1;
            rowsNums = nums2;
        }
        int[] dp = new int[colsNums.length + 1];
        for (int i = 1; i <= rowsNums.length; i++) {
            int cur = 0;
            for (int j = 1; j <= colsNums.length; j++) {
                int leftTop = cur;
                cur = dp[j];
                if (rowsNums[i - 1] == colsNums[j - 1]) {
                    dp[j] = leftTop + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
            }
        }

        System.out.println(Arrays.toString(dp));
        return dp[colsNums.length];
    }

    /* ---------------------------------------------------------------------------------------------- */

    /** LCS 最长公共子串 Longest Common Substring */
    public static void lcsS() {

    }

    public static void longestCommonSubstring(String str1, String str2) {

    }

}
