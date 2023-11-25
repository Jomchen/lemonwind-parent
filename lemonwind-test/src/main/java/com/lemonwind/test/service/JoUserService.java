package com.lemonwind.test.service;

import com.lemonwind.test.dto.JoUserDto;
import com.lemonwind.test.model.JoUser;

import java.util.List;

/**
 * @author Jomkie
 * @since 2021/3/28 21:08:16
 * 用户服务接口
 */
public interface JoUserService {

    List<JoUserDto> getAll();

    JoUserDto getById(Long id);

    boolean saveEntity(JoUser joUser);
    
    void updateBatchEnity(List<JoUser> list);

    boolean saveBatchEntity(List<JoUser> list, int batch);

    List<JoUser> findByConditions();

}
