package org.apache.ibatis.typetest.test;

import org.apache.ibatis.typetest.entity.JoKongfu;
import org.apache.ibatis.typetest.entity.JoUser;
import org.apache.ibatis.typetest.jicheng.JoExtends;
import org.apache.ibatis.typetest.jicheng.JoMulExtends;
import org.apache.ibatis.typetest.jicheng.impl.JoUserExtendsImpl;
import org.apache.ibatis.typetest.jicheng.impl.JoUserMulExtendsImpl;
import org.apache.ibatis.typetest.jiekou.JoInterface;
import org.apache.ibatis.typetest.jiekou.JoMulInterface;
import org.apache.ibatis.typetest.jiekou.impl.JoUserInterfaceImpl;
import org.apache.ibatis.typetest.jiekou.impl.JoUserMulInterfaceImpl;

import java.lang.reflect.*;

public class TestReflectType {

  public static void main(String[] args) {
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
    Type type = joUserExtendsImpl.getGenericSuperclass();
    ParameterizedType parameterizedType = (ParameterizedType) type;
    Class cla = (Class) parameterizedType.getActualTypeArguments()[0];

    /* ---------------- TypeVariable 是具体泛型中的对象，例如 List<T> 中的 T ---------------- */
    Type type2 = joUserExtendsImpl.getGenericSuperclass();
  }

}
