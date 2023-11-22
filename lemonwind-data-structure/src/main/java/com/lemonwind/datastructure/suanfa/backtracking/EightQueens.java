package com.lemonwind.datastructure.suanfa.backtracking;

/**
 * 回朔算法
 *
 * 八皇后问题
 * 8 x 8 格的国际象棋上摆放八个皇后，使其不能互相攻击：任意两个皇后都不能牌同一行，同一列或同一斜线上
 * 请问有多少种摆法
 *
 * A[n,m] => 记为排列，n个元素，排列m次，共有几种排列
 * C[n,m] => 记为组合，n个元素，如果需要一组m个元素的组合，共有几种组合方式
 *
 * A[n,m,] => n(n-1)...(n-m+1) = n!/(n-m)! => 排列
 * C[n,m] => A[n,m]/m! => n!/m!(n-m)! => C[n,n-m]
 *
 */
public class EightQueens {

    public static void main(String[] args) {
        // 2 种
        //test1(4);
        //test2(4);

        // 92 种
        //test1(8);
        //test2(8);

        test03();
    }


    /** 数组索引是行号，值是列号，col[4] = 5，表示第5行第6列 */
    private static int[] cols = null;
    private static int ways = 0;
    /**
     * 回溯法
     * @param n 棋盘行或列数
     */
    public static void test1(int n) {
        if (n < 1) { return; }
        cols = new int[n];
        place(0);
        System.out.println("总共有 " + ways + " 种方法");
    }
    /**
     * 从第 row 行开始摆放皇后
     * @param row 行
     */
    private static void place(int row) {
        if (row == cols.length) {
            ways++;
            show();
            return;
        }
        for (int col = 0; col < cols.length; col++) {
            if (isValid(row, col)) {
                cols[row] = col;
                place(row + 1);
            }
        }
    }
    /**
     * 判断第 row 行第 col 列是否合法
     * 根据数学知识 A(x1, y1) 和 B(x2, y2)，如果 (x1 - x2)/(y1 - y2) 为 -1 或 1 ，则是 45 度角
     * @param row 行
     * @param col 列
     * @return boolean 是否合法
     */
    private static boolean isValid(int row, int col) {
        for (int i = 0; i < row; i++) {
            // 第 col 列已经有皇后
            if (cols[i] == col) { return false; }
            // 判断此顶点是否和其它顶点在一条斜线上，这些 row 肯定比 i 大
            if (row - i == Math.abs(col - cols[i])) { return false; }
        }

        return true;
    }
    private static void show() {
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                if (cols[row] == col) {
                    System.out.print("x ");
                } else {
                    System.out.print("o ");
                }
            }
            System.out.println();
        }
        System.out.println("---------------------------------------");
    }

    /* ---------------------------------------- 华丽的分割线 ---------------------------------------- */
    /** 摆放种数 */
    private static int WAYS_SECOND = 0;
    /** 方便打印皇后的缓存数据 */
    private static int[] QUENNS_SECOND = null;
    /** 标记着某一列是否有皇后 */
    private static boolean[] COLS_SECOND = null;
     /** 标记着某一对角线是否有皇后（左上角->右下角，left top -> right bottom）*/
     /** 一条线上的各点 row - col + (n - 1) 都相等 */
    private static boolean[] LEFT_TOP_SECOND = null;
    /** 标记着某一对角线是否有皇后（右上角->左下角，right top -> left bottom）*/
     /** 一条线上的各点 row + col 都相等 */
    private static boolean[] RIGHT_TOP_SECOND = null;
    /**
     * n 皇后优化
     * @param n 行数或列数
     */
    public static void test2(int n) {
        if (n < 1) { return; }

        // 注意对角线一定是 2 * n - 1
        QUENNS_SECOND = new int[n];
        COLS_SECOND = new boolean[n];
        LEFT_TOP_SECOND = new boolean[(n << 1) - 1];
        RIGHT_TOP_SECOND = new boolean[LEFT_TOP_SECOND.length];

        place2(0);
        System.out.println("总共有 " + WAYS_SECOND + " 种方法");
    }
    private static void place2(int row) {
        if (row == COLS_SECOND.length) {
            WAYS_SECOND++;
            show2(WAYS_SECOND);
            return;
        }

        for (int col = 0; col < COLS_SECOND.length; col++) {
            if(isValid2(row, col)) { continue; }

            COLS_SECOND[col] = true;
            LEFT_TOP_SECOND[row - col + COLS_SECOND.length - 1] = true;
            RIGHT_TOP_SECOND[row + col] = true;
            QUENNS_SECOND[row] = col;
            place2(row + 1);

            // 这里的重置是为了下一行不能摆皇后的回溯情况
            COLS_SECOND[col] = false;
            LEFT_TOP_SECOND[row - col + COLS_SECOND.length - 1] = false;
            RIGHT_TOP_SECOND[row + col] = false;
        }
    }
    private static boolean isValid2(int row, int col) {
        if (COLS_SECOND[col]) { return true; }
        int ltIndex = row - col + COLS_SECOND.length - 1;
        if (LEFT_TOP_SECOND[ltIndex]) { return true; }
        int rtIndex = row + col;
        if (RIGHT_TOP_SECOND[rtIndex]) { return true; }
        return false;
    }
    private static void show2(int way) {
        for (int row = 0; row < QUENNS_SECOND.length; row++) {
            for (int col = 0; col < QUENNS_SECOND.length; col++) {
                if (QUENNS_SECOND[row] == col) {
                    System.out.print("x ");
                } else {
                    System.out.print("o ");
                }
            }
            System.out.println();
        }
        System.out.println("上图是第 " + way + " 种---------------------------------------");
    }

    /* ---------------------------------------- 华丽的分割线 ---------------------------------------- */

    private static int[] QUEENS_THIRD;
    /** 8 个位，某位为1表示那列有皇后 */
    private static byte COLS_THIRD;
    private static short LEFT_TOP_THIRD;
    private static short RIGHT_TOP_THIRD;
    private static int WAYS_THIRD = 0;
    public static void test03() {
        QUEENS_THIRD = new int[Byte.SIZE];
        place3(0);
        System.out.println("总共有 " + WAYS_THIRD + " 种方法");
    }
    private static void place3(int row) {
        if (row == 8) {
            WAYS_THIRD ++;
            show3(WAYS_THIRD);
            return;
        }
        for (int col = 0; col < 8; col++) {
            int cv = (1 << col);
            if ((COLS_THIRD & cv) != 0) { continue; }
            int lv = 1 << (row - col + 8 - 1);
            if ((LEFT_TOP_THIRD & lv) != 0) { continue; }
            int rv = 1 << (row + col);
            if ((RIGHT_TOP_THIRD & rv) != 0) { continue; }

            // 置值
            QUEENS_THIRD[row] = col;
            COLS_THIRD |= (1 << col);
            LEFT_TOP_THIRD |= lv;
            RIGHT_TOP_THIRD |= rv;
            place3(row + 1);
            COLS_THIRD &= ~cv;
            LEFT_TOP_THIRD &= ~lv;
            RIGHT_TOP_THIRD &= ~rv;
        }
    }
    private static void show3(int way) {
        for (int row = 0; row < QUEENS_THIRD.length; row++) {
            for (int col = 0; col < QUEENS_THIRD.length; col++) {
                if (QUEENS_THIRD[row] == col) {
                    System.out.print("x ");
                } else {
                    System.out.print("o ");
                }
            }
            System.out.println();
        }
        System.out.println("上图是第 " + way + " 种---------------------------------------");
    }

}
