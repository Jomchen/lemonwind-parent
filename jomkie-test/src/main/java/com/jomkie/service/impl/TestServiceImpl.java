package com.jomkie.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.common.LemonException;
import com.jomkie.common.Responsecode;
import com.jomkie.common.remote.RemoteApi;
import com.jomkie.common.remote.RemoteRequestObj;
import com.jomkie.common.wechat.WeChatAuthentication;
import com.jomkie.common.wechat.WeChatPayBuild;
import com.jomkie.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private WeChatAuthentication weChatAuthentication;

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

        return remoteApi.postRequest(
                "http://127.0.0.1:8088/take/stock/find/page/status/list",
                HttpMethod.POST,
                headers,
                map,
                String.class
        ).getData();

        /*return remoteApi.postRequest(
                "http://127.0.0.1:8088/take/stock/find/page/status/list/list",
                HttpMethod.POST,
                headers,
                map,
                String.class
        ).getData();*/
    }

    @Override
    public String testWechatPay() {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateFormat = sdf.format(currentDate);

        String description = "测试商品" + currentDateFormat;
        String outTradeNo = "订单id" + currentDateFormat;
        int amountMoneyTotal = 1;
        String amountMoneyCurrency = "CNY";

        // 请求体封装过程
        Map<String, Object> requestData = WeChatPayBuild.buildParam(
                WeChatAuthentication.APPID,
                WeChatAuthentication.MERCHANTID,
                description,
                outTradeNo,
                WeChatPayBuild.WECHAT_NOTIFY_URL,
                amountMoneyTotal,
                amountMoneyCurrency
        ).getRequestMap();

        // 封装请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        String requestBodyJson = JSONObject.toJSONString(requestData);
        String authorization = weChatAuthentication.getAuthorization(
                nonceStr,
                currentDate,
                HttpMethod.POST,
                WeChatPayBuild.WECHAT_PAY_URL,
                requestBodyJson
        );
        headers.set("Authorization", authorization);

        // 执行请求
        RemoteRequestObj<String> result = remoteApi.postRequest(WeChatPayBuild.WECHAT_PAY_URL, HttpMethod.POST, headers, requestData, String.class);
        String resultData = result.getData();
        return Optional.ofNullable(resultData).filter(Objects::nonNull).orElseThrow(() -> new LemonException(Responsecode.REMOTE_FAIL));
    }

}
