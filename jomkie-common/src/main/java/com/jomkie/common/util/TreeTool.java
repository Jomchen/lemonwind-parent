package com.jomkie.common.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 目录树工具（此功能适用于森林和二叉树）
 * 注意元素不要出现 相互层级关系，否则会无限循环
 * <Obj> 组成树的元素
 * <Identifier> 元素自己的唯一标识
 * @author Jomkie
 * @since 2021-06-01 16:44:0
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

    /** 获取父级对象 */
    private Function<Obj, Obj> getParentObjFun;

    /** 对一个元素逻辑处理 */
    private Consumer<Obj> consumer;

    public TreeTool(Function<Obj, Identifier> getIdentifierOfItSelfFun,
                    Function<Identifier, List<Obj>> getChildrenByParentIdentifierFun,
                    BiConsumer<Obj, List<Obj>> setChildrenFun) {
        this.getIdentifierOfItSelfFun = getIdentifierOfItSelfFun;
        this.getChildrenByParentIdentifierFun = getChildrenByParentIdentifierFun;
        this.setChildrenFun = setChildrenFun;
    }

    /**
     * 获取拥有指定层数的完整树
     *           A
     *         /   \
     *       B     C
     *     / \    / \
     *  D   E  F  G
     *  如果 originalRootList  中只有A，method(3, originalRootList) 那么执行结果为图示的整棵树
     * @author Jomkie
     * @since 2021-06-01 16:45:12
     * @param depth 树的深度，最少为 1
     * @param originalRootList 树的顶级节点集合
     */
    public List<Obj> getTree(int depth, List<Obj> originalRootList) {
        if (Objects.isNull(getIdentifierOfItSelfFun)
                || Objects.isNull(getChildrenByParentIdentifierFun)
                || Objects.isNull(setChildrenFun)) {
            throw new RuntimeException("Building conditions are not complete.");
        }
        if (depth <= 0) { throw new RuntimeException("The depth of tree is at least 1"); }
        if (isEmpty(originalRootList)) { throw new RuntimeException("Root element must be not empty."); }

        List<Obj> rootList = new LinkedList<>();
        Queue<Obj> queue = new LinkedList<>();
        originalRootList.forEach(obj -> {
            rootList.add(obj);
            queue.add(obj);
        });

        int currentDepth = 1;
        int numbersForCurrentLayer = rootList.size();
        while ( ! isEmpty(queue)) {
            if (currentDepth >= depth) { return rootList; }

            Obj currentObj = queue.poll();
            Identifier identifierOfItSelf = getIdentifierOfItSelfFun.apply(currentObj);
            List<Obj> children = getChildrenByParentIdentifierFun.apply(identifierOfItSelf);
            if ( ! isEmpty(children)) {
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
     *  获取树中某一层的元素集合
     *           A
     *         /   \
     *       B     C
     *     / \    / \
     *  D   E  F  G
     *  如果 originalRootList  中只有A，method(3, originalRootList) 那么执行结果为 [D,E,F,G]
     * @author Jomkie
     * @since 2021-06-02 11:50:56
     * @param depth 树的深度，至少为 1
     * @param originalRootList 树的顶级节点集合
     */
    public List<Obj> getObjListOfSpecificLayer(int depth, List<Obj> originalRootList) {
        if (Objects.isNull(getIdentifierOfItSelfFun) || Objects.isNull(getChildrenByParentIdentifierFun)) {
            throw new RuntimeException("Building conditions are not complete.");
        }
        if (depth <= 0) { throw new RuntimeException("The depth of tree is at least 1"); }
        if (isEmpty(originalRootList)) { throw new RuntimeException("Root element must be not empty."); }

        Queue<Obj> queue = new LinkedList<>();
        originalRootList.forEach(queue::add);

        int currentDepth = 1;
        int numbersForCurrentLayer = queue.size();
        while ( ! isEmpty(queue)) {
            if (currentDepth >= depth) { return new ArrayList<>(queue); }

            Obj currentObj = queue.poll();
            Identifier identifierOfItSelf = getIdentifierOfItSelfFun.apply(currentObj);
            List<Obj> children = getChildrenByParentIdentifierFun.apply(identifierOfItSelf);
            if ( ! isEmpty(children)) {
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


    /**
     * 通过一个元素，获取到顶级元素形成的链
     *       A
     *      /\
     *    B  C
     *   /
     *  D
     *  method(D)，那么执行结果为：[D,B,A]
     * @author Jomkie
     * @since 2021-06-09 11:47:59
     * @param obj 一个元素
     */
    public List<Obj> getForefatherChain(Obj obj) {
        if (Objects.isNull(getParentObjFun)) {
            throw new RuntimeException("The condition with  <getParentObjFun> must be not null");
        }
        if (Objects.isNull(obj)) {
            throw new RuntimeException("The element must be not null");
        }

        Obj temporary = obj;
        List<Obj> queue = new LinkedList<>();
        while (Objects.nonNull(temporary)) {
            queue.add(temporary);
            temporary = getParentObjFun.apply(temporary);
        }

        return queue;
    }

    /**
     * 通过父级元素集合获取指定深度内的后代元素
     *           A
     *         /   \
     *       B     C
     *     / \    / \
     *  D   E  F  G
     *  如果 parentList  中只有A，method(3, parentList) 那么执行结果为 [A,B,C,D,E,F,G]
     * @author Jomkie
     * @since 2021-08-04 09:38:02
     * @param depth 深度
     * @param parentList 父集元素集合
     */
    public List<Obj> getAllObjForSpecificDepth(int depth, List<Obj> parentList) {
        if (isEmpty(parentList)) { return Collections.EMPTY_LIST; }
        if (depth <= 0) { throw new RuntimeException("The depth of tree is at least 1"); }
        if (Objects.isNull(getChildrenByParentIdentifierFun)
                || Objects.isNull(getIdentifierOfItSelfFun)) {
            throw new RuntimeException("Building conditions are not complete");
        }

        List<Obj> resultList = new LinkedList<>(parentList);
        Queue<Obj> queue = new LinkedList<>(parentList);
        int currentDepth = 1;
        int numbersForCurrentLayer = queue.size();

        while ( ! queue.isEmpty()) {
            if (currentDepth >= depth) {
                return resultList;
            }

            Obj currentObj = queue.poll();
            Identifier identifierOfItSelf = getIdentifierOfItSelfFun.apply(currentObj);
            List<Obj> children = getChildrenByParentIdentifierFun.apply(identifierOfItSelf);
            if ( ! isEmpty(children)) {
                resultList.addAll(children);
                queue.addAll(children);
            }

            -- numbersForCurrentLayer;
            if (numbersForCurrentLayer <= 0) {
                currentDepth ++;
                numbersForCurrentLayer = queue.size();
            }
        }

        return resultList;
    }

    /**
     * 从父级元素开始到所有的后代元素进行逻辑处理
     * @author Jomkie
     * @since 2021-08-04 11:34:21
     * @param originalList 父级元素
     */
    public void consumeEachObj(List<Obj> originalList) {
        if (Objects.isNull(getIdentifierOfItSelfFun)
                || Objects.isNull(getChildrenByParentIdentifierFun)
                || Objects.isNull(consumer)) {
            throw new RuntimeException("Building conditions are not complete");
        }

        if (isEmpty(originalList)) { return; }
        Queue<Obj> queue = new LinkedList<>(originalList);
        while ( ! queue.isEmpty()) {
            Obj currentObj = queue.poll();
            consumer.accept(currentObj);

            Identifier identifierOfItSelf = getIdentifierOfItSelfFun.apply(currentObj);
            List<Obj> children = getChildrenByParentIdentifierFun.apply(identifierOfItSelf);
            if ( ! isEmpty(children)) {
                queue.addAll(children);
            }
        }
    }

    private boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

}
