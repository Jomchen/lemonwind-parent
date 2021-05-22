package com.jomkie.web;

import com.jomkie.annotations.ReqValidGroup;
import com.jomkie.common.*;
import com.jomkie.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author Jomkie
 * @since 2021-05-22 15:30:43
 * 测试 api
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private HttpServletResponse response;

    /**
     * @author Jomkie
     * @since 2021-05-22 15:17:38
     * 测试生成二维码
     */
    @GetMapping(UrlContent.GENERATE_QRCODE_IMAGE)
    public void generateQrcodeImage(@PathVariable("data") String data) {

        data = Objects.isNull(data) ? "Linux" : data;
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            log.error("获取输出流失败", e);
        }

        try {
            // 源码中可知晓使用完输出流后会关闭流
            QrcodeImageTool.generateQRCodeImage(data, 350, 350, outputStream);
        } catch (IOException e) {
            throw new LemonException(Responsecode.GENERATED_QDIMAGE_FAIL, e);
        } catch (Exception e) {
            throw new LemonException(Responsecode.GENERATED_QDIMAGE_FAIL, e);
        }
    }

    /**
     * @author Jomkie
     * @since 2021-05-20 11:14:49
     * 测试post远程回调请求
     */
    @ReqValidGroup()
    @PostMapping(UrlContent.NET_TEST_REMOTE_POST)
    public ResultObj<String> testRemotePost(@RequestBody String body) {
        log.warn("testRemotePost 请求过来的数据为：{}", body);
        return ResultObj.success("testRemotePost successful.");
    }

    /**
     * @author Jomkie
     * @since 2021-05-21 22:11:52
     * 测试get远程回调请求
     */
    @ReqValidGroup()
    @GetMapping(UrlContent.NET_TEST_REMOTE_GET)
    public ResultObj<String> testRemoteGet(@PathVariable("data") String body) {
        log.warn("testRemoteGet 请求过来的数据为：{}", body);
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
