package com.lemonwind.web;

import com.alibaba.fastjson.JSONObject;
import com.lemonwind.annotations.ReqValidGroup;
import com.lemonwind.common.*;
import com.lemonwind.common.redis.RedisTool;
import com.lemonwind.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

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
    private RedisTool redisTool;

    /**
     * 测试热交换
     * @author Jomkie
     * @since 2021-06-25 23:51:24
     * @param
     * @return com.lemonwind.common.ResultObj<java.lang.String>
     */
    @GetMapping(UrlContent.NET_TEST_HOT_SWAP)
    public ResultObj<String> testHotSwap() {
        //String theData = String.valueOf(System.currentTimeMillis());
        String theData = "Linux";
        /*StringBuilder stringBuilder = new StringBuilder();
        IntStream.range(0, 10).boxed().forEach(stringBuilder::append);
        theData = stringBuilder.toString();*/
        return ResultObj.success(theData);
    }

    /**
     * 测试生成二维码
     * @author Jomkie
     * @since 2021-05-22 15:17:38
     */
    @GetMapping(UrlContent.NET_GENERATE_QRCODE_IMAGE)
    public void generateQrcodeImage(@PathVariable("redisKey") String redisKey) {

        String value = redisTool.get(redisKey);
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
     * 存储 redis 数据
     * @author Jomkie
     * @since 2021-05-23 10:9:6
     * @param body 包含要存储的key和value
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
        redisTool.set(redisKey, redisValue);
        return ResultObj.success();
    }

    /**
     * 查询 redis 数据
     * @author Jomkie
     * @since 2021-05-23 10:8:52
     * @param redisKey 主键
     */
    @GetMapping(UrlContent.NET_TEST_REDIS_GET)
    public ResultObj<String> testRedisGet(@PathVariable("redisKey") String redisKey) {
        String value = redisTool.get(redisKey);
        return Optional.ofNullable(value).map(v -> ResultObj.success(v)).orElse(ResultObj.fail(Responsecode.FAILE, "未找到相应的值"));
    }

    /**
     * 删除 redis 数据
     * @author Jomkie
     * @since 2021-05-23 21:30:37
     * @param redisKey 主键
     */
    @GetMapping(UrlContent.NET_TEST_REDIS_DELETE)
    public ResultObj<Void> testRedisDel(@PathVariable("redisKey") String redisKey) {
        String value = redisTool.get(redisKey);
        if (Objects.isNull(value)) {
            return ResultObj.fail(Responsecode.FAILE, "未找到相应的值");
        } else {
            redisTool.delete(redisKey);
            return ResultObj.success();
        }
    }

    /**
     * 测试post远程回调请求
     * @author Jomkie
     * @since 2021-05-20 11:14:49
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
     * 测试get远程回调请求
     * @author Jomkie
     * @since 2021-05-21 22:11:52
     */
    @ReqValidGroup()
    @GetMapping(UrlContent.NET_TEST_REMOTE_GET)
    public ResultObj<String> testRemoteGet(@PathVariable("data") String body) {
        log.warn("testRemoteGet 请求过来的数据为：{}", body);
        return ResultObj.success("testRemoteGet successful.");
    }

    /**
     * 普通测试
     * @author Jomkie
     * @since 2021-05-20 15:19:59
     */
    @ReqValidGroup()
    @GetMapping(UrlContent.NET_TEST_NORMAL)
    public ResultObj<String> normal() {
        String result = testService.testNormal();
        log.info("我接收到的信息是：{}", result);
        return ResultObj.success("normal successful");
    }

    /**
     * 测试微信支付接口
     * @author Jomkie
     * @since 2021-05-20 15:39:36
     */
    @ReqValidGroup()
    @GetMapping(UrlContent.NET_TEST_WECHAT_PAY)
    public ResultObj<String> wecahtPay() {
        String result = testService.testWechatPay();
        log.info("我接口层拿到数据是：{}", result);
        return ResultObj.success(result);
    }

    /**
     * 测试获取微信支付平台证书
     * @author Jomkie
     * @since 2021-05-23 22:22:55
     */
    @GetMapping(UrlContent.NET_TEST_GET_WECHAT_PLATFORM)
    public ResultObj<String> getWechatPlatform() {
        String result = testService.getWechatPlatform();
        return ResultObj.success(result);
    }

    /**
     * 测试 redis 放入 list
     * @author Jomkie
     * @since 2021-07-07 20:49:30
     */
    @GetMapping(UrlContent.NET_TEST_PUT_LIST_FOR_REDIS)
    public ResultObj<String> putListForRedis(@PathVariable("redisKey") String redisKey) {
        String result = testService.putListForRedis(redisKey);
        return ResultObj.success(result);
    }

    /**
     * 测试 redis 获取 list
     * @author Jomkie
     * @since 2021-07-07 20:52:48
     */
    @GetMapping(UrlContent.NET_TEST_GET_LIST_FOR_REDIS)
    public ResultObj<String> getListForRedis(@PathVariable("redisKey") String redisKey) {
        String result = testService.getListForRedis(redisKey);
        return ResultObj.success(result);
    }

    /**
     * 循环拿 list 的数据
     * @author Jomkie
     * @since 2021-07-08 14:57:41
     * @param redisKey
     */
    @GetMapping(UrlContent.NET_TEST_LOOP_GET_LIST_FOR_REDIS)
    public ResultObj<String> loopGetListForRedis(@PathVariable("redisKey") String redisKey) {
        String result = testService.loopGetListForRedis(redisKey);
        return ResultObj.success(result);
    }

    /**
     * 测试 trim 方法
     * @author Jomkie
     * @since 2021-07-08 15:40:44
     * @param redisKey
     */
    @GetMapping(UrlContent.NET_TEST_TRIM_REDIS)
    public ResultObj<String> trimRedis(@PathVariable("redisKey") String redisKey) {
        String result = testService.trimRedis(redisKey);
        return ResultObj.success(result);
    }

    /**
     * 任何测试
     * @author Jomkie
     * @since 2021-07-21 11:17:16
     * @param anyData 任何数据
     */
    @GetMapping(UrlContent.NET_TEST_ANY)
    public ResultObj<String> anyTest(@PathVariable("anyData") String anyData) {
        String result = testService.anyTest(anyData);
        return ResultObj.success(result);
    }

}
