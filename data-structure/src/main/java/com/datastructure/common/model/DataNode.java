package com.datastructure.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DataNode {

    public Integer data;

    public DataNode next;

    public DataNode(Integer data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataNode{" +
                "data=" + data +
                '}';
    }
}
