package com.jomkie.test;

import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
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
    }

}
