package com.lemonwind.test.typetest.jiekou.impl;

import com.lemonwind.test.typetest.jiekou.JoMulInterface;
import com.lemonwind.test.typetest.entity.JoKongfu;
import com.lemonwind.test.typetest.entity.JoUser;

public class JoUserMulInterfaceImpl implements JoMulInterface<JoKongfu, JoUser> {

  @Override
  public JoKongfu getData(JoUser joUser) {
    return null;
  }

}
