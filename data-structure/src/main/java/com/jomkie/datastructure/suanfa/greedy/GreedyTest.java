package com.jomkie.datastructure.suanfa.greedy;

import java.util.Arrays;

/**
 * 贪心算法
 * 每一步都采取当前状态下最优的选择（局部最优解），从而希望推导出僵尸最优解
 * 哈夫曼树，最小生成树算法（Prim, Kruska)，最短路径算法（Dijkstra）
 */
public class GreedyTest {

    public static void main(String[] args) {
        System.out.println("载重船总共可以选：" + test00() + " 件");;
        System.out.println("硬币找补最少可以找补：" + test01() + " 枚");;
    }

    /**
     *  固定载重的船，在相应重量的古董中，装尽可能多的古董
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
     * 零钱兑换
     * 假设有指定面值的硬币，要找补指定的金额，如何轮到硬币个数最少能找补（相应面值能重复被选择）
     * @return
     */
    public static int test01() {
        int money = 41, counts = 0;
        int[] faces = {25, 10, 5, 1};
        Arrays.sort(faces);
        for (int i = faces.length - 1; i >= 0; i--) {
           if (money < faces[i]) {
               continue;
           }

           money -= faces[i];
           counts++;
            System.out.println("减去：" + faces[i]);
           i = faces.length - 1;
        }
        return counts;
    }

}
