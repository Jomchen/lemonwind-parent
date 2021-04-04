package com.jomkie.service.impl;

import com.jomkie.common.BaseServiceImpl;
import com.jomkie.dao.JoUserMapper;
import com.jomkie.dto.JoUserDto;
import com.jomkie.model.JoUser;
import com.jomkie.service.TestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestServiceImpl extends BaseServiceImpl<JoUserMapper, JoUser> implements TestService {


    @Override
    public void test() {
        System.out.println("执行了测试方法");
    }

    @Override
    public void add(JoUserDto joUserDto) {
        JoUser user = new JoUser();
        BeanUtils.copyProperties(joUserDto, user);
        save(user);
    }

}
