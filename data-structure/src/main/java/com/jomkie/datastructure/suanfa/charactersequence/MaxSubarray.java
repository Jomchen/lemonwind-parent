package com.jomkie.datastructure.suanfa.charactersequence;

public class MaxSubarray {

    public static void main(String[] args) {
        maxSubarray();
    }

    /** 最大连续子续列和 */
    public static int maxSubarray() {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        // 输出6
        return maxSubarrayTool(nums);
    }

    /** 最大连续子续列和，暴力法 */
    public static int maxSubarrayTool(int[] nums) {
        // 解题思路，任意起始点为开头，任意结束点为结尾，求这种形式开头到结尾的连续子序列和的最大值
        if (nums == null || nums.length == 0) { return 0; }
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            int amount = 0;
            for (int end = begin; end < nums.length; end++) {
                amount += nums[end];
                max = Math.max(max, amount);
            }
        }
        return max;
    }

    /**
     * 最大连续子续列和，分治优化
     * S[i, j) 表示 [i, j) 区间的最大连续子序列和，存在的三种情况
     *    [i, j) 存在于 [begin, mid) 中
     *    [i, j) 存在于 [mid, end) 中
     *    [i, j) 一部分存在于 [begin, mid) 中，另一部分存在于 [mid, end) 中
     * 根据思想：
     *    mid - 1 开始往前相加，加到最大的值 x1
     *    mid 开始往后相加， 加到最大的 x2
     *    那么 S[i, j] 为 x1 + x2
     * @param nums
     * @return
     */
    public static int maxSubarrayTool2(int[] nums) {
        return 0;
    }

}
