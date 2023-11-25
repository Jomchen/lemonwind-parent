package com.lemonwind.test.run.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;

/**
 * 测试 NIO
 * @author Jomkie
 * @since 2021-09-09 10:52:19
 */
public class TestNio {

    // TODO 3.1.2 的使用通道，数据复制需要重新理解
    public static void main(String[] args) throws IOException {
        test00();
    }

    public static void test00() throws IOException {
        /*int[] sourceArray = new int[10];
        IntStream.range(0, 10).boxed().forEach(index -> sourceArray[index] = index);
        IntBuffer intBuffer = IntBuffer.wrap(sourceArray);
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }*/

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
//        intBuffer.compact();
        // ################## 需要重视
        //intBuffer.arrayOffset();
        //System.out.println(ByteOrder.nativeOrder().toString());

        /*ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        for (int i = 0; i < 5; i++) {
            byteBuffer.put((byte)1);
        }
        System.out.println(byteBuffer.position() + "--" + byteBuffer.limit());
        byteBuffer.compact();
        System.out.println(byteBuffer.position() + "--" + byteBuffer.limit());*/


        openChannel();
    }



    /** 创建一个直接缓冲区 */
    public void createDirectBuffer() {
        // 创建一个直接的字节缓冲区，如果没有 direct 字眼或wrap的包装方式创建的缓冲区都是非直接缓冲区
        // 只有直接缓冲区才可以进行系统级别的 I/O 操作
        //ByteBuffer.allocateDirect(5);
    }

    /** 复制算法解析 */
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

    /** 测试文件的读取 */
    public static void testReadFile() throws FileNotFoundException {
        /*FileInputStream fileInputStream = new FileInputStream("/opt/test/nio-test/nio.txt");
        ReadableByteChannel readableByteChannel = Channels.newChannel(fileInputStream);*/
    }

    public static void openChannel() throws IOException {
        // 能打开通道的几种方式
        String readFilePath = "/opt/test/nio-test/read.txt";
        String writeFilePath = "/opt/test/nio-test/write.txt";
        String mode = "rw"; // "rw" "rws" "rwd"
        /*FileChannel fileChannel = new RandomAccessFile(readFilePath, mode).getChannel();
        FileChannel fileChannel2 = new FileInputStream(readFilePath).getChannel();
        FileChannel fileChannel3 = new FileOutputStream(readFilePath).getChannel();
        DatagramChannel datagramChannel = DatagramChannel.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        SocketChannel socketChannel = SocketChannel.open();*/

        /*FileInputStream fileInputStream = new FileInputStream(readFilePath);
        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(fileInputStream));
        long startTime2 = System.currentTimeMillis();
        String str2;
        while ((str2 = bufferedReader2.readLine()) != null) {
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println((endTime2 - startTime2) + "------> 毫秒**IO");
        bufferedReader2.close();
        fileInputStream.close();

        FileChannel fileChannel = new RandomAccessFile(readFilePath, mode).getChannel();
        InputStream inputStream = Channels.newInputStream(fileChannel);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        long startTime = System.currentTimeMillis();
        String str;
        while ((str = bufferedReader.readLine()) != null) {
        }
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + "------> 毫秒**NIO");
        bufferedReader.close();
        fileChannel.close();*/

        FileChannel fileChannel3 = new RandomAccessFile(readFilePath, mode).getChannel();
        Reader reader = Channels.newReader(fileChannel3, StandardCharsets.ISO_8859_1.name());
        BufferedReader bufferedReader3 = new BufferedReader(reader);
        long startTime3 = System.currentTimeMillis();
        String str3;
        while ((str3 = bufferedReader3.readLine()) != null) {
        }
        long endTime3 = System.currentTimeMillis();
        System.out.println((endTime3 - startTime3) + "------> 毫秒**NIO2");
        bufferedReader3.close();
        reader.close();
    }

}
