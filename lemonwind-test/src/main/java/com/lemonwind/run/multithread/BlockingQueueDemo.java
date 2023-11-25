package com.lemonwind.run.multithread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        /* ---------------------------------------------------------------------------
        方法类型  抛出异常    特殊值     阻塞      超时
        插入         add(e)       offer(e)  put(e)  offer(e,time,unit)
        移除         remove()  poll()      take()   poll(time,unit)
        检查         element() peek()    不可用   不可用
        --------------------------------------------------------------------------- */

        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(3);
        blockingQueue.add("a");
        blockingQueue.add("b");
        blockingQueue.add("c");

        // 输出 a
//        System.out.println(blockingQueue.element());

        // 测试：抛出异常
//        blockingQueue.add("to happen error");

        // 测试：输出 a b c 再抛出异常
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());


        // 测试：输出 true true true false
//        blockingQueue.remove();
//        blockingQueue.remove();
//        blockingQueue.remove();
//        System.out.println(blockingQueue.offer("a"));;
//        System.out.println(blockingQueue.offer("b"));;
//        System.out.println(blockingQueue.offer("c"));;
//        System.out.println(blockingQueue.offer("ww"));;

        // 测试：输出 a b c null
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());

        // 放进 a b c 后，在放 blocking 时进入阻塞
//        blockingQueue.remove();
//        blockingQueue.remove();
//        blockingQueue.remove();
//        blockingQueue.put("a");
//        blockingQueue.put("b");
//        blockingQueue.put("c");
//        System.out.println("准备放入 blocking");
//        blockingQueue.put("blocking");

        // 测试：输出 a b c，取第四个元素时发生阻塞
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());

//        offer(time, unit) 和 poll(time, unit) 阻塞时间限制，不作兴趣例
    }

}
