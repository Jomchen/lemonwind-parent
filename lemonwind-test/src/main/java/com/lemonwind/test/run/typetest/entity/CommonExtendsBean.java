package com.lemonwind.test.run.typetest.entity;

import java.util.List;

public class CommonExtendsBean<T extends JoFather> extends CommonEntity<T> {

    List<T> extendsBound;

    public CommonExtendsBean() {
    }

    public CommonExtendsBean(List<T> extendsBound) {
        this.extendsBound = extendsBound;
    }

    public List<? extends JoFather> getExtendsBound() {
        return extendsBound;
    }

    public void setExtendsBound(List<T> extendsBound) {
        this.extendsBound = extendsBound;
    }
}
