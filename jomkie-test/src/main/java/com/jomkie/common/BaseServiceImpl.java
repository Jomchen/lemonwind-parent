package com.jomkie.common;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jomkie
 * @date 2021/3/28 21:06:10
 * 基础服务实现
 */
public class BaseServiceImpl<Dao, T> implements BaseService<T> {

    @Autowired
    private Dao dao;

}
