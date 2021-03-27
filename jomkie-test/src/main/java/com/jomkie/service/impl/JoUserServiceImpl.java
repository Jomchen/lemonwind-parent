package com.jomkie.service.impl;

import com.jomkie.common.BaseServiceImpl;
import com.jomkie.dao.JoUserMapper;
import com.jomkie.dto.JoUserDto;
import com.jomkie.model.JoUser;
import com.jomkie.service.JoUserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JoUserServiceImpl extends BaseServiceImpl<JoUserMapper, JoUser> implements JoUserService {

    @Autowired
    private JoUserMapper joUserMapper;

    @Override
    public List<JoUserDto> getAll() {
        List<JoUser> list = joUserMapper.getAll();
        return list.stream().map(bean -> {
            JoUserDto dto = new JoUserDto();
            BeanUtils.copyProperties(bean, dto);
            return dto;
        }).collect(Collectors.toList());
    }

}
