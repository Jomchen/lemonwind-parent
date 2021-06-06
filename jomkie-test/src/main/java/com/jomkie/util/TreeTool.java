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
 * <Obj> 一个树的节点类型
 * <Identifier> 节点的唯一标识
 * 获取目录树
 */
@NoArgsConstructor
@Data
public class TreeTool<Obj, Identifier> {

    /** 通过节点获取自己的唯一标识 */
    Function<Obj, Identifier> getIdentifierOfItSelfFun;

    /** 通过父级标识获取直接子级节点集合 */
    Function<Identifier, List<Obj>> getChildrenByParentIdentifierFun;

    /** 为对象设置子级节点集合 */
    BiConsumer<Obj, List<Obj>> setChildrenFun;

    public TreeTool(Function<Obj, Identifier> getIdentifierOfItSelfFun,
                    Function<Identifier, List<Obj>> getChildrenByParentIdentifierFun,
                    BiConsumer<Obj, List<Obj>> setChildrenFun) {
        this.getIdentifierOfItSelfFun = getIdentifierOfItSelfFun;
        this.getChildrenByParentIdentifierFun = getChildrenByParentIdentifierFun;
        this.setChildrenFun = setChildrenFun;
    }

    /**
     * @author Jomkie
     * @since 2021-06-01 16:45:12
     * @param depth 树的深度，最少为 1
     * @param originalRootList 树的顶级节点集合
     * 层序遍历获取指定深度的树
     */
    public List<Obj> getTree(int depth, List<Obj> originalRootList) {
        if (Objects.isNull(getIdentifierOfItSelfFun)
                || Objects.isNull(getChildrenByParentIdentifierFun)
                || Objects.isNull(setChildrenFun)) {
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
            Identifier identifierOfItSelf = getIdentifierOfItSelfFun.apply(currentObj);
            List<Obj> children = getChildrenByParentIdentifierFun.apply(identifierOfItSelf);
            if ( ! CollectionUtils.isEmpty(children)) {
                children.forEach(queue::add);
                setChildrenFun.accept(currentObj, children);
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
     * @param originalRootList 树的顶级节点集合
     * 获取树中指定层的节点集合
     */
    public List<Obj> getObjListOfSpecificLayer(int depth, List<Obj> originalRootList) {
        if (Objects.isNull(getIdentifierOfItSelfFun) || Objects.isNull(getChildrenByParentIdentifierFun)) {
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
            Identifier identifierOfItSelf = getIdentifierOfItSelfFun.apply(currentObj);
            List<Obj> children = getChildrenByParentIdentifierFun.apply(identifierOfItSelf);
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
