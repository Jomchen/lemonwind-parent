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

@RestController
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * @author Jomkie
     * @since 2021-05-20 11:14:49
     * 测试远程通信
     */
    @PostMapping(UrlContent.NET_TEST_REMOTE)
    @ReqValidGroup()
    public ResultObj<String> testRemote(@RequestBody String notifyData) {
        log.info("微信的回调信息为：{}", notifyData);
        log.info("微信的回调信息为：{}", notifyData);
        log.info("微信的回调信息为：{}", notifyData);
        log.info("微信的回调信息为：{}", notifyData);
        log.info("微信的回调信息为：{}", notifyData);
        return ResultObj.success("remote successful.");
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 15:19:59
     * 普通测试
     */
    @GetMapping(UrlContent.NET_TEST_NORMAL)
    @ReqValidGroup()
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
    @GetMapping(UrlContent.NET_TEST_WECHAT_PAY)
    @ReqValidGroup()
    public ResultObj<String> wecahtPay() {
        String result = testService.testWechatPay();
        log.info("我接口层拿到数据是：{}", result);
        return ResultObj.success(result);
    }

}
