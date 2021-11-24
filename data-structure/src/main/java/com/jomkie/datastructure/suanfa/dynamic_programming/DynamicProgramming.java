package com.jomkie.datastructure.suanfa.dynamic_programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 动态规划
 * @author Jomkie
 * @since 2021-11-23 21:37:44
 */
public class DynamicProgramming {

    public static void main(String[] args) {
        System.out.println(coinChange());
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
//        return coinChangeTool(faces, money);

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
//        return coinChangeTool2(faces, dp, money);

        int money = 41;
        Integer[] faces = {25, 20, 5, 1};

//        int money = 41;
//        Integer[] faces = {25, 20, 5, 5};
        return money + " 分在面额为" + Arrays.toString(faces) +  "中找补需要 " + coinChangeTool3(faces, money) + " 枚";
    }
    /** 暴力递归 */
    private static int coinChangeTool(int[] faces, int money) {
        if (money < 1) { return Integer.MAX_VALUE; }
        if (money == 25 || money == 20 || money == 5 || money == 1) { return 1; }
        int min1 = Math.min(coinChangeTool(faces, money - 25), coinChangeTool(faces, money - 20));
        int min2 = Math.min(coinChangeTool(faces, money - 5), coinChangeTool(faces, money - 1));
        return Math.min(min1, min2) + 1;
    }
    /** 记忆化搜索-优化，此算法目前限定了面值 */
    private static int coinChangeTool2(int[] faces, int[] dp, int money) {
        if (money < 1) { return Integer.MAX_VALUE; }
        if (dp[money] == 0) {
            int min1 = Math.min(coinChangeTool2(faces, dp, money - 25), coinChangeTool2(faces, dp, money - 20));
            int min2 = Math.min(coinChangeTool2(faces, dp, money - 5), coinChangeTool2(faces, dp, money - 1));
            dp[money] = Math.min(min1, min2) + 1;
        }
        return dp[money];
    }
    /** 递推（自底向上），此算法不足以处理不够补零的情况 */
    private static int coinChangeTool3(Integer[] faces, int money) {
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

}
