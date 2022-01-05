package com.jomkie.typetest.entity;

import java.util.List;

public class CommonSuperBean<JoSon> extends CommonBean<JoSon> {

    List<? super JoSon> superBound;

}
