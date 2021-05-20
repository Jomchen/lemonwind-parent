package com.jomkie.web;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.common.ResultObj;
import com.jomkie.common.UrlContent;
import com.jomkie.dto.JoUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    /**
     * @author Jomkie
     * @since 2021-05-20 11:14:49
     * 测试远程通信
     */
    @PostMapping(UrlContent.NET_TEST_REMOTE)
    public ResultObj<String> testRemote(@RequestBody JoUserDto joUserDto) {
        log.info(JSONObject.toJSONString(joUserDto));
        return ResultObj.success("remote successful.");
    }

}
