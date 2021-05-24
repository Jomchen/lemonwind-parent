package com.jomkie.service.impl;

import com.jomkie.common.remote.RemoteApi;
import com.jomkie.common.wechat.action.WeChatNativePay;
import com.jomkie.common.wechat.action.WeChatPlatformCertification;
import com.jomkie.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private WeChatPlatformCertification weChatPlatformCertification;

    @Autowired
    private WeChatNativePay weChatNativePay;

    @Autowired
    private RemoteApi remoteApi;

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

}
