package com.jomkie.web;

import com.jomkie.common.ResultObj;
import com.jomkie.common.UrlContent;
import com.jomkie.service.AopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jomkie
 * @since 2021-05-08 11:18:33
 * 测试 aop 拦截
 */
@RestController
public class AopController {

    @Autowired
    @Qualifier("aopServiceImpl")
    AopService aopService;

    /**
     * @author Jomkie
     * @since 2021-05-08 11:19:55
     * 测试一个接口多个实现的 aop 拦截
     */
    @GetMapping(UrlContent.NET_AOP_TEST_AOP_SERVER_AND_LOG)
    public ResultObj<String> testAopServerAndLog() {
        return aopService.testAopServerAndLog();
    }

}
