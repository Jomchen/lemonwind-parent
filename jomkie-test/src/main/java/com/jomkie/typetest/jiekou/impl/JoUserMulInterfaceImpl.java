package com.jomkie.typetest.jiekou.impl;

import org.apache.ibatis.typetest.entity.JoKongfu;
import org.apache.ibatis.typetest.entity.JoUser;
import org.apache.ibatis.typetest.jiekou.JoMulInterface;

public class JoUserMulInterfaceImpl implements JoMulInterface<JoKongfu, JoUser> {

  @Override
  public JoKongfu getData(JoUser joUser) {
    return null;
  }

}
