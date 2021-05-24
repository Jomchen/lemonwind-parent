package com.jomkie.common.wechat;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.common.redis.RedisTool;
import com.jomkie.common.remote.RemoteApi;
import com.jomkie.common.remote.RemoteRequestObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Jomkie
 * @since 2021-05-24 20:59:44
 * 微信扫码支付
 */
@Component
public class WeChatNativePay implements WeChatRequestBase<Map<String, String>, String> {

    @Autowired
    private RemoteApi remoteApi;

    @Autowired
    private RedisTool redisTool;

    @Autowired
    private WeChatAuthentication weChatAuthentication;

    @Override
    public HttpHeaders preHeaders(String url, HttpMethod httpMethod, Map<String, String> requestData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        String requestBodyJson = JSONObject.toJSONString(requestData);
        String authorization = weChatAuthentication.getAuthorization(
                nonceStr,
                new Date(),
                HttpMethod.POST,
                WeChatPayBuild.WECHAT_PAY_URL,
                requestBodyJson
        );
        headers.set("Authorization", authorization);

        return headers;
    }

    @Override
    public void postHandler(Map<String, String> requestData, RemoteRequestObj<String> resultObj) {
        String orderId = requestData.get(WeChatPayBuild.OUT_TRADE_NO);
        String redisKey = WeChatCacheKeyGenerator.qrcodeMsg(WeChatCacheKeyEnum.QRCODE_CONTENT, orderId);

        String resultJson = resultObj.getData();
        JSONObject resultJsonObj = JSONObject.parseObject(resultJson);
        String content = Optional.ofNullable(resultJsonObj.get("code_url")).map(Object::toString).orElse(null);

        // TODO 如果 content 为空，此时应该做处理
        redisTool.set(redisKey, content);
    }

}
