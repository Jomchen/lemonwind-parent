package com.jomkie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jomkie.common.BaseServiceImpl;
import com.jomkie.dao.JoUserMapper;
import com.jomkie.dto.JoUserDto;
import com.jomkie.model.JoUser;
import com.jomkie.service.JoUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jomkie
 * @date 2021/3/28 21:08:03
 * 用户服务实现
 */
@Service
public class JoUserServiceImpl extends BaseServiceImpl<JoUserMapper, JoUser> implements JoUserService {

    @Autowired
    private JoUserMapper joUserMapper;

    @Override
    public List<JoUserDto> getAll() {
        QueryWrapper<JoUser> queryWrapper = new QueryWrapper<>();

        List<JoUser> list = joUserMapper.selectList(queryWrapper);
        return list.stream().map(bean -> {
            JoUserDto dto = new JoUserDto();
            BeanUtils.copyProperties(bean, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public JoUserDto getById(Long id) {
        JoUser joUser = joUserMapper.selectById(id);
        return Optional.ofNullable(joUser).map(bean -> {
            JoUserDto dto = new JoUserDto();
            BeanUtils.copyProperties(bean, dto);
            return dto;
        }).orElse(null);
    }

}
