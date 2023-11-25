package com.lemonwind.test.run.multithread;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ForkJoinDemo {

    public static void main(String[] args) {

        // 计算方式1
        long start = System.currentTimeMillis();
        int data = IntStream.range(0, 1_000_000).reduce(0, Integer::sum);
        long end = System.currentTimeMillis();
        System.out.println(data + "<-->" + (end - start) + "秒");

        // 计算方式2
        MyFork myFork = new MyFork(0, 1_000_000);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        long start2 = System.currentTimeMillis();
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myFork);
        long end2 = System.currentTimeMillis();
        try {
            Integer result = forkJoinTask.get();
            System.out.println(result + "<-->" + (end2 - start2) + "秒");
            forkJoinPool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 计算方式3
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            // 此为没有返回值的情况
            System.out.println(Thread.currentThread().getName());
        });
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> "Linux" );
        completableFuture2.whenComplete((d, e) -> {
            // 这里的 d 是返回值
            // 这里的 e 是异常，如果没有异常则为 null
        });
    }

    public static class MyFork extends RecursiveTask<Integer> {
        public static final Integer VALUE = 10;
        public Integer start;
        public Integer end;

        public MyFork(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if ((end - start) <= VALUE) {
                return IntStream.rangeClosed(start, end).reduce(0, Integer::sum);
            }

            int mid = (start + end) >> 1;
            MyFork left = new MyFork(start, mid);
            MyFork right = new MyFork(mid + 1, end);
            left.fork();
            Integer rightValue = right.compute();
            Integer leftValue = left.join();
            return leftValue + rightValue;
        }
    }

}
