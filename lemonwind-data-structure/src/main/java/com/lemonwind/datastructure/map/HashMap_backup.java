package com.lemonwind.datastructure.map;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class HashMap_backup<K, V> implements Map<K, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private Node<K, V>[] table;
    private static final int DEFAULT_CAPACITY = 1 << 4;

    public HashMap_backup() {
        table = new Node[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if (size == 0) { return; }
        Arrays.fill(table, null);
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        int index = index(key);
        Node<K, V> root = table[index];
        if (root == null) {
            root = new Node<>(key, value, null);
            table[index] = root;
            size++;
            afterPut(root);
            return null;
        }

        // 添加新的节点到红黑树上
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = key;
        int h1 = k1 == null ? 0 : k1.hashCode();
        Node<K, V> result;
        boolean searched = false; // 是否已经搜索过
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (Objects.equals(k1, k2)) {
                cmp = 0;
            } else if (k1 != null && k2 != null
                && k1.getClass() == k2.getClass()
                && k1 instanceof Comparable
                && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
                // 什么都不做，防止 compare 相同，但是 equals 不同的情况（对于 key 实现了 Comparable 的情况）
            } else if (searched) { // 已经扫描了
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            } else { // searched == false; 还没有扫描，然后再根据内存地址大小决定左右
                // 先扫描，然后根据内存地址大小决定左右
                if (node.left != null && (result = node(node.left, k1)) != null
                        || (node.right != null && (result = node(node.right, k1)) != null)) {
                    // 已经存在这个 key
                    node = result;
                    cmp = 0;
                } else { // 不存在这个 key
                    searched = true;
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                }
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                V oldValue = node.value;
                node.key = key;
                node.value = value;
                //node.hash = h1; 这里可以不用写，因为 node.hash 一定等于 h1
                return oldValue;
            }
        } while (node != null);

        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size ++;

        afterPut(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) { return false; }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) { return false; }
            queue.offer(table[i]);
            while ( !queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(node.value, value)) { return true; }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) { return; }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) { continue; }
            queue.offer(table[i]);
            while ( !queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (visitor.visit(node.key, node.value)) { return; }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
    }

    private Node<K, V> node(K key) {
        Node<K, V> root = table[index(key)];
        return root == null ? null : node(root, key);
    }

    private Node<K, V> node(Node<K, V> node, K k1) {
        int h1 = k1 == null ? 0 : k1.hashCode();
        // 存储查找结果
        Node<K, V> result;
        int cmp = 0;
        while (node != null) {
            K k2 = node.key;
            int h2 = node.hash;
            // 这里不能用 h1 - h2 的结果作为判断，因为两者可能都是比较大的内存地址
            // 如果 h1 是正数，h2 是绝对值很大的负数，结果可能溢出造成结果为负数
            // 从而造成判断错误
            if (h1 > h2) {
                node = node.right;
            } else if (h1 < h2) {
                node = node.left;
            } else if (Objects.equals(k1, k2)) {
                return node;
            } else if (k1 != null && k2 != null
                && k1.getClass() == k2.getClass()
                && k1 instanceof  Comparable
                && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
                node = cmp > 0 ? node.right : node.left;
            } else if (node.right != null && (result = node(node.right, k1)) != null) { // hash 值相等，不具备可比较性，也不equals
                return result;
            } else { // 只能往左边找
                node = node.left;
            }
//            } else if (node.left != null && (result = node(node.left, k1)) != null) {
//                return result;
//            } else {
//                return null;
//            }
        }
        return null;
    }

    private int index(K key) {
        if (key == null) { return 0; }
        int hash = key.hashCode();
        return (hash ^ (hash >>> 16)) & (table.length - 1);
    }

    private int index(Node<K, V> node) {
        return (node.hash ^ (node.hash >>> 16)) & (table.length - 1);
    }

    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        // 如果是根节点或上溢到达了根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack((parent))) { return; }

        // 叔父节点，祖父节点
        Node<K, V> uncle = parent.sibling();
        Node<K, V> grand = red(parent.parent);
        // 叔父节点是红色
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            afterPut(grand);
            return;
        }

        // 叔父节点是黑色
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                black(parent);
            } else { // LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (node.isLeftChild()) { // RL
                black(node);
                rotateRight(parent);
            } else { // RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 右旋转
     * @param grand
     */
    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        // 更新 parent 的 parent
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            // 这里其实是将 grand, parent, child 所在集合在 table 中的位置置为根节点
            // 这三个节点计算的索引一定相同
            table[index(grand)] = parent;
        }

        // 更新 child 的 parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新 grand 的 parent
        grand.parent = parent;
    }

    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;
        return node;
    }

    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<K, V> node) {
        return null == node ? BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    /*
     * @param k1
     * @param k2
     * @param h1 k1的hashCode
     * @param h2 k2的hashCode
     * @return
     */
//    private int compare(K k1, K k2, int h1, int h2) {
//        int result = h1 - h2;
//        if (result != 0) { return result; }
//
//        // 比较 equals 看是否是同一对象
//        if (Objects.equals(k1, k2)) { return 0; }
//
//        // TODO 注意这里要调整
//        // 能来到这行表示：hashCode相等，但是 equals 对比不同
//        if (k1 != null && k2 != null) {
//            // 比较类名
//            String k1Class = k1.getClass().getName();
//            String k2Class = k2.getClass().getName();
//            result = k1Class.compareTo(k2Class);
//            if (result != 0) { return result; }
//
//            // 同类型且具备可比较性
//            if (k1 instanceof Comparable) {
//                return ((Comparable) k1).compareTo(k2);
//            }
//        }
//
//        // 同一种类型，hash值一样，但是不具备可比较性
//        // k1 不为空，k2为空；k1 为空，k2不为空
//        // 使用内存地址相减进行返回 hashCode
//        return System.identityHashCode(k1) - System.identityHashCode(k2);
//    }

    private V remove(Node<K, V> node) {
        if (null == node) { return null; }

        size --;

        V oldValue = node.value;

        if (node.hasTwoChildren()) {
            Node<K, V> s = successor(node);
            node.key = s.key;
            node.value = s.value;
            node.hash = s.hash;
            node = s;
        }

        // 这里 node 的度必然是 0 或 1
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        int index = index(node);
        if (null != replacement) {

            // node 是度为1的节点
            replacement.parent = node.parent;
            if (node.parent == null) {
                table[index] = replacement;
            } else if (node.parent.left == node) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }

            // 删除节点之后的处理
            afterRemove(replacement);
        } else if (node.parent == null) {
            // node 度为0 且是根节点
            table[index] = null;

            // 删除节点之后的处理
            afterRemove(node);
        } else {
            // node 度为0 但不是根节点
            if (node.parent.left == node) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }

            // 删除节点之后的处理
            afterRemove(node);
        }

        return oldValue;
    }

    private void afterRemove(Node<K, V> node) {
        // 如果删除的节点是红色 或者用以取代node的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<K, V> parent =node.parent;
        // 删除的是根节点
        if (parent == null) { return; }

        // 删除的是黑色叶子节点[下溢]
        // 判断被删除的node是左还是右（这种判断才是真正正确的）
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左色，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有一个子节点
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有 1 个子节点
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有一个子节点
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有 1 个子节点
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }
    
    private Node<K, V> successor(Node<K, V> node) {
        if (null == node) { return null; }
        if (null != node.right) {
            Node<K, V> temporary = node.right;
            while (null != temporary.left) {
                temporary = temporary.left;
            }
            return temporary;
        }

        while (null != node.parent && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }
    
    private static class Node<K, V> {
        int hash;
        K key;
        V value;
        boolean color = RED;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.hash = key == null ? 0 : key.hashCode();
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }
        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }
        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }
    }

}
