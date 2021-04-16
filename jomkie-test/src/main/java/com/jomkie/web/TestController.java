package com.jomkie.web;


import com.jomkie.common.ResultObj;
import com.jomkie.common.UrlContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jomkie
 * @since 2021-04-15 22:31:27
 * 测试接口
 */
@RestController
@Slf4j
public class TestController {

    @GetMapping(UrlContent.NET_REMOTE_TEST)
    public ResultObj<String> remoteTest() {
        return ResultObj.success("你请求成功了哦");
    }

}
