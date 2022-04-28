package com.jomkie.test.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.stream.IntStream;

/**
 * 测试 NIO
 * @author Jomkie
 * @since 2021-09-09 10:52:19
 */
public class TestNio {

    // TODO 3.1.2 的使用通道，数据复制需要重新理解
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
        intBuffer.compact();
        // ################## 需要重视
        //intBuffer.arrayOffset();
        //System.out.println(ByteOrder.nativeOrder().toString());

        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        for (int i = 0; i < 5; i++) {
            byteBuffer.put((byte)1);
        }
        System.out.println(byteBuffer.position() + "--" + byteBuffer.limit());
        byteBuffer.compact();
        System.out.println(byteBuffer.position() + "--" + byteBuffer.limit());
    }



    public void createDirectBuffer() {
        // 创建一个直接的字节缓冲区，如果没有 direct 字眼或wrap的包装方式创建的缓冲区都是非直接缓冲区
        // 只有直接缓冲区才可以进行系统级别的 I/O 操作
        //ByteBuffer.allocateDirect(5);
    }

    public void copyDetail(ReadableByteChannel src, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16 * 1024);
        // 读取数据到 buffer 中，也许不会完全占满 buffer
        while (src.read(buffer) != -1) {
            // 因为buffer的指针发生了偏移，所以移动 position 和 limit 以进行预处理
            buffer.flip();
            // 将 buffer 中的数据写入到 dest 中（也许只使用了 buffer 的部分数据进行写操作）
            dest.write(buffer);
            // 如果是只使用了 buffer 的部分数据，剩下的数据进行压缩以让新的数据追加在旧数据（还未使用的数据）之后
            // 如果 buffer 已经空了，则和 clear 操作等同
            buffer.compact();
        }

        // 如果 buffer 中有剩余数据 且 src又已经没有数据可读，偏移指针预处理 buffer
        buffer.flip();
        // 如果 buffer 中有剩余数据，则使用之进行写操作
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

}
