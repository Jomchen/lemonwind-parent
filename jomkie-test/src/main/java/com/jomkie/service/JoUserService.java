package com.jomkie.service;

import com.jomkie.dto.JoUserDto;

import java.util.List;

/**
 * @author Jomkie
 * @date 2021/3/28 21:08:16
 * 用户服务接口
 */
public interface JoUserService {

    List<JoUserDto> getAll();

}
