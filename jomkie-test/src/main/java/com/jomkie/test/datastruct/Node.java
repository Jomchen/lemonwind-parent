package com.jomkie.test.datastruct;

import lombok.Data;

@Data
public class Node<T> {

    private T data;
    private Node<T> next;

}
