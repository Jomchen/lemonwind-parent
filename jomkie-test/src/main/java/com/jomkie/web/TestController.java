package com.jomkie.web;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.annotations.ReqValidGroup;
import com.jomkie.common.*;
import com.jomkie.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;

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

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;

    /**
     * @author Jomkie
     * @since 2021-05-22 15:17:38
     * 测试生成二维码
     */
    @GetMapping(UrlContent.NET_GENERATE_QRCODE_IMAGE)
    public void generateQrcodeImage(@PathVariable("redisKey") String redisKey) {

        String value = strRedisTemplate.opsForValue().get(redisKey);
        Optional.ofNullable(value).orElseThrow(() -> new LemonException(Responsecode.ACQUIRE_TARGET_FAIL));
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            log.error("获取输出流失败", e);
        }

        try {
            // 源码中可知晓使用完输出流后会关闭流
            QrcodeImageTool.generateQRCodeImage(value, 350, 350, outputStream);
        } catch (IOException e) {
            throw new LemonException(Responsecode.GENERATED_QDIMAGE_FAIL, e);
        } catch (Exception e) {
            throw new LemonException(Responsecode.GENERATED_QDIMAGE_FAIL, e);
        }
    }

    /**
     * @author Jomkie
     * @since 2021-05-23 10:9:6
     * @param body 包含要存储的key和value
     * 存储 redis 数据
     */
    @PostMapping(UrlContent.NET_TEST_REDIS_SAVE)
    public ResultObj<String> testRedisSave(@RequestBody String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        String redisKey = Optional.ofNullable(jsonObject.get("redisKey")).filter(Objects::nonNull)
                .map(Object::toString)
                .orElseThrow(() -> new LemonException(Responsecode.ACQUIRE_TARGET_FAIL));

        String redisValue = Optional.ofNullable(jsonObject.get("redisValue")).filter(Objects::nonNull)
                .map(Object::toString)
                .orElseThrow(() -> new LemonException(Responsecode.ACQUIRE_TARGET_FAIL));

        // 保存数据逻辑未实现
        strRedisTemplate.opsForValue().set(redisKey, redisValue);
        return ResultObj.success();
    }

    /**
     * @author Jomkie
     * @since 2021-05-23 10:8:52
     * @param redisKey 主键
     * 查询 redis 数据
     */
    @GetMapping(UrlContent.NET_TEST_REDIS_GET)
    public ResultObj<String> testRedisGet(@PathVariable("redisKey") String redisKey) {
        String value = strRedisTemplate.opsForValue().get(redisKey);
        return Optional.ofNullable(value).map(v -> ResultObj.success(v)).orElse(ResultObj.fail(Responsecode.FAILE, "未找到相应的值"));
    }

    /**
     * @author Jomkie
     * @since 2021-05-23 21:30:37
     * @param redisKey 主键
     * 删除 redis 数据
     */
    @GetMapping(UrlContent.NET_TEST_REDIS_DELETE)
    public ResultObj<Void> testRedisDel(@PathVariable("redisKey") String redisKey) {
        String value = strRedisTemplate.opsForValue().get(redisKey);
        if (Objects.isNull(value)) {
            return ResultObj.fail(Responsecode.FAILE, "未找到相应的值");
        } else {
            strRedisTemplate.delete(redisKey);
            return ResultObj.success();
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

        // 收集请求头部信息
        StringBuilder headersBuilder = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headersBuilder.append(headerName).append(": ").append(headerValue).append("\n");
        }

        log.warn("testRemotePost 请求头数据为：\n{} \ntestRemotePost 请求体为：{}", headersBuilder, body);
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

    /**
     * @author Jomkie
     * @since 2021-05-23 22:22:55
     * 测试获取微信支付平台证书
     */
    @GetMapping(UrlContent.NET_TEST_GET_WECHAT_PLATFORM)
    public ResultObj<String> getWechatPlatform() {
        String result = testService.getWechatPlatform();
        return ResultObj.success(result);
    }

}
