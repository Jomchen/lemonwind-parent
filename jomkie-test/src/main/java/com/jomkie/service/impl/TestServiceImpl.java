package com.jomkie.service.impl;

import com.jomkie.common.LemonException;
import com.jomkie.common.Responsecode;
import com.jomkie.common.pay.wechat.WeChatRequestParam;
import com.jomkie.common.remote.RemoteApi;
import com.jomkie.common.remote.RemoteRequestObj;
import com.jomkie.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private RemoteApi remoteApi;

    @Override
    public String testNormal() {
        Map<String, String> map = new HashMap<>();
        map.put("takeStockCode", "takeStockCode");
        map.put("takeStockName", "takeStockName");

        return remoteApi.postRequest(
                RemoteRequestObj.build("http://127.0.0.1:8088/take/stock/find/page/status/list", map),
                String.class
        );
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

        // 请求微信的过程
        Map<String, Object> requestMap = WeChatRequestParam.buildParam(
                appid,
                mchid,
                description,
                outTradeNo,
                notifyUrl,
                amountMoneyTotal,
                amountMoneyCurrency
        ).getRequestMap();
        RemoteRequestObj<Map<String, Object>> remoteRequestObj = RemoteRequestObj.build(WeChatRequestParam.WECHAT_PAY_URL, requestMap);
        String result = remoteApi.postRequest(remoteRequestObj, String.class);

        // 请求内部接口的过程
        /*Map<String, String> map = new HashMap<>();
        map.put("takeStockCode", "takeStockCode");
        map.put("takeStockName", "takeStockName");
        String result = remoteApi.postRequest(RemoteRequestObj.build(
                "http://127.0.0.1:8088/take/stock/find/page/status/list",
                map)
        );*/

        return Optional.ofNullable(result).filter(Objects::nonNull).orElseThrow(() -> new LemonException(Responsecode.FAILE));
    }

}
