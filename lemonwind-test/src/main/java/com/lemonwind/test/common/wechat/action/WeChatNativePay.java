package com.lemonwind.test.common.wechat.action;

import com.alibaba.fastjson.JSONObject;
import com.lemonwind.test.common.LemonException;
import com.lemonwind.test.common.UrlContent;
import com.lemonwind.test.common.redis.RedisTool;
import com.lemonwind.test.common.remote.RemoteApi;
import com.lemonwind.test.common.remote.RemoteRequestObj;
import com.lemonwind.test.common.wechat.WeChatAuthentication;
import com.lemonwind.test.common.wechat.WeChatCacheKeyEnum;
import com.lemonwind.test.common.wechat.WeChatCacheKeyGenerator;
import com.lemonwind.test.common.wechat.WeChatPayBuild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Jomkie
 * @since 2021-05-24 20:59:44
 * 微信扫码支付
 */
@Component
public class WeChatNativePay {

    /** 微信 Native 支付请求地址 */
    public static final String WECHAT_PAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/native";
    /** 请求后的回调地址 */
    public static final String WECHAT_NOTIFY_URL = "http://www.jomchen.top" + UrlContent.NET_TEST_REMOTE_POST;

    @Autowired
    private RemoteApi remoteApi;

    @Autowired
    private RedisTool redisTool;

    @Autowired
    private WeChatAuthentication weChatAuthentication;

    public String wechatPay() {
        // 信息声明
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateFormat = sdf.format(currentDate);

        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        String description = "测试商品" + currentDateFormat;
        String outTradeNo = "orderId" + currentDateFormat;
        int amountMoneyTotal = 1;
        String amountMoneyCurrency = "CNY";

        // 请求体封装过程
        Map<String, Object> requestData = WeChatPayBuild.buildParam(
                WeChatAuthentication.APPID,
                WeChatAuthentication.MERCHANTID,
                description,
                outTradeNo,
                WECHAT_NOTIFY_URL,
                amountMoneyTotal,
                amountMoneyCurrency
        ).getRequestMap();

        // 获取请求头
        HttpHeaders headers = weChatAuthentication.getBaseHttpHeaders(currentDate, requestData, HttpMethod.POST, WECHAT_PAY_URL, nonceStr);

        // 执行请求
        RemoteRequestObj<String> result = remoteApi.execute(WECHAT_PAY_URL, HttpMethod.POST, headers, requestData, String.class);

        // 后置处理
        postHandler(requestData, result);
        return result.getData();
    }

    public void postHandler(Map<String, Object> requestData, RemoteRequestObj<String> resultObj) {
        // TODO 请求结果验证 和 缓存

        String orderId = requestData.get(WeChatPayBuild.OUT_TRADE_NO).toString();
        String redisKey = WeChatCacheKeyGenerator.qrcodeMsg(WeChatCacheKeyEnum.QRCODE_CONTENT, orderId);

        String resultJson = resultObj.getData();
        JSONObject resultJsonObj = JSONObject.parseObject(resultJson);
        String content = Optional.ofNullable(resultJsonObj.get("code_url")).map(Object::toString).orElse(null);

        if (null == content || content.length() <= 0) { throw new LemonException("没有得到支付二维码地址"); }
        redisTool.set(redisKey, content, 2, TimeUnit.HOURS);
    }

}
