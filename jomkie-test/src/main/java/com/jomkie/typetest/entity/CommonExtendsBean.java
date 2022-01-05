package com.jomkie.typetest.entity;

import java.util.List;

public class CommonExtendsBean<JoFather> extends CommonEntity<JoFather> {

    List<? extends JoFather> extendsBound;

    public CommonExtendsBean() {
    }

    public CommonExtendsBean(List<? extends JoFather> extendsBound) {
        this.extendsBound = extendsBound;
    }

    public List<? extends JoFather> getExtendsBound() {
        return extendsBound;
    }

    public void setExtendsBound(List<? extends JoFather> extendsBound) {
        this.extendsBound = extendsBound;
    }
}
