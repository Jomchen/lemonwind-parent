package com.jomkie.datastructure.tree.trie;

public class TrieTest {

    public static void main(String[] args) {
        test00();
    }

    public static void test00() {
        Trie<Integer> trie = new Trie<>();
        trie.add("cat", 1);
        trie.add("dog", 2);
        trie.add("catalog", 3);
        trie.add("cast", 4);
        trie.add("小码哥", 5);
        System.out.println(trie.size() == 5);
        System.out.println(trie.get("dog"));
        System.out.println(trie.get("小码哥"));
//        System.out.println(trie.startWith("c"));
//        System.out.println(trie.startWith("ca"));
//        System.out.println(trie.startWith("cat"));
//        System.out.println(trie.startWith("cata"));
//        System.out.println(trie.get("小码哥") == 5);
//        System.out.println(trie.remove("cat") == 1);
//        System.out.println(trie.remove("catalog") == 3);
//        System.out.println(trie.remove("cast") == 4);
//        System.out.println(trie.size() == 2);
//        System.out.println(trie.startWith("do"));
//        System.out.println(trie.startWith("c"));
    }

}
