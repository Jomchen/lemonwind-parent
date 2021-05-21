package com.jomkie.web;

import com.jomkie.annotations.ReqValidGroup;
import com.jomkie.common.ResultObj;
import com.jomkie.common.UrlContent;
import com.jomkie.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * @author Jomkie
     * @since 2021-05-20 11:14:49
     * 测试post远程请求
     */
    @ReqValidGroup()
    @PostMapping(UrlContent.NET_TEST_REMOTE_POST)
    public ResultObj<String> testRemotePost(@RequestBody String body) {
        log.info("testRemotePost：{}", body);
        log.info("testRemotePost：{}", body);
        log.info("testRemotePost：{}", body);
        log.info("testRemotePost：{}", body);
        return ResultObj.success("testRemotePost successful.");
    }

    /**
     * @author Jomkie
     * @since 2021-05-21 22:11:52
     * 测试get远程请求
     */
    @ReqValidGroup()
    @PostMapping(UrlContent.NET_TEST_REMOTE_GET)
    public ResultObj<String> testRemoteGet(@RequestBody String body) {
        log.info("testRemoteGet：{}", body);
        log.info("testRemoteGet：{}", body);
        log.info("testRemoteGet：{}", body);
        log.info("testRemoteGet：{}", body);
        return ResultObj.success("testRemoteGet successful.");
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 15:19:59
     * 普通测试
     */
    @ReqValidGroup()
    @GetMapping(UrlContent.NET_TEST_NORMAL)
    public ResultObj<String> normal() {
        String result = testService.testNormal();
        log.info("我接收到的信息是：{}", result);
        return ResultObj.success("normal successful");
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 15:39:36
     * 测试微信支付接口
     */
    @ReqValidGroup()
    @GetMapping(UrlContent.NET_TEST_WECHAT_PAY)
    public ResultObj<String> wecahtPay() {
        String result = testService.testWechatPay();
        log.info("我接口层拿到数据是：{}", result);
        return ResultObj.success(result);
    }

}
