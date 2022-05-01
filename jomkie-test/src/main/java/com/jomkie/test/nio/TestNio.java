package com.jomkie.test.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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
        // TODO 关于官方给出的数据复制算法讲解
        // while 之中：write 时可能不会使用完缓冲中的全部数据，所以调用 compact 进行剩余数据进行压缩待下次使用
        // while 之外：在 while 之中有数据剩余数据压缩情况，且读管道中已经没有数据读取的时候，调用 flip 进行剩余数据预处理位置移动
        // while 之外：buffer.hasRemaining() 判断是否真有剩余数据，如果有则处理掉
    }



    public void createDirectBuffer() {
        // 创建一个直接的字节缓冲区，如果没有 direct 字眼或wrap的包装方式创建的缓冲区都是非直接缓冲区
        // 只有直接缓冲区才可以进行系统级别的 I/O 操作
        //ByteBuffer.allocateDirect(5);
    }

    public void testReadFile() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("/opt/test/nio-test/nio.txt");
        ReadableByteChannel readableByteChannel = Channels.newChannel(fileInputStream);
    }

}
