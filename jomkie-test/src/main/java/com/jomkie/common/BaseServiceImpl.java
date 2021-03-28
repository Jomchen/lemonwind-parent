package com.jomkie.common;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl<Dao, T> implements BaseService<T> {

    @Autowired
    private Dao dao;

}
