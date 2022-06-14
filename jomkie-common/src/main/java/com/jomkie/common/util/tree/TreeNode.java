package com.jomkie.common.util.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jomkie
 * @param <T> 数据类型
 * @param <ID> 数据唯一标志
 */
public abstract class TreeNode<T, ID> {

    /** data */
    T data;
    
    /** children */
    List<TreeNode<T, ID>> children;
    
    
    public TreeNode() {}
    
    public TreeNode(T data) {
        this.data = data;
    }
    
    
    /**
     * @return 获取数据的唯一标志
     */
    public abstract ID getId();
    
    /**
     * 建立子关系
     * @param children 子节点
     */
    public void injectChildren(List<TreeNode<T, ID>> children) {
        if (null == children || children.isEmpty()) { return; }
        
        if (null == this.children) {
            this.children = new ArrayList<>();
            this.children.addAll(children);
        }
    }
    
}
