package com.jomkie.typetest.entity;

import java.util.List;

public class CommonExtendsBean<JoFather> extends CommonEntity<JoFather> {

    List<? extends JoFather> extendsBound;

    public CommonExtendsBean() {
    }

    public CommonExtendsBean(List<? extends JoFather> extendsBound) {
        this.extendsBound = extendsBound;
    }

}
