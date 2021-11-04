package com.jomkie.datastructure.suanfa.greedy;

import java.util.Arrays;

/**
 * 贪心算法
 * 每一步都采取当前状态下最优的选择（局部最优解），从而希望推得出最优解
 * 贪心算法只着眼局部最优解，不保证结果最优解
 * 哈夫曼树，最小生成树算法（Prim, Kruska)，最短路径算法（Dijkstra）
 */
public class GreedyTest {

    public static void main(String[] args) {
        System.out.println("载重船总共可以选：" + test00() + " 件");;
        System.out.println("硬币找补最少可以找补：" + test01() + " 枚");;
    }

    /**
     *  固定载重的船，在相应重量的古董中，装尽可能多的古董（同一个古董不能重复装）
     *  贪心策略：每一次都优先选择重量最小的古董
     */
    public static int test00() {
        int capacity = 30;
        int[] weights = {3, 5, 4, 10, 7, 14, 2, 11};
        int count = 0, weight = 0;
        Arrays.sort(weights);

        for (int i = 0; i < weights.length && weight < capacity; i++) {
            int newWeight = weight + weights[i];
            if (newWeight <= capacity) {
                weight = newWeight;
                count ++;
                System.out.println(weights[i]);
            }
        }

        return count;
    }

    /**
     * 零钱兑换-找补个数最小（贪心）
     * 假设有指定面值的硬币，要找补指定的金额，如何轮到硬币个数最少能找补（相应面值能重复被选择）
     * 此方案不准确，如果 {25, 20, 5, 1} 时则找补 {25 ,5 ,5 ,5 ,1}
     * 贪心策略保证局部最优解，但不保证结果最优解
     */
    public static int test01() {
        int money = 41, counts = 0, i = 0;
        Integer[] faces = {25, 10, 5, 1};
        Arrays.sort(faces, (Integer x, Integer y) -> y - x);
        while (i < faces.length) {
            if (money < faces[i]) {
                i++;
                continue;
            }

            money -= faces[i];
            counts++;
            System.out.println("找补：" + faces[i]);
            // 这里没有必要，因为当i为当前面额，证明之前的面额一定已经不符合找补条件了
            //i = 0;
        }

        return counts;
    }

    /* ----------------------
    0-1 背包问题：
    有n件物品和一个最大承重为 W 的背包，每件物品的重量是 wi，价值是 vi，
    在保证总重量不超过 W 的前提下，将哪几件物品装入背包，可以使得背包的总价值最大？注意：每个物品只有 1 件，也就是每个物品只能选择 0 件或 1 件，因此称为 0-1 背包问题
    如果采取贪心策略，有3个方案
        价值主导，优先选择价值最高的物品放进背包
        重量主导，优先选择重量最轻的物品放进背包
        价值密度主导：优先选择价值密度最高的物品放进背包（价值密度 = 价值 / 重量）
    ---------------------- */

}
