package com.lemonwind.test.common;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Jomkie
 * @since 2021/3/28 21:06:10
 * 基础服务实现
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    @Autowired
    private M mapper;

    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        return false;
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        List<T> list = mapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        T entity = list.get(0);

        if (list.size() == 1) {
            return entity;
        }

        if (throwEx) {
            throw new RuntimeException("found amount of the entity over two.");
        } else {
            return entity;
        }
    }

    @Override
    public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<T> getBaseMapper() {
        return this.mapper;
    }

    @Override
    public Class<T> getEntityClass() {
        return null;
    }

}
