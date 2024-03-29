package com.lemonwind.datastructure.tree.heap;

import com.lemonwind.common.util.treeprint.BinaryTreeInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 * 二叉堆（Binary Heap）
 * 鉴于完全二叉树的特性，二叉堆的底层（物理结构）一般用数组实现即可
 * 二叉堆并不保证某一层的数据一定全部 大于/小于 下一层的数据
 *
 * 数组大小为 n，数组元素索引从 0 ~ n-1，依次是完全二叉树的层序遍历结果
 * 某个元素的索引为 i，且 i > 0，它的父节点索引为 floor((i - 1) / 2)
 * 如果 2i + 1 <= n - 1，它的左子节点索引为 2i + 1
 * 如果 2i + 1 > n - 1，它无左子节点
 * 如果 2i + 2 <= n - 1，它的右子节点索引为 2i + 2
 * 如果 2i + 2 > n - 1，它无右子节点
 *
 * 这个类默认是最大堆
 */
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {

    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap() {
        this((E[])null, null);
    }

    public BinaryHeap(E[] elements) {
        this(elements, null);
    }

    public BinaryHeap(Comparator<E> comparator) {
        super(comparator);
        elements = (E[])new Object[DEFAULT_CAPACITY];
    }

    public BinaryHeap(Collection<E> elements, Comparator<E> comparator) {
        super(comparator);
        size = elements == null ? 0 : elements.size();
        if (size == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            int capacity = Math.max(size, DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            int i = 0;
            for (E element : elements) {
                this.elements[i++] = element;
            }
            heapiry();
        }
    }

    public BinaryHeap(E[] elements, Comparator<E> comparator) {
        super(comparator);
        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            size = elements.length;
            int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                this.elements[i] = elements[i];
            }
            heapiry();
        }
    }

    @Override
    public void clear() {
        size = 0;
        Arrays.fill(elements, 0, size, null);
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        elements[size++] = element;
        siftUp(size - 1);
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        emptyCheck();
        E root = elements[0];
        elements[0] = elements[--size];
        elements[size] = null;
        siftDown(0);
        return root;
    }

    @Override
    public E replace(E element) {
        elementNotNullCheck(element);

        E root = null;
        if (size == 0) {
            elements[0] = element;
            size++;
        } else {
            root = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return root;
    }

    private void heapiry() {
        // 自上而下的上滤
        /*for (int i = 1; i < size; i++) {
            siftUp(i);
        }*/

        // 这里选择自下而上的下滤
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * 让 index 位置的元素上滤
     * @param index 元素索引
     */
    private void siftUp(int index) {
        /*E e = elements[index];
        while (index > 0) {
            int parentIndex = (index - 1) >> 1;
            E p = elements[parentIndex];
            if (compare(e, p) <= 0) { return; }

            E tmp = elements[index];
            elements[index] = elements[parentIndex];
            elements[parentIndex] = tmp;
            index = parentIndex;
        }*/

        E element = elements[index];
        while (index > 0) {
            int parentIndex = (index - 1) >> 1;
            E parent = elements[parentIndex];
            if (compare(element, parent) <= 0) { break; }
            elements[index] = parent;
            index = parentIndex;
        }

        elements[index] = element;
    }

    /**
     * 让 index 位置的元素下滤
     * @param index 元素索引
     */
    private void siftDown(int index) {
        E element = elements[index];
        // 必须保证 index 节点是非叶子节点，只要索引小于第一个非叶子节点即可
        // 第一个叶子节点的索引刚好是非叶子节点的数量
        int half = size >> 1;
        while (index < half) {
            // 非叶子节点有两种情况：1. 只有左子节点 2. 有左右两个节点
            int childIndex = (index << 1) + 1;
            E child = elements[childIndex];

            // 因为是大顶堆（某节点一定比子节点大），所以要从子节点中找更大的节点，和父节点进行比较
            int rightIndex = childIndex + 1;
            if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
                child = elements[childIndex = rightIndex];
            }

            if (compare(element, child) >= 0) { break; }

            // 因为是大顶堆，子节点中最大的还要比父节点大，所以交换位置，再对下溢后的父节点继续作下溢操作
            elements[index] = child;
            index = childIndex;
        }

        elements[index] = element;
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) { return; }

        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("heap is empty");
        }
    }

    private void elementNotNullCheck(E element) {
        if (null == element) {
            throw new IllegalArgumentException("element must not be empty");
        }
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        Integer index = (Integer) node;
        index = (index << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        Integer index = (Integer) node;
        index = (index << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        Integer index = (Integer) node;
        return elements[index];
    }

}
