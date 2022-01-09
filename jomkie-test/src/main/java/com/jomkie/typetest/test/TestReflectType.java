package com.jomkie.typetest.test;


import com.jomkie.typetest.entity.*;
import com.jomkie.typetest.jicheng.JoExtends;
import com.jomkie.typetest.jicheng.JoMulExtends;
import com.jomkie.typetest.jicheng.impl.JoUserExtendsImpl;
import com.jomkie.typetest.jicheng.impl.JoUserMulExtendsImpl;
import com.jomkie.typetest.jiekou.JoInterface;
import com.jomkie.typetest.jiekou.JoMulInterface;
import com.jomkie.typetest.jiekou.impl.JoUserInterfaceImpl;
import com.jomkie.typetest.jiekou.impl.JoUserMulInterfaceImpl;

import java.lang.reflect.*;
import java.util.LinkedList;
import java.util.List;

public class TestReflectType {

  public static void main(String[] args) throws NoSuchFieldException {
    Class<?> joUser = JoUser.class;
    Class<?> joKongfu = JoKongfu.class;

    Class<?> joExtends = JoExtends.class;
    Class<?> joMulExtends = JoMulExtends.class;
    Class<?> joUserExtendsImpl = JoUserExtendsImpl.class;
    Class<?> joUsermulExtendsImpl = JoUserMulExtendsImpl.class;

    Class<?> joInterface = JoInterface.class;
    Class<?> joMulInterface = JoMulInterface.class;
    Class<?> joUserInterfaceImpl = JoUserInterfaceImpl.class;
    Class<?> joUserMulInterfaceImpl = JoUserMulInterfaceImpl.class;

//    ParameterizedType
//    GenericArrayType
//    TypeVariable
//    Class
//    WildcardType

    /* ---------------- ParameterizedType 是带有参数化的类型对象，例如 List<T> 或 Map<K, V> ---------------- */
//    Type type = joUserExtendsImpl.getGenericSuperclass();
//    ParameterizedType parameterizedType = (ParameterizedType) type;
//    Class cla = (Class) parameterizedType.getActualTypeArguments()[0];

    /* ----------------------- 测试00 ----------------------- */
    Class<?> commonBeanClass = CommonBean.class;
    Field listField = commonBeanClass.getDeclaredField("list");
    Field mapField = commonBeanClass.getDeclaredField("map");
    Field arrayField = commonBeanClass.getDeclaredField("array");
    Field listArrayField = commonBeanClass.getDeclaredField("listArray");
    Field mapEntryField = commonBeanClass.getDeclaredField("mapEntry");
    Field genericListField = commonBeanClass.getDeclaredField("genericList");
    Field genericArrayField = commonBeanClass.getDeclaredField("genericArray");


    Type listType = listField.getGenericType();
    Type mapType = mapField.getGenericType();
    Type arrayType = arrayField.getGenericType();
    Type listArrayType = listArrayField.getGenericType();
    Type mapEntryType = mapEntryField.getGenericType();
    Type genericListType = genericListField.getGenericType();
    Type genericArrayType = genericArrayField.getGenericType();

    ParameterizedType listParameterizedType = (ParameterizedType) listType;
    ParameterizedType mapParameterizedType = (ParameterizedType) mapType;
    // arrayType instanceof ParameterizedType 为 false
    GenericArrayType listArrayGenericArrayType = (GenericArrayType) listArrayType;
    ParameterizedType mapEntryParameterizedType = (ParameterizedType) mapEntryType;
    ParameterizedType genericListParameterizedType = (ParameterizedType) genericListType;
    GenericArrayType genericArrayGenericArrayType = (GenericArrayType) genericArrayType;

    TypeVariable genericListTypeVariable = (TypeVariable) genericListParameterizedType.getActualTypeArguments()[0];
    TypeVariable genericArrayTypeVariable = (TypeVariable) genericArrayGenericArrayType.getGenericComponentType();

    /* ----------------------- ParameterizedType ----------------------- */
    // class org.apache.ibatis.typetest.entity.JoUser
//    listParameterizedType.getActualTypeArguments()[0];
    // interface java.util.List
//    listParameterizedType.getRawType();
    // interface java.util.Map
//    mapEntryParameterizedType.getOwnerType();

    /* ----------------------- GenericArrayType ----------------------- */
    // java.util.List<java.lang.String>
//    listArrayGenericArrayType.getGenericComponentType();

    /* ----------------------- TypeVariable ----------------------- */

    
    
    
    
    
    
    
    
    
    
    
    
    
//    List<? super JoFather> list = new LinkedList<>();
//    JoFather joFather = new JoFather();
//    JoSon joSon = new JoSon();
//    list.add(joFather);
//    list.add(joSon);
	/*
	 * JoFather f1 = list.get(0); JoSon s1 = list.get(0);
	 */

//    List<? extends JoFather> list2 = new LinkedList<>();
	/*
	 * list2.add(joFather); list2.add(joSon);
	 */
//    JoFather joFather2 = list2.get(0);

  }

}
