package com.jomkie.typetest.entity;

import java.util.List;

public class CommonExtendsBean<JoFather> extends CommonBean<JoFather> {

    List<? extends JoFather> extendsBound;

}
