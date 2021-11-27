package com.jomkie.datastructure.suanfa.charactersequence;

public class MaxSubarray {

    public static void main(String[] args) {
        System.out.println(maxSubarray());;
    }

    /** 最大连续子续列和 */
    public static int maxSubarray() {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        // 输出6
//        return maxSubArrayTool(nums);
        return maxSubArrayTool2(nums, 0, nums.length);
    }

    /** 最大连续子续列和，暴力法 */
    public static int maxSubArrayTool(int[] nums) {
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
     *    [i, j) = [i, mind) + [mid, j)
     *    S[i, mid) = max {S[k, mid)}, begin <= k < mid
     * @param nums
     * @return
     */
    public static int maxSubArrayTool2(int[] nums, int begin, int end) {
        if ((end - begin) < 2) { return nums[begin]; }
        int mid = (begin + end) >> 1;

        int leftMax = Integer.MIN_VALUE;
        int leftSum = 0;
        for (int i = mid - 1; i >= begin; i--) {
            leftSum += nums[i];
            leftMax = Math.max(leftMax, leftSum);
        }

        int rightMax = Integer.MIN_VALUE;
        int rightSum = 0;
        for (int j = mid; j < end; j++) {
            rightSum += nums[j];
            rightMax = Math.max(rightMax, rightSum);
        }

        return Math.max(
                (leftMax + rightMax),
                Math.max(
                        maxSubArrayTool2(nums, begin, mid),
                        maxSubArrayTool2(nums, mid, end)
                )
        );
    }

    /** 最优解法为 动态规划算法 */

}
