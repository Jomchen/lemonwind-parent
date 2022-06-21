package com.jomkie.common.util.tree.test;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.common.util.tree.TreeTool;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;

/**
 * @author jomkie
 */
public class TreeToolTest {

    public static void main(String[] args) {
        TreeJoUser node1 = new TreeJoUser().setId("1").setName("name_1").setAge(1).setParentId("-1");
            TreeJoUser node1_1 = new TreeJoUser().setId("1_1").setName("name_1_1").setAge(11).setParentId("1");
                TreeJoUser node1_1_1 = new TreeJoUser().setId("1_1_1").setName("name_1_1_1").setAge(111).setParentId("1_1");
                TreeJoUser node1_1_2 = new TreeJoUser().setId("1_1_2").setName("name_1_1_2").setAge(112).setParentId("1_1");
                TreeJoUser node1_1_3 = new TreeJoUser().setId("1_1_3").setName("name_1_1_3").setAge(113).setParentId("1_1");
            TreeJoUser node1_2 = new TreeJoUser().setId("1_2").setName("name_1_2").setAge(12).setParentId("1");
                TreeJoUser node1_2_1 = new TreeJoUser().setId("1_2_1").setName("name_1_2_1").setAge(121).setParentId("1_2");
                TreeJoUser node1_2_2 = new TreeJoUser().setId("1_2_2").setName("name_1_2_2").setAge(122).setParentId("1_2");
                TreeJoUser node1_2_3 = new TreeJoUser().setId("1_2_3").setName("name_1_2_3").setAge(123).setParentId("1_2");
            TreeJoUser node1_3 = new TreeJoUser().setId("1_3").setName("name_1_3").setAge(13).setParentId("1");
                TreeJoUser node1_3_1 = new TreeJoUser().setId("1_3_1").setName("name_1_3_1").setAge(131).setParentId("1_3");
                TreeJoUser node1_3_2 = new TreeJoUser().setId("1_3_2").setName("name_1_3_2").setAge(132).setParentId("1_3");
                TreeJoUser node1_3_3 = new TreeJoUser().setId("1_3_3").setName("name_1_3_3").setAge(133).setParentId("1_3");
        
        TreeJoUser node2 = new TreeJoUser().setId("2").setName("name_2").setAge(2).setParentId("-1");
            TreeJoUser node2_1 = new TreeJoUser().setId("2_1").setName("name_2_1").setAge(21).setParentId("2");
                TreeJoUser node2_1_1 = new TreeJoUser().setId("2_1_1").setName("name_2_1_1").setAge(211).setParentId("2_1");
                TreeJoUser node2_1_2 = new TreeJoUser().setId("2_1_2").setName("name_2_1_2").setAge(212).setParentId("2_1");
                TreeJoUser node2_1_3 = new TreeJoUser().setId("2_1_3").setName("name_2_1_3").setAge(213).setParentId("2_1");
            TreeJoUser node2_2 = new TreeJoUser().setId("2_2").setName("name_2_2").setAge(22).setParentId("2");
                TreeJoUser node2_2_1 = new TreeJoUser().setId("2_2_1").setName("name_2_2_1").setAge(221).setParentId("2_2");
                TreeJoUser node2_2_2 = new TreeJoUser().setId("2_2_2").setName("name_2_2_2").setAge(222).setParentId("2_2");
                TreeJoUser node2_2_3 = new TreeJoUser().setId("2_2_3").setName("name_2_2_3").setAge(223).setParentId("2_2");
            TreeJoUser node2_3 = new TreeJoUser().setId("2_3").setName("name_2_3").setAge(23).setParentId("2");
                TreeJoUser node2_3_1 = new TreeJoUser().setId("2_3_1").setName("name_2_3_1").setAge(231).setParentId("2_3");
                TreeJoUser node2_3_2 = new TreeJoUser().setId("2_3_2").setName("name_2_3_2").setAge(232).setParentId("2_3");
                TreeJoUser node2_3_3 = new TreeJoUser().setId("2_3_3").setName("name_2_3_3").setAge(233).setParentId("2_3");
                
        TreeJoUser node3 = new TreeJoUser().setId("3").setName("name_3").setAge(3).setParentId("-1");
            TreeJoUser node3_1 = new TreeJoUser().setId("3_1").setName("name_3_1").setAge(31).setParentId("3");
                TreeJoUser node3_1_1 = new TreeJoUser().setId("3_1_1").setName("name_3_1_1").setAge(311).setParentId("3_1");
                TreeJoUser node3_1_2 = new TreeJoUser().setId("3_1_2").setName("name_3_1_2").setAge(312).setParentId("3_1");
                TreeJoUser node3_1_3 = new TreeJoUser().setId("3_1_3").setName("name_3_1_3").setAge(313).setParentId("3_1");
            TreeJoUser node3_2 = new TreeJoUser().setId("3_2").setName("name_3_2").setAge(32).setParentId("3");
                TreeJoUser node3_2_1 = new TreeJoUser().setId("3_2_1").setName("name_3_2_1").setAge(321).setParentId("3_2");
                TreeJoUser node3_2_2 = new TreeJoUser().setId("3_2_2").setName("name_3_2_2").setAge(322).setParentId("3_2");
                TreeJoUser node3_2_3 = new TreeJoUser().setId("3_2_3").setName("name_3_2_3").setAge(323).setParentId("3_2");
            TreeJoUser node3_3 = new TreeJoUser().setId("3_3").setName("name_3_3").setAge(33).setParentId("3");
                TreeJoUser node3_3_1 = new TreeJoUser().setId("3_3_1").setName("name_3_3_1").setAge(331).setParentId("3_3");
                TreeJoUser node3_3_2 = new TreeJoUser().setId("3_3_2").setName("name_3_3_2").setAge(332).setParentId("3_3");
                TreeJoUser node3_3_3 = new TreeJoUser().setId("3_3_3").setName("name_3_3_3").setAge(333).setParentId("3_3");
        
        List<TreeJoUser> dataList = Stream.of(
            node1,
                node1_1, 
                    node1_1_1, node1_1_2, node1_1_3,
                node1_2, 
                    node1_2_1, node1_2_2, node1_2_3,
                node1_3, 
                    node1_3_1, node1_3_2, node1_3_3,
            node2,
                node2_1, 
                    node2_1_1, node2_1_2, node2_1_3,
                node2_2, 
                    node2_2_1, node2_2_2, node2_2_3,
                node2_3, 
                    node2_3_1, node2_3_2, node2_3_3,
            node3,
                node3_1, 
                    node3_1_1, node3_1_2, node3_1_3,
                node3_2, 
                    node3_2_1, node3_2_2, node3_2_3,
                node3_3, 
                    node3_3_1, node3_3_2, node3_3_3
        ).collect(toList());
        List<TreeJoUser> rootList = dataList.stream().filter(e -> Objects.equals(e.getParentId(), "-1")).collect(toList());
        
        // 组装树工具
        Map<String, List<TreeJoUser>> parentIdMap = dataList.stream().collect(groupingBy(TreeJoUser::getParentId, toList()));
        Map<String, TreeJoUser> idMap = dataList.stream().collect(toMap(TreeJoUser::getId, x -> x, (x, y) -> y));
        Function<TreeJoUser, String> idFun = TreeJoUser::getId;
        Function<String, List<TreeJoUser>> childrenByParentIdFun = parentIdMap::get;
        BiConsumer<TreeJoUser, List<TreeJoUser>> injectChildrenFun = TreeJoUser::injectChildren;
        Function<TreeJoUser, TreeJoUser> parentObjFun = bean -> idMap.get(bean.getParentId());
        TreeTool<TreeJoUser, String> treeTool = new TreeTool<TreeJoUser, String>()
            .setIdFun(idFun)
            .setChildrenByParentIdFun(childrenByParentIdFun)
            .setInjectChildrenFun(injectChildrenFun)
            .setParentObjFun(parentObjFun);
        
        // 获取一棵树
//        System.out.println(JSONObject.toJSONString(treeTool.getTree(Integer.MAX_VALUE, rootList)));
        
        // 获取两层的树
//        System.out.println(JSONObject.toJSONString(treeTool.getTree(2, rootList)));

        // 获取所有树节点
//        System.out.println(JSONObject.toJSONString(treeTool.getAllObjForSpecificDepth(Integer.MAX_VALUE, rootList)));

        // 获取前两层的所有树节点
//        System.out.println(JSONObject.toJSONString(treeTool.getAllObjForSpecificDepth(2, rootList)));

        // 获取第三层的节点集合
//        System.out.println(JSONObject.toJSONString(treeTool.getObjListOfSpecificLayer(3, rootList)));;

        // 获取当前节点到根的所有节点集合
        System.out.println(JSONObject.toJSONString(treeTool.getForefatherChain2(node3_3_3)));
    }
    
}
