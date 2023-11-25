package com.lemonwind.common.util.tree;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lemonwind
 * @param <T> 数据类型
 * @param <ID> 数据唯一标志
 */
@Data
@Accessors(chain = true)
public abstract class TreeNode<T, ID> {

    /** layer */
    private Integer layer;

    /** data */
    private T data;
    
    /** children */
    private List<TreeNode<T, ID>> children;

    private InjectChildrenConsumer<TreeNode<T, ID>> injectChildrenConsumer = (layerNum, obj, list) -> obj.setLayer(layerNum).injectChildren(list);

    public TreeNode() {}
    
    public TreeNode(T data) {
        this.data = data;
    }
    
    
    /**
     * @return 获取数据的唯一标志
     */
    public abstract ID getId();
    
    /**
     * 建立父子关系
     * @param children 子节点
     */
    public TreeNode<T, ID> injectChildren(List<TreeNode<T, ID>> children) {
        if (null == children || children.isEmpty()) { return this; }
        
        if (null == this.children) {
            this.children = new ArrayList<>();
            this.children.addAll(children);
        }
        return this;
    }
    
}
