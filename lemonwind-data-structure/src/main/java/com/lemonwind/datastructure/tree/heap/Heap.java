package com.lemonwind.datastructure.tree.heap;

public interface Heap<E> {

    int size();
    boolean isEmpty();
    void clear();
    void add(E element);
    E get();
    E remove();
    /** 删除堆顶元素，并将新元素添加到堆顶，最后调整堆 */
    E replace(E element);

}
