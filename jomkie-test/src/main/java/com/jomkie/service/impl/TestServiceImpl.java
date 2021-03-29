package com.jomkie.service.impl;

import com.jomkie.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl  implements TestService {

    @Override
    public void test() {
        System.out.println("执行了测试方法");
    }

}
