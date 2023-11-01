package com.jomkie.test.typetest.jiekou.impl;

import com.jomkie.test.typetest.jiekou.JoMulInterface;
import com.jomkie.test.typetest.entity.JoKongfu;
import com.jomkie.test.typetest.entity.JoUser;

public class JoUserMulInterfaceImpl implements JoMulInterface<JoKongfu, JoUser> {

  @Override
  public JoKongfu getData(JoUser joUser) {
    return null;
  }

}
