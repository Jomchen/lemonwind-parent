package com.jomkie.datastructure.map;

import com.jomkie.datastructure.hash.Person;

public class MapTest {

    public static void main(String[] args) {
        /*test00();
        test01();*/
        test02();
    }

    public static void test00() {
        TreeMap<String, Integer> map = new TreeMap<>();
        // 打印 class_2,public_8,text_6
        /*map.put("class", 2);
        map.put("public", 5);
        map.put("text", 6);
        map.put("public", 8);*/

        // 打印 a_8,b_6,c_2
        map.put("c", 2);
        map.put("a", 5);
        map.put("b", 6);
        map.put("a", 8);
        map.traversal(new Map.Visitor<String, Integer>() {
                  @Override
                  public boolean visit(String key, Integer value) {
                      System.out.println(key + "_" + value);
                      return false;
                  }
              }
        );
    }

    public static void test01() {
        Set<String> set = new TreeSet<>();
        // 打印 a,b,c
        set.add("a");
        set.add("c");
        set.add("b");
        set.add("c");
        set.add("c");

        set.traversal(new Set.Visitor<String>() {
            @Override
            public boolean visit(String element) {
                System.out.println(element);
                return false;
            }
        });
    }

    public static void test02() {
        Person p1 = new Person(10, 1.67f, "jack");
        Person p2 = new Person(10, 1.67f, "jack");

        Map<Object, Integer> map = new HashMap<>();
        map.put(p1, 1);
        map.put(p2, 2);
        map.put("jack", 3);
        map.put("rose", 4);
        map.put("jack", 5);
        map.put(null, 6);
        /*System.out.println(map.get("jack"));
        System.out.println(map.get("rose"));
        System.out.println(map.get(null));
        System.out.println(map.get(p1));*/

        System.out.println(map.size());
        System.out.println(map.remove("jack"));
        System.out.println(map.get("jack"));
        System.out.println(map.size());
    }

}
