package com.jomkie.datastructure.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap<K-V> 添加原理
 * 针对 K 计算它的 hashCode，hashCode相同计算的索引一定相同；索引相同hashCode不一定相同
 *    添加元素时，K 的hashCode与数组长度求模计算出索引
 *    在索引的集合中用 K 的hashCode各存在元素的 K 进行比对，如果相同则覆盖对应元素的 K 和 V
 *    如果不相同则放入集合尾部（新元素）
 *    ### 注意！！如果不重写 K 的 hashCode 则比对的就是它们的 内存地址 ###
 *    ### 注意！！如果不重写 K 的 equals 则比对的就是它们的 内存地址 ###
 *    ### 注意！！所以必须重写 K 的 hashCode 和 equals
 *
 *    ### hashCode 相等，equals 不一定相等
 *    ### equals 相等则 hashCode 一定相等
 */
public class HashTest {

    public static void main(String[] args) {
        test02();
    }

    /** 实验重写 hashCode() 就越是否生效 */
    public static void test00() {
        Person p1 = new Person(10, 1.67f, "jack");
        Person p2 = new Person(10, 1.67f, "jack");
        System.out.println(p1.hashCode() == p2.hashCode());
    }

    /** 测试重写 equals() 方法是否生效 */
    public static void test01() {
        Person p1 = new Person(10, 1.67f, "jack");
        Person p2 = new Person(10, 1.67f, "jack");

        Map<Object, Object> map = new HashMap<>();
        map.put(p1, "abc");
        map.put("test", "ccc");
        map.put(p2, "bcd");

        // 打印2，如果重写了 equals 则为2，第三次操作会覆盖第一次的数据
        System.out.println(map.size());
        // 打印3，如果没有重写 equals
        System.out.println(map.size());
    }

    /** 测试字符串的 hashCode 生成 */
    public static void test02() {
        String str = "jack";
        int len = str.length();
        int hashCode = 0;
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
//            hashCode = hashCode * 31 + c;
            hashCode = (hashCode << 5) - hashCode + c;
        }
        System.out.println(hashCode);
        System.out.println(str.hashCode());
    }

}
