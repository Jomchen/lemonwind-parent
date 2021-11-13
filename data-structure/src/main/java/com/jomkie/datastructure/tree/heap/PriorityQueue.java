package com.jomkie.datastructure.tree.heap;

import java.util.Comparator;

/**
 * 优先级队列
 */
public class PriorityQueue<E> {
    private BinaryHeap<E> heap;

    public PriorityQueue() {
        this(null);
    }

    public PriorityQueue(Comparator<E> comparator) {
         this.heap = new BinaryHeap<>(comparator);
    }

    public int size() {
        return heap.size;
    }

    public boolean isEmpty() {
        return heap.size == 0;
    }

    public void clear() {
        heap.clear();
    }
    public void enQueue(E element) {
        heap.add(element);
    }

    public E deQueue() {
        return heap.remove();
    }

    public E front() {
        return heap.get();
    }

}
