package com.jomkie.datastructure.skiplist;

public class TestSkipList {

    public static void main(String[] args) {
        SkipList<Integer, Integer> list = new SkipList<>();
        /*test00(list, 100, 10);
        test00(list, 20, 5);*/
        test00(list, 30, 10);
    }

    /** 理论应该全部输出 true */
    private static void test00(SkipList<Integer, Integer> list, int count, int delta) {
        for (int i = 0; i < count; i++) {
            list.put(i, i + delta);
        }
        System.out.println(list);
        for (int i = 0; i < count; i++) {
            System.out.println(list.get(i) == i + delta);
        }
        System.out.println(list.size() == count);
        for (int i = 0; i < count; i++) {
            System.out.println(list.remove(i) == i + delta);
        }
        System.out.println(list.size() == 0);
    }


}
