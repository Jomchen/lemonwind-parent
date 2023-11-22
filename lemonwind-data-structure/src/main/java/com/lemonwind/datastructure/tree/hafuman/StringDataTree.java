package com.lemonwind.datastructure.tree.hafuman;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 一个数据为字符串的二叉树实现
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StringDataTree extends TreeNodeData<String> {

    private Integer code;
    private String data;

    @Override
    public int getCode() {
        return this.code.intValue();
    }

    @Override
    public void printLog() {
        System.out.println(String.format("<code-data> : <%s-%s>", getCode(), this.data));
    }

}
