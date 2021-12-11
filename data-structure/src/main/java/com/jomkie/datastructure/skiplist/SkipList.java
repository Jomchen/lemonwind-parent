package com.jomkie.datastructure.skiplist;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 跳表
 * 在有序链表的基础上增加了“跳跃跃”的功能
 * 设计的初衷是为了取代平衡树（比如红黑树）
 * Redis 中的 SortedSet，LevelDB 中的 MemTable 都用到了跳表
 * Redis, LevelDB 都是著名的 Key-Value 数据库
 *
 * 对比平衡树
 *     跳表的实现和维护会更加简单
 *     跳表的搜索，删除，添加的平均时间复杂度是 O(logn)
 * 查询原理，查询 key
 *     从顶层链表的首元素开始，从左往右搜索，直到找到一个大于或等于目标的元素，或者到达当前层链表的尾部
 *     如果该元素等于目标元素，则表示元素已被找到
 *     如果该元素大于目标元素或已到达链表的尾部，则退回到当前层的前一个元素，然后转入下一层进行搜索
 */
public class  SkipList<K, V> {

    private int size;
    private Comparator<K> comparator;
    private Node<K, V> first;
    private static final double P = 0.25;

    /** 有效层，最低层索引为0 */
    private int level;
    /** 一般设置跳表的最高高度为 32 层 */
    private static final int MAX_LEVEL = 32;

    public SkipList() {
        this(null);
    }

    public SkipList(Comparator<K> comparator) {
        this.comparator = comparator;
        this.first = new Node(null, null, MAX_LEVEL);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V put(K key, V value) {
        keyCheck(key);

        Node<K, V> node = first;
        Node<K, V>[] prevs = new Node[level];
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1;
            while (node.nexts[i] != null
                    && (cmp = compare(key, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }
            if (cmp == 0) {
                V oldV = node.nexts[i].value;
                node.nexts[i].value = value;
                return oldV;
            }
            prevs[i] = node;
        }

        int newLevel = randomLevel();
        Node<K, V> newNode = new Node<>(key, value, newLevel);
        for (int i = 0; i < newLevel; i++) {
            if (i >= level) {
                first.nexts[i] = newNode;
            } else {
                newNode.nexts[i] = prevs[i].nexts[i];
                prevs[i].nexts[i] = newNode;
            }
        }
        size++;
        level = Math.max(level, newLevel);
        return null;
    }

    public V get(K key) {
        keyCheck(key);
        Node<K, V> node = first;
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1;
            while (node.nexts[i] != null
                            && (cmp = compare(key, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }

            // node.nexts[i].key >= key
            if (cmp == 0) { return node.nexts[i].value; }
        }

        return null;
    }

    public V remove(K key) {
        keyCheck(key);

        Node<K, V> node = first;
        Node<K, V>[] prevs = new Node[level];
        boolean exist = false;
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1;
            while (node.nexts[i] != null
                    && (cmp = compare(key, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }
            prevs[i] = node;
            if (cmp == 0) { exist = true; }
        }
        if (!exist) { return null; }

        // 需要被删除的节点
        Node<K, V> removedNode = node.nexts[0];
        // 数量减少
        size--;

        // 设置后继节点
        for (int i = 0; i < removedNode.nexts.length; i++) {
            prevs[i].nexts[i] = removedNode.nexts[i];
        }

        // 更新跳表的层数，从以前的层数进行层为数遍历，判断相应层是否有下一个节点
        int newLevel = level;
        while (--newLevel >= 0 && first.nexts[newLevel] == null) {
            level = newLevel;
        }

        return removedNode.value;
    }

    /** 随机算出新节点的层级数 */
    private int randomLevel() {
        int level = 1;
        // 生成 [0, 1) 的小数
        while (Math.random() < P && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    private void keyCheck(K key) {
        if (null == key) {
            throw new IllegalArgumentException("key must not be null");
        }
    }

    private int compare(K k1, K k2) {
        return comparator != null
            ? comparator.compare(k1, k2)
            : ((Comparable<K>)k1).compareTo(k2);
    }

    private static class Node<K, V> {
        static final Node[] EMPTY_NODE = {};
        K key;
        V value;
        Node<K, V>[] nexts = EMPTY_NODE;

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            this.nexts = new Node[level];
        }

        @Override
        public String toString() {
            return key + ":" + value + "_" + nexts.length;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("一共" + level + "层").append("\n");
        for (int i = level - 1; i >= 0; i--) {
            Node<K, V> node = first;
            while (node.nexts[i] != null) {
                sb.append(node.nexts[i].key);
                sb.append(" ");
                node = node.nexts[i];
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
