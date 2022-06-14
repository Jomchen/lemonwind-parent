package com.jomkie.common.util.tree;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 目录树工具（此功能适用于森林和二叉树）
 * 已处理环形的异常节点
 * @param <Obj> 组成树的元素
 * @param <ID> 元素自己的唯一标识
 * @author Jomkie
 * @since 2021-06-01 16:44:0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TreeTool<Obj, ID> {

    /** 通过元素获取自己的唯一标识 */
    private Function<Obj, ID> idFun;

    /** 通过父级标识获取直接子级元素集合 */
    private Function<ID, List<Obj>> childrenByParentIdFun;

    /** 将子级元素集合注入到父级元素中 */
    private BiConsumer<Obj, List<Obj>> injectChildrenFun;

    /** 通过一个对象获取父级对象 */
    private Function<Obj, Obj> parentObjFun;

    /** 对一个元素逻辑处理 */
    private Consumer<Obj> consumer;

    public TreeTool(Function<Obj, ID> idFun,
                    Function<ID, List<Obj>> childrenByParentIdFun,
                    BiConsumer<Obj, List<Obj>> injectChildrenFun) {
        this.idFun = idFun;
        this.childrenByParentIdFun = childrenByParentIdFun;
        this.injectChildrenFun = injectChildrenFun;
    }
    
    // TODO 这些方法还缺一个层级数和对象作为参数的处理实现未解决

    /**
     * 获取拥有指定层数的完整树
     * 假设下例为原数据结构：
     *           A
     *         /   \
     *       B     C
     *     / \    / \
     *  D   E  F  G
     *  如果 originalRootList  中有[B,C]，那么 method(2, originalRootList) 执行结果为：
     *       B     C
     *     / \    / \
     *  D   E  F  G
     * @author Jomkie
     * @since 2021-06-01 16:45:12
     * @param depth 树的深度，最少为 1
     * @param originalRootList 树的某层元素集合
     * @return 树结构
     */
    public List<Obj> getTree(int depth, List<Obj> originalRootList) {
        if (Objects.isNull(idFun)
                || Objects.isNull(childrenByParentIdFun)
                || Objects.isNull(injectChildrenFun)) {
            throw new RuntimeException("Building conditions are not complete");
        }
        if (depth <= 0) { throw new RuntimeException("The depth of tree is at least 1"); }
        if (isEmpty(originalRootList)) {
            return Collections.EMPTY_LIST;
        }

        List<Obj> rootList = new LinkedList<>();
        Queue<Obj> queue = new LinkedList<>();
        originalRootList.forEach(obj -> {
            rootList.add(obj);
            queue.add(obj);
        });

        int currentDepth = 1;
        int numbersForCurrentLayer = rootList.size();
        Set<ID> beingExistSet = new HashSet<>();
        while (isNotEmpty(queue)) {
            if (currentDepth >= depth) { return rootList; }

            Obj currentObj = queue.poll();
            ID idOfItSelf = idFun.apply(currentObj);
            if (beingExistSet.contains(idOfItSelf)) {
                throw new RuntimeException("data exception, a node has been exist " + idOfItSelf.toString());
            } else {
                beingExistSet.add(idOfItSelf);
            }

            List<Obj> children = childrenByParentIdFun.apply(idOfItSelf);
            if (isNotEmpty(children)) {
                children.forEach(queue::add);
                injectChildrenFun.accept(currentObj, children);
            }

            --numbersForCurrentLayer;
            if (numbersForCurrentLayer <= 0) {
                currentDepth++;
                numbersForCurrentLayer = queue.size();
            }
        }

        return rootList;
    }

    /**
     *  获取树中某一层的元素集合
     *  假设下例为原数据结构：
     *           A
     *         /   \
     *       B     C
     *     / \    / \
     *  D   E  F  G
     *  如果 originalRootList  中只有A，那么 method(3, originalRootList) 执行结果为 [D,E,F,G]
     * @author Jomkie
     * @since 2021-06-02 11:50:56
     * @param depth 树的深度，至少为 1
     * @param originalRootList 树的某层元素集合
     * @return 指定层的节点集合
     */
    public List<Obj> getObjListOfSpecificLayer(int depth, List<Obj> originalRootList) {
        if (Objects.isNull(idFun) || Objects.isNull(childrenByParentIdFun)) {
            throw new RuntimeException("Building conditions are not complete");
        }
        if (depth <= 0) { throw new RuntimeException("The depth of tree is at least 1"); }
        if (isEmpty(originalRootList)) {
            return Collections.EMPTY_LIST;
        }

        Queue<Obj> queue = new LinkedList<>();
        originalRootList.forEach(queue::add);

        int currentDepth = 1;
        int numbersForCurrentLayer = queue.size();
        Set<ID> beingExistSet = new HashSet<>();
        while (isNotEmpty(queue)) {
            if (currentDepth >= depth) { return new ArrayList<>(queue); }

            Obj currentObj = queue.poll();
            ID idOfItSelf = idFun.apply(currentObj);
            if (beingExistSet.contains(idOfItSelf)) {
                throw new RuntimeException("data exception, a node has been exist " + idOfItSelf.toString());
            } else {
                beingExistSet.add(idOfItSelf);
            }

            List<Obj> children = childrenByParentIdFun.apply(idOfItSelf);
            if (isNotEmpty(children)) {
                children.forEach(queue::add);
            }

            --numbersForCurrentLayer;
            if (numbersForCurrentLayer <= 0) {
                currentDepth++;
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
     * @return 当前节点到顶级节点的节点集合
     */
    public List<Obj> getForefatherChain2(Obj obj) {
        if (Objects.isNull(parentObjFun) || Objects.isNull(idFun)) {
            throw new RuntimeException("both <getParentObjFun> and <getIDOfItSelfFun> must be not null");
        }
        if (Objects.isNull(obj)) {
            return Collections.EMPTY_LIST;
        }

        Obj temporary = obj;
        List<Obj> queue = new LinkedList<>();
        Set<ID> idSet = new HashSet<>();
        while (Objects.nonNull(temporary)) {
            ID idOfItSelf = idFun.apply(temporary);
            if (idSet.contains(idOfItSelf)) {
                throw new RuntimeException("data exception, a node has been exist " + idOfItSelf.toString());
            } else {
                idSet.add(idOfItSelf);
            }

            queue.add(temporary);
            temporary = parentObjFun.apply(temporary);
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
     * @return 本节点作为根节点的整棵树的节点集合
     * @since 2021-08-04 09:38:02
     * @param depth 深度
     * @param parentList 某层元素集合
     */
    public List<Obj> getAllObjForSpecificDepth(int depth, List<Obj> parentList) {
        if (isEmpty(parentList)) { return Collections.EMPTY_LIST; }
        if (depth <= 0) { throw new RuntimeException("The depth of tree is at least 1"); }
        if (Objects.isNull(childrenByParentIdFun)
                || Objects.isNull(idFun)) {
            throw new RuntimeException("Building conditions are not complete");
        }

        List<Obj> resultList = new LinkedList<>(parentList);
        Queue<Obj> queue = new LinkedList<>(parentList);
        int currentDepth = 1;
        int numbersForCurrentLayer = queue.size();
        Set<ID> beingExistSet = new HashSet<>();

        while (isNotEmpty(queue)) {
            if (currentDepth >= depth) {
                return resultList;
            }

            Obj currentObj = queue.poll();
            ID idOfItSelf = idFun.apply(currentObj);
            if (beingExistSet.contains(idOfItSelf)) {
                throw new RuntimeException("data exception, a node has been exist " + idOfItSelf.toString());
            } else {
                beingExistSet.add(idOfItSelf);
            }

            List<Obj> children = childrenByParentIdFun.apply(idOfItSelf);
            if (isNotEmpty(children)) {
                resultList.addAll(children);
                queue.addAll(children);
            }

            --numbersForCurrentLayer;
            if (numbersForCurrentLayer <= 0) {
                currentDepth++;
                numbersForCurrentLayer = queue.size();
            }
        }

        return resultList;
    }

    /**
     * 遍历整棵树的每个元素，遍历的同时根据实现消费元素
     * @author Jomkie
     * @since 2021-08-04 11:34:21
     * @param originalList 某层元素集合
     */
    public void consumeEachObj(List<Obj> originalList) {
        if (Objects.isNull(idFun)
                || Objects.isNull(childrenByParentIdFun)
                || Objects.isNull(consumer)) {
            throw new RuntimeException("Building conditions are not complete");
        }

        if (isEmpty(originalList)) { return; }
        Queue<Obj> queue = new LinkedList<>(originalList);
        Set<ID> beingExistSet = new HashSet<>();
        while (isNotEmpty(queue)) {
            Obj currentObj = queue.poll();
            consumer.accept(currentObj);

            ID idOfItSelf = idFun.apply(currentObj);
            if (beingExistSet.contains(idOfItSelf)) {
                throw new RuntimeException("data exception, a node has been exist " + idOfItSelf.toString());
            } else {
                beingExistSet.add(idOfItSelf);
            }

            List<Obj> children = childrenByParentIdFun.apply(idOfItSelf);
            if (isNotEmpty(children)) {
                queue.addAll(children);
            }
        }
    }

    public boolean isEmpty(Collection<Obj> dataList) {
        return null == dataList || dataList.isEmpty();
    }
    
    public boolean isNotEmpty(Collection<Obj> dataList) {
        return null != dataList && !dataList.isEmpty();
    }

}
