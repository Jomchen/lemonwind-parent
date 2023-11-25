package com.lemonwind.common.util.tree;

import java.util.List;

/**
 * 子集注入
 * @param <Obj>
 */
@FunctionalInterface
public interface InjectChildrenConsumer<Obj> {

    /**
     * 注入子集
     * @param layer obj对象所处的节点层数
     * @param obj 父级对象
     * @param children 子级对象
     */
    void inject(Integer layer, Obj obj, List<Obj> children);

}
