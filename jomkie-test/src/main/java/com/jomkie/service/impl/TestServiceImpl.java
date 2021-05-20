package com.jomkie.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.common.pay.wechat.WeChatRequestParam;
import com.jomkie.common.remote.RemoteApi;
import com.jomkie.common.remote.RemoteRequestObj;
import com.jomkie.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private RemoteApi remoteApi;

    @Override
    public String testNormal() {
        Map<String, String> map = new HashMap<>();
        map.put("takeStockCode", "takeStockCode");
        map.put("takeStockName", "takeStockName");

        String result = remoteApi.postRequest(RemoteRequestObj.build(
                "http://127.0.0.1:8088/take/stock/find/page/status/list",
                map)
        );
        return result;
    }

    @Override
    public String testWechatPay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());

        String appid = "appid";
        String mchid = "mchid";
        String description = "测试商品" + timestamp;
        String outTradeNo = "订单id" + timestamp;
        String notifyUrl = "http://www.jomchen.top/net/test/remote";
        int amountMoneyTotal = 1;
        String amountMoneyCurrency = "CNY";

        Map<Object, Object> requestMap = WeChatRequestParam.buildParam(
                appid,
                mchid,
                description,
                outTradeNo,
                notifyUrl,
                amountMoneyTotal,
                amountMoneyCurrency
        ).getRequestMap();
        String result = remoteApi.postRequest(RemoteRequestObj.build(
                WeChatRequestParam.WECHAT_PAY_URL,
                requestMap)
        );

        /*Map<String, String> map = new HashMap<>();
        map.put("takeStockCode", "takeStockCode");
        map.put("takeStockName", "takeStockName");
        String result = remoteApi.postRequest(RemoteRequestObj.build(
                "http://127.0.0.1:8088/take/stock/find/page/status/list",
                map)
        );*/
        return result;
    }

}
