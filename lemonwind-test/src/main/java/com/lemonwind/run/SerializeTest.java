package com.lemonwind.run;

import com.alibaba.fastjson.JSONObject;
import com.lemonwind.run.typetest.entity.JoUser;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class SerializeTest {
    public static void main(String[] args) {

    }

    public List<JoUser> getList() { return null; }
    public JoUser getObj() {
        return null;
    }
    public JoUser[] getArray() {
        return null;
    }
    public Map<String, JoUser> getMap() { return null; }

    /**
     * 测试序列化 和 反序列化功能
     */
    public static void test00() throws NoSuchMethodException {
        // TODO 这里测试用 jackson 的方式序列化为字符串，并将字符串反序列化为对象
        List<JoUser> userList = LongStream.range(0, 5).mapToObj(id ->
                new JoUser().setId(id).setName("李寻欢" + id).setAge(20 + (int)id)
        ).collect(Collectors.toList());
        JoUser userObj = userList.get(0);
        JoUser[] userArray = userList.toArray(new JoUser[0]);
        Map<String, JoUser> userMap = new HashMap<>(); userMap.put(userObj.getName(), userObj);

        Class<?> clazz = SerializeTest.class;
        Method listMethod = clazz.getMethod("getList");
        Type[] listType = ((ParameterizedType)listMethod.getGenericReturnType()).getActualTypeArguments();
        Method objMethod = clazz.getMethod("getObj");
        Type objType = objMethod.getGenericReturnType();
        Method arrayMethod = clazz.getMethod("getArray");
        Type arrayType = arrayMethod.getGenericReturnType();
        Method mapMethod = clazz.getMethod("getMap");
        Type mapType = mapMethod.getGenericReturnType();

        String listStr = JSONObject.toJSONString(userList);
        String objStr = JSONObject.toJSONString(userObj);
        String arrayStr = JSONObject.toJSONString(userArray);
        String mapStr = JSONObject.toJSONString(userMap);
    }

}
