package com.jomkie.web;


import com.alibaba.fastjson.JSONObject;
import com.jomkie.annotations.ReqValidGroup;
import com.jomkie.common.ResultObj;
import com.jomkie.common.UrlContent;
import com.jomkie.dto.JoTestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jomkie
 * @since 2021-04-15 22:31:27
 * 测试接口
 */
@RestController
@Slf4j
public class JoTestController {

    /**
     * @author Jomkie
     * @since 2021-04-26 9:52:37
     * 测试 远程
     */
    @GetMapping(UrlContent.NET_TEST_REMOTE)
    public ResultObj<String> remoteTest() {
        return ResultObj.success("你请求成功了哦");
    }

    /**
     * @author Jomkie
     * @since 2021-04-26 9:52:27
     * 测试dto
     */
    @PostMapping(UrlContent.NET_TEST_CUSTOMER)
    public ResultObj<String> customerTest(@RequestBody @ReqValidGroup() JoTestDto dto) {
        log.info(JSONObject.toJSONString(dto));
        return ResultObj.success("customerTest 成功");
    }

}
