package com.jomkie.web;


import com.alibaba.fastjson.JSONObject;
import com.jomkie.annotations.ReqValidGroup;
import com.jomkie.common.ResultObj;
import com.jomkie.common.UrlContent;
import com.jomkie.dto.JoTestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(UrlContent.NET_TEST_CUSTOMER_VALID)
    @ReqValidGroup()
    public ResultObj<String> customerValid(@RequestBody JoTestDto dto) {
        log.info(JSONObject.toJSONString(dto));
        return ResultObj.success("customerTest 成功");
    }

    @GetMapping(UrlContent.NET_TEST_PATH_ID)
    @ReqValidGroup
    public ResultObj<String> pathId(
            @PathVariable("id")
            String id) {

        log.info("pathId 的 传入id 为 {}", id);
        return ResultObj.success("pathId 成功");
    }

}
