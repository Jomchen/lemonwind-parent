package com.jomkie.test;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.stream.IntStream;

/**
 * 测试 NIO
 * @author Jomkie
 * @since 2021-09-09 10:52:19
 * @param
 * @return
 */
public class TestNio {

    public static void main(String[] args) {
        test00();
    }

    public static void test00() {
        int[] sourceArray = new int[10];
        IntStream.range(0, 10).boxed().forEach(index -> sourceArray[index] = index);
        IntBuffer intBuffer = IntBuffer.wrap(sourceArray);
        // IntBuffer intBuffer2 = IntBuffer.allocate(10);
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

        /* -- Buffer 中有 capacity（总容量，不可变动），mark（标记位置，默认为 -1），position（当前应处理位置），limit（上界，默认等于 capacity）-- */
        /* -- rewind(), clear(), flip() 总是丢弃 mark */

        // limit 移动到 capacity 位置，position 移动到 0 位置
        //intBuffer.clear();
        // limit 移动到 position 位置，position 移动到 0 位置
        //intBuffer.flip();
        // 仅 position 移动到 0 位置
        //intBuffer.rewind();
        // 检测 position 是否已经到达 limit 的上界
        //intBuffer.hasRemaining();
        // 当前位置(position)到上界(limit)剩余元素数量
        //intBuffer.remaining();
        // position = mark
        //intBuffer.reset();
        // mark = position
        //intBuffer.mark();
        // mark丢弃，position 为 limit - 1 - position + 1，limit 为 capacity
        //intBuffer.compact();
        // ################## 需要重视
        //intBuffer.arrayOffset();
        //System.out.println(ByteOrder.nativeOrder().toString());
    }

    // TODO 3.1.2 的使用通道，数据复制需要重新理解

    public void createDirectBuffer() {
        // 创建一个直接的字节缓冲区，如果没有 direct 字眼或wrap的包装方式创建的缓冲区都是非直接缓冲区
        // 只有直接缓冲区才可以进行系统级别的 I/O 操作
        //ByteBuffer.allocateDirect(5);
    }

}
