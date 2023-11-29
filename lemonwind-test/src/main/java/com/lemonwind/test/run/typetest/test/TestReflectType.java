package com.lemonwind.test.run.typetest.test;


import com.lemonwind.test.run.typetest.entity.*;
import com.lemonwind.test.run.typetest.jicheng.JoExtends;
import com.lemonwind.test.run.typetest.jicheng.JoMulExtends;
import com.lemonwind.test.run.typetest.jicheng.impl.JoUserExtendsImpl;
import com.lemonwind.test.run.typetest.jicheng.impl.JoUserMulExtendsImpl;
import com.lemonwind.test.run.typetest.jiekou.JoInterface;
import com.lemonwind.test.run.typetest.jiekou.JoMulInterface;
import com.lemonwind.test.run.typetest.jiekou.impl.JoUserInterfaceImpl;
import com.lemonwind.test.run.typetest.jiekou.impl.JoUserMulInterfaceImpl;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TestReflectType {

  static Object lockOne = new Object();
  static Map<String, String> MY_MAP = new HashMap<>();


  public static void main(String[] args) throws NoSuchFieldException, InterruptedException {
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
    Class<?> commonExtendsBeanClass = CommonExtendsBean.class;
    Class<?> commonSuperBeanClass = CommonSuperBean.class;
    
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
    
    /* ----------------------- WildcardType ----------------------- */
    
    Field extendsBoundOfExtendsBean = commonExtendsBeanClass.getDeclaredField("extendsBound");
    Type extendsBoundOfExtendsBeanType = extendsBoundOfExtendsBean.getGenericType();
    ParameterizedType extendsBoundOfExtendsBeanParameterizedType = (ParameterizedType) extendsBoundOfExtendsBeanType;
    TypeVariable extendsBoundOfExtendsBeanTypeVariable = (TypeVariable) extendsBoundOfExtendsBeanParameterizedType.getActualTypeArguments()[0];
    System.out.println(extendsBoundOfExtendsBeanTypeVariable.getBounds()[0]);;
    
    System.out.println("------------------------------------------------------");
    
    Field superBound = commonSuperBeanClass.getDeclaredField("superBound");
    Type superBoundType = superBound.getGenericType();
    ParameterizedType superBoundParameterizedType = (ParameterizedType) superBoundType;
    WildcardType superBoundWildcardType = (WildcardType) superBoundParameterizedType.getActualTypeArguments()[0];
    Type xxType = superBoundWildcardType.getLowerBounds()[0];
    TypeVariable xxTypeVariable = (TypeVariable) xxType;
    // 以下两个输出需要严重注意一下
    System.out.println(xxType);
    System.out.println((Class<JoSon>)xxTypeVariable.getBounds()[0]);
    
    System.out.println("------------------------------------------------------");
    
    Field extendsBound = commonSuperBeanClass.getDeclaredField("extendsBound");
    Type extendsBoundType = extendsBound.getGenericType();
    ParameterizedType extendsBoundParameterized = (ParameterizedType) extendsBoundType;
    WildcardType extendsBoundWildcardType = (WildcardType) extendsBoundParameterized.getActualTypeArguments()[0];
    System.out.println(extendsBoundWildcardType.getUpperBounds()[0]);
    
    
    
    
    
    
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


    MY_MAP.put("1", "111");
    MY_MAP.put("2", "222");
    MY_MAP.put("3", "333");
    AtomicInteger atomicInteger = new AtomicInteger(0);
    for (Map.Entry<String, String> entry : MY_MAP.entrySet()) {
      String key = entry.getKey();
      new Thread(() -> {
        while (true) {
          System.out.println(System.currentTimeMillis());
          synchronized (lockOne) {
            System.out.println(Thread.currentThread().getName() + "拿到了第 <1> 个锁");
          }
          System.out.println(Thread.currentThread().getName() + "==>" + atomicInteger.getAndAdd(1));
          synchronizedMethod();
        }
      }, "my-thread-" + key).start();
    }
  }

  private static void synchronizedMethod() {
    synchronized (MY_MAP) {
      System.out.println(Thread.currentThread().getName() + "拿到了第 <2> 个锁");
    }
  }


}
