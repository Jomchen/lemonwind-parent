package com.jomkie.util;

import com.jomkie.common.LemonException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Jomkie
 * @since 2021-06-01 16:44:0
 * <Obj> 一个树的元素
 * <Identifier> 元素自己的唯一标识
 * 获取目录树
 */
@NoArgsConstructor
@Data
public class TreeTool<Obj, Identifier> {

    /** 通过对象获取自己的唯一标识 */
    Function<Obj, Identifier> acquireIdentifierOfItSelfFunction;

    /** 通过父级标识获取对应的子级对象集合 */
    Function<Identifier, List<Obj>> acquireChildrenByParentIdentifierFunction;

    /** 为对象设置子级对象 */
    BiConsumer<Obj, List<Obj>> setChildrenFunction;

    public TreeTool(Function<Obj, Identifier> acquireIdentifierOfItSelfFunction,
                    Function<Identifier, List<Obj>> acquireChildrenByParentIdentifierFunction,
                    BiConsumer<Obj, List<Obj>> setChildrenFunction) {
        this.acquireIdentifierOfItSelfFunction = acquireIdentifierOfItSelfFunction;
        this.acquireChildrenByParentIdentifierFunction = acquireChildrenByParentIdentifierFunction;
        this.setChildrenFunction = setChildrenFunction;
    }

    /**
     * @author Jomkie
     * @since 2021-06-01 16:45:12
     * @param depth 树的深度，最少为 1
     * @param originalRootList 树的顶级元素集合
     * 层序遍历获取树
     */
    public List<Obj> getTree(int depth, List<Obj> originalRootList) {
        if (Objects.isNull(acquireIdentifierOfItSelfFunction)
                || Objects.isNull(acquireChildrenByParentIdentifierFunction)
                || Objects.isNull(setChildrenFunction)) {
            throw new LemonException("Building conditions are not complete.");
        }
        if (depth <= 0) { throw new LemonException("The depth of tree is at least 1"); }
        if (CollectionUtils.isEmpty(originalRootList)) { throw new LemonException("Root element must be not empty."); }

        List<Obj> rootList = new LinkedList<>();
        Queue<Obj> queue = new LinkedList<>();
        originalRootList.forEach(obj -> {
            rootList.add(obj);
            queue.add(obj);
        });

        int currentDepth = 1;
        int numbersForCurrentLayer = rootList.size();
        while ( ! CollectionUtils.isEmpty(queue)) {
            if (currentDepth >= depth) { return rootList; }

            Obj currentObj = queue.poll();
            Identifier identifierOfItSelf = acquireIdentifierOfItSelfFunction.apply(currentObj);
            List<Obj> children = acquireChildrenByParentIdentifierFunction.apply(identifierOfItSelf);
            if ( ! CollectionUtils.isEmpty(children)) {
                children.forEach(queue::add);
                setChildrenFunction.accept(currentObj, children);
            }

            -- numbersForCurrentLayer;
            if (numbersForCurrentLayer <= 0) {
                currentDepth ++;
                numbersForCurrentLayer = queue.size();
            }
        }

        return rootList;
    }

    /**
     * @author Jomkie
     * @since 2021-06-02 11:50:56
     * @param depth 树的深度，至少为 1
     * @param originalRootList 树的顶级元素集合
     * 在树中查找指定层的元素集合
     */
    public List<Obj> getObjListOfSpecificLayer(int depth, List<Obj> originalRootList) {
        if (Objects.isNull(acquireIdentifierOfItSelfFunction) || Objects.isNull(acquireChildrenByParentIdentifierFunction)) {
            throw new LemonException("Building conditions are not complete.");
        }
        if (depth <= 0) { throw new LemonException("The depth of tree is at least 1"); }
        if (CollectionUtils.isEmpty(originalRootList)) { throw new LemonException("Root element must be not empty."); }

        Queue<Obj> queue = new LinkedList<>();
        originalRootList.forEach(queue::add);

        int currentDepth = 1;
        int numbersForCurrentLayer = queue.size();
        while ( ! CollectionUtils.isEmpty(queue)) {
            if (currentDepth >= depth) { return new ArrayList<>(queue); }

            Obj currentObj = queue.poll();
            Identifier identifierOfItSelf = acquireIdentifierOfItSelfFunction.apply(currentObj);
            List<Obj> children = acquireChildrenByParentIdentifierFunction.apply(identifierOfItSelf);
            if ( ! CollectionUtils.isEmpty(children)) {
                children.forEach(queue::add);
            }

            -- numbersForCurrentLayer;
            if (numbersForCurrentLayer <= 0) {
                currentDepth ++;
                numbersForCurrentLayer = queue.size();
            }
        }

        return new ArrayList<>(queue);
    }

}
