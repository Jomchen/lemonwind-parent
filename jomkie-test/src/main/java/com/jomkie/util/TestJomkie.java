package com.jomkie.util;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 目录树工具（此功能适用于森林和二叉树）
 * 元素不要出现相互依赖的情况，否则会无限循环
 * <Obj> 组成树的元素
 * <Identifier> 元素自己的唯一标识
 * @author Jomkie
 * @since 2021-06-01 16:44:0
 */
public class TestJomkie {

    public static void main(String[] args) {
        List<TestJoUser> database = new LinkedList<>();

        /* ---------- 创建对象 ----------  Start */
        TestJoUser root1 = new TestJoUser("1", "-1", "根1");
            TestJoUser child1_1 = new TestJoUser("1_1", "1", "子1_1");
                TestJoUser child1_1_1 = new TestJoUser("1_1_1", "1_1", "子1_1_1");
                TestJoUser child1_1_2 = new TestJoUser("1_1_2", "1_1", "子1_1_2");
            TestJoUser child1_2 = new TestJoUser("1_2", "1", "子1_2");
                TestJoUser child1_2_1 = new TestJoUser("1_2_1", "1_2", "子1_2_1");
                TestJoUser child1_2_2 = new TestJoUser("1_2_2", "1_2", "子1_2_2");

        TestJoUser root2 = new TestJoUser("2", "-1", "根2");
            TestJoUser child2_1 = new TestJoUser("2_1", "2", "子2_1");
                TestJoUser child2_1_1 = new TestJoUser("2_1_1", "2_1", "子2_1_1");
                TestJoUser child2_1_2 = new TestJoUser("2_1_2", "2_1", "子2_1_2");
            TestJoUser child2_2 = new TestJoUser("2_2", "2", "子2_2");
                TestJoUser child2_2_1 = new TestJoUser("2_2_1", "2_2", "子2_2_1");
                TestJoUser child2_2_2 = new TestJoUser("2_2_2", "2_2", "子2_2_2");

        TestJoUser root3 = new TestJoUser("3", "-1", "根3");
            TestJoUser child3_1 = new TestJoUser("3_1", "3", "子3_1");
                TestJoUser child3_1_1 = new TestJoUser("3_1_1", "3_1", "子3_1_1");
                TestJoUser child3_1_2 = new TestJoUser("3_1_2", "3_1", "子3_1_2");
            TestJoUser child3_2 = new TestJoUser("3_2", "3", "子3_2");
                TestJoUser child3_2_1 = new TestJoUser("3_2_1", "3_2", "子3_2_1");
                TestJoUser child3_2_2 = new TestJoUser("3_2_2", "3_2", "子3_2_2");
        /* ---------- 构建对象 ----------  End */

        /* ---------- 构建源数据 ----------  Start */
        database.add(root1);
            database.add(child1_1);
                database.add(child1_1_1);
                database.add(child1_1_2);
            database.add(child1_2);
                database.add(child1_2_1);
                database.add(child1_2_2);

        database.add(root2);
            database.add(child2_1);
                database.add(child2_1_1);
                database.add(child2_1_2);
            database.add(child2_2);
                database.add(child2_2_1);
                database.add(child2_2_2);

        database.add(root3);
            database.add(child3_1);
                database.add(child3_1_1);
                database.add(child3_1_2);
            database.add(child3_2);
                database.add(child3_2_1);
                database.add(child3_2_2);
        /* ---------- 构建源数据 ----------  End */

        /* ---------- 封装查询工具 ----------  Start */
        Function<TestJoUser, String> acquireIdentifierOfItSelfFunction = TestJoUser::getId;
        Function<String, List<TestJoUser>> acquireChildrenByParentIdentifierFunction = parentId -> database.stream()
                .filter(obj -> parentId.equals(obj.getParentId()))
                .collect(Collectors.toList());
        BiConsumer<TestJoUser, List<TestJoUser>> setChildFunction = (obj, children) -> {
            if (obj.getChildren() == null) { obj.setChildren(new ArrayList<>()); }
            obj.getChildren().addAll(children);
        };

        TreeTool<TestJoUser, String> treeTool = new TreeTool<>(
                acquireIdentifierOfItSelfFunction,
                acquireChildrenByParentIdentifierFunction,
                setChildFunction
        );
        /* ---------- 封装查询工具 ----------  End */

        /* ---------------------------
            构建根级数据
            rootList 只能有顶级，元素对象内不能挂下级元素，否则结果不正确
        --------------------- */
        List<TestJoUser> rootList = new ArrayList<>();
        rootList.add(root1);
        rootList.add(root2);
        rootList.add(root3);

        // 获取树结构
        String treeJson = JSONObject.toJSONString(treeTool.getTree(3, rootList));
        System.out.println("一棵树：");
        System.out.println(treeJson);

        // 获取某一层的数据
        String layerJson = JSONObject.toJSONString(treeTool.getObjListOfSpecificLayer(3, rootList));
        System.out.println("指定层的数据：");
        System.out.println(layerJson);

        // 获取根到某一层内的所有元素集合
        String allObjSpecificDepthJson = JSONObject.toJSONString(treeTool.getAllObjForSpecificDepth(3, rootList));
        System.out.println("根到指定层内的所有元素集合：");
        System.out.println(allObjSpecificDepthJson);
    }

}
