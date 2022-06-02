package com.jomkie.test.multithread;

public class ThreadPoolDemo {

    public static void main(String[] args) {
        // 创建固定线程数的线程池
//        Executors.newFixedThreadPool();
        // 一个任务一个任务执行，一池一线程
//        Executors.newSingleThreadExecutor()
        // 线程池根据需求创建线程，可扩容，遇强则强（无限扩容容易内存溢出）
//        Executors.newCachedThreadPool();
    }


}
