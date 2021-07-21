package com.jomkie.service.impl;

import com.jomkie.common.redis.RedisTool;
import com.jomkie.common.remote.RemoteApi;
import com.jomkie.common.wechat.action.WeChatNativePay;
import com.jomkie.common.wechat.action.WeChatPlatformCertification;
import com.jomkie.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private WeChatPlatformCertification weChatPlatformCertification;

    @Autowired
    private WeChatNativePay weChatNativePay;

    @Autowired
    private RemoteApi remoteApi;

    @Autowired
    private RedisTool redisTool;

    @Override
    public String testNormal() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));

        Map<String, String> map = new HashMap<>();
        map.put("takeStockCode", "takeStockCode");
        map.put("takeStockName", "takeStockName");

        // "http://127.0.0.1:8088/take/stock/find/page/status/list/list",
        return remoteApi.execute(
                "http://127.0.0.1:8088/take/stock/find/page/status/list",
                HttpMethod.POST,
                headers,
                map,
                String.class
        ).getData();
    }

    @Override
    public String testWechatPay() {
        String data = weChatNativePay.wechatPay();
        return data;
    }

    @Override
    public String getWechatPlatform() {
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        String platform = weChatPlatformCertification.getPlatformList(nonceStr, new Date());
        return platform;
    }

    @Override
    public String putListForRedis(String redisKey) {
        // leftPush 相当于压入栈，rightPush 相当于加入队尾
        // range 或其它有起始和结束范围的方法 都是基于栈顶或队头取元素，第一个元素索引是 0
        ListOperations<String, String> listOperations = redisTool.getStrRedisTemplate().opsForList();
        IntStream.range(0, 10).boxed().forEach(index -> {
            listOperations.rightPush(redisKey, String.valueOf(index));
        });
        return "成功";
    }

    @Override
    public String getListForRedis(String redisKey) {
        ListOperations<String, String> listOperations = redisTool.getStrRedisTemplate().opsForList();
        List<String> list = listOperations.range(redisKey, 0, 4);
        return list.stream().collect(Collectors.joining(","));
    }

    @Override
    public String loopGetListForRedis(String redisKey) {
        ListOperations<String, String> listOperations = redisTool.getStrRedisTemplate().opsForList();
        Long size = listOperations.size(redisKey);
        if (null == size && size <= 0) {
            log.warn("redisKey 为 {} 查询没有信息", redisKey);
        }

        final int width = 3;
        int startIndex = 0;
        int endIndex = startIndex + width - 1;
        while (startIndex <= size) {
            List<String> list = listOperations.range(redisKey, startIndex, endIndex);
            list.stream().forEach(log::info);
            startIndex += width;
            endIndex = startIndex + width - 1;
            log.info("------------------------------------------------------");
        }

        return "执行完成";
    }

    @Override
    public String trimRedis(String redisKey) {
        // 删除除索引范围外的数据
        ListOperations<String, String> listOperations = redisTool.getStrRedisTemplate().opsForList();
        listOperations.trim(redisKey, 0, 5);
        return "执行完成";
    }

    @Override
    public String anyTest(String anyData) {
        return System.currentTimeMillis() + anyData;
    }

}
