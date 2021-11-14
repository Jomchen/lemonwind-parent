package com.jomkie.datastructure.tree.trie;

import java.util.HashMap;

/**
 * 又被称为前缀树 Prefix Tree
 */
public class Trie<V> {

    private int size;
    private Node<V> root = new Node<>();

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public void clear() {
        size = 0;
        root.getChildren().clear();
    }

    public V get(String str) {
        Node<V> node = node(str);
        return node == null ? null : node.value;
    }

    public boolean contains(String str) {
        Node<V> node = node(str);
        return null == node ? false : node.word;
    }

    public V add(String str, V value) {
        keyCheck(str);
        int length = str.length();
        Node<V> node = root;
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            Node<V> child = node.getChildren().get(c);
            if (null == child) {
                child = new Node<>();
                node.getChildren().put(c, child);
            }
            node = child;
        }

        if (node.word) {
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        node.word = true;
        node.value = value;
        size ++;
        return null;
    }

    public V remove(String str) {
        return null;
    }
    public boolean startWith(String prefix) {
        return false;
    }

    private Node<V> node(String key) {
        keyCheck(key);
        Node<V> node = root;
        int length = key.length();
        for (int i = 0; i < length; i++) {
            char c = key.charAt(i);
            node = node.getChildren().get(c);
            if (node == null) { return null; }
        }

        return node.word ? node : null;
    }

    private void keyCheck(String key) {
        if (null == key || key.length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }

    private static class Node<V> {
        V value;
        boolean word;
        private HashMap<Character, Node<V>> children;

        public HashMap<Character, Node<V>> getChildren() {
            return children == null ? (children = new HashMap<>()) : children;
        }
    }

}
