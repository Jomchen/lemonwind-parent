package com.jomkie.datastructure.recursion;

public class RecursionTest {

    public static void main(String[] args) {
        System.out.println(fid(5));
        System.out.println(fid01(5));
        System.out.println(fid02(5));
        System.out.println(fid03(5));
        hanoi(3, "A", "B", "C");
    }

    /** 斐波那契数列 */
    public static int fid(int n) {
        // 1, 1, 2, 3, 5, 8, 13, 21, 34, ...
        if (n == 1 || n == 2) {
            return 1;
        } else {
            return fid(n - 1) + fid(n - 2);
        }
    }

    /** 斐波那契数列，优化方案1，缓存信息 */
    public static int fid01(int n) {
        if (n <= 2) {
            return 1;
        }
        int [] array =new int[n + 1];
        array[1] = array[2] = 1;
        return fid01Tool(n, array);
    }
    private static int fid01Tool(int n, int[] array) {
        if (array[n] == 0) {
            array[n] = fid01Tool(n - 1, array) + fid01Tool(n - 2, array);
        }
        return array[n];
    }

    /** 斐波那契数，优化方案2，滚动数组 */
    public static int fid02(int n) {
        // x 与 2 进行相模的运算可以优化为 x & 1
        // 4 % 2 = 0 0b100 & 0b001 = 0
        // 3 % 2 = 1 0b011 & 0b001 = 1
        // 4 % 2 = 0 0b101 & 0b001 = 1
        // 5 % 2 = 1 0b110 & 0b001 = 0
        if (n <= 2) { return 1; }
        int[] array = new int[2];
        array[0] = array[1] = 1;
        for (int i = 3; i <= n; i++) {
            array[i % 2] = array[(i - 1) % 2] + array[(i - 2) % 2];
        }

        return array[n % 2];
    }

    /** 斐波那契数，优化方案3 */
    public static int fid03(int n) {
        int former = 1;
        int latter = 1;
        if (n <= 2) {
            return latter;
        }
        int i = 3;
        while (i <= n) {
            latter = former + latter;
            former = latter - former;
            i++;
        }

        return latter;
    }

    /** 上楼梯（跳台阶）*/

    /**
     * 上楼梯（跳台阶）
     * 同理可以和斐波那契数一样进行优化
     * @param n 目前的剩余的台阶数
     *  f(n) = f(n - 1) + f(n - 2)
     * @return
     */
    public static int climbStairs(int n) {
        // 楼梯有 n 阶台阶，上楼可以一步上 1 阶，也可以一步上 2 阶，走完 n 阶台阶共有多少种不同的走法
        if (n == 1) { return 1; }
        if (n == 2) { return 2; }

        // 因为一步只上 1 阶 或 2 阶，所以只共可能的走法应该是两种情况相加
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    /**
     * 汉诺塔
     * 要求将 n 个盘子从 n1-n 挪动到 seatC
     * @param n 盘子的数量
     * @param n 盘子的数量
     * @param n 盘子的数量
     * @param n 盘子的数量
     * @return
     */

    /**
     * 汉诺塔
     * 要求将 n 个盘子从 n1 ~ n 挪动到 seatC
     * 盘子号数小的只能放在盘子号数大的上面
     * @param n 盘子号数
     * @param seatA 座位A
     * @param seatB 座位B
     * @param seatC 座位C
     * @return
     */
    public static void hanoi(int n, String seatA, String seatB, String seatC) {
        if (n == 1) {
            System.out.println("将 " + n + " 号盘从 "  + seatA + " 放到 " + seatC);
            return;
        }

        hanoi(n - 1, seatA, seatC, seatB);
        System.out.println("将" + n + " 号盘从 " + seatA + " 放到 " + seatC);
        hanoi(n - 1, seatB, seatA, seatC);
    }


}
