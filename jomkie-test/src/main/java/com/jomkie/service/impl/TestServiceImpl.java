package com.jomkie.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.common.LemonException;
import com.jomkie.common.Responsecode;
import com.jomkie.common.pay.wechat.WeChatRequestParam;
import com.jomkie.common.remote.RemoteApi;
import com.jomkie.common.remote.RemoteRequestObj;
import com.jomkie.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TestServiceImpl implements TestService {

    String appid = "appid";
    String mchid = "mchid";

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

        return remoteApi.postRequest(headers,
                RemoteRequestObj.build("http://127.0.0.1:8088/take/stock/find/page/status/list", map),
                String.class
        );

        /*return remoteApi.postRequest(
                headers,
                RemoteRequestObj.build("http://127.0.0.1:8088/take/stock/find/page/status/list",map),
                String.class
        );*/
    }

    @Override
    public String testWechatPay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());


        String description = "测试商品" + timestamp;
        String outTradeNo = "订单id" + timestamp;
        String notifyUrl = "http://www.jomchen.top/net/test/remote";
        int amountMoneyTotal = 1;
        String amountMoneyCurrency = "CNY";

        // 封装请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));

        // 请求微信的过程
        JSONObject requestData = WeChatRequestParam.buildParam(
                appid,
                mchid,
                description,
                outTradeNo,
                notifyUrl,
                amountMoneyTotal,
                amountMoneyCurrency
        ).getRequestObj();
        RemoteRequestObj<JSONObject> remoteRequestObj = RemoteRequestObj.build(WeChatRequestParam.WECHAT_PAY_URL, requestData);
        String result = remoteApi.postRequest(headers, remoteRequestObj, String.class);

        return Optional.ofNullable(result).filter(Objects::nonNull).orElseThrow(() -> new LemonException(Responsecode.FAILE));
    }

}
