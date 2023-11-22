package com.lemonwind.test.multithread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicDemo {

    public static void main(String[] args) {
        // AtomicInteger 可能引发 ABA 问题，如果只注重结果则没有影响
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.compareAndSet(0, 1);

        // 以时间戮递增1以避免 ABA 问题
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 1);
        int stamp = atomicStampedReference.getStamp();
//        atomicStampedReference.compareAndSet();
    }
}
