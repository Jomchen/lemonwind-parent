package com.jomkie.datastructure.tree.trie;

import java.util.HashMap;

/**
 * 又被称为前缀树 Prefix Tree
 */
public class Trie<V> {

    private int size;
    private Node<V> root;

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public void clear() {
        size = 0;
        root = null;
    }

    public V get(String str) {
        Node<V> node = node(str);
        return node != null && node.word ? node.value : null;
    }

    public boolean contains(String str) {
        Node<V> node = node(str);
        return null != node && node.word;
    }

    public V add(String str, V value) {
        keyCheck(str);
        if (root == null) { root = new Node<>(); }
        int length = str.length();
        Node<V> node = root;
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            boolean emptyChildren = node.children == null;
            Node<V> childNode = emptyChildren ? null : node.children.get(c);
            if (null == childNode) {
                childNode = new Node<>();
                node.children = emptyChildren ? new HashMap<>() : node.children;
                node.children.put(c, childNode);
            }
            node = childNode;
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
        return node(prefix) != null;
    }

    private Node<V> node(String key) {
        keyCheck(key);
        Node<V> node = root;
        int length = key.length();
        for (int i = 0; i < length; i++) {
            if (node == null
                    || node.children == null
                    || node.children.isEmpty()) {
                return null;
            }
            char c = key.charAt(i);
            node = node.children.get(c);
        }

        return node;
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
    }

}
