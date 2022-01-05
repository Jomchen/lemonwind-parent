package com.jomkie.typetest.entity;

import java.util.List;

public class CommonSuperBean<JoSon> extends CommonEntity<JoSon> {

    List<? super JoSon> superBound;

    public CommonSuperBean() {
    }

    public CommonSuperBean(List<? super JoSon> superBound) {
        this.superBound = superBound;
    }

    public List<? super JoSon> getSuperBound() {
        return superBound;
    }

    public void setSuperBound(List<? super JoSon> superBound) {
        this.superBound = superBound;
    }
}
