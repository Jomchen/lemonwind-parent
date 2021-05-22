package com.jomkie.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.common.LemonException;
import com.jomkie.common.Responsecode;
import com.jomkie.common.pay.wechat.PemUtil;
import com.jomkie.common.pay.wechat.WeChatPayBuild;
import com.jomkie.common.remote.RemoteApi;
import com.jomkie.common.remote.RemoteRequestObj;
import com.jomkie.service.TestService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.swing.text.html.FormSubmitEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    /** 商户的appid */
    String appid = "appid";
    /** 商户号 */
    String merchantId = "merchantId";
    /** 请求模式 */
    String schema = "WECHATPAY2-SHA256-RSA2048";
    /** 请求后的回调地址 */
    String notifyUrl = "http://www.jomchen.top/net/test/remote";
    /** 商户证书序列号 */
    String certificateSerialNo = "serial_no";
    /** 商户私钥文件路径 */
    String privateKeyPath = "";

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
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateFormat = sdf.format(currentDate);

        String description = "测试商品" + currentDateFormat;
        String outTradeNo = "订单id" + currentDateFormat;
        int amountMoneyTotal = 1;
        String amountMoneyCurrency = "CNY";

        // 请求体封装过程
        Map<String, Object> requestData = WeChatPayBuild.buildParam(
                appid,
                merchantId,
                description,
                outTradeNo,
                notifyUrl,
                amountMoneyTotal,
                amountMoneyCurrency
        ).getRequestMap();

        // 封装请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));
        headers.setContentType(MediaType.APPLICATION_JSON);
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        String requestBodyJson = JSONObject.toJSONString(requestData);
        String token = getToken(
                certificateSerialNo,
                nonceStr,
                currentDate,
                FormSubmitEvent.MethodType.POST.name(),
                WeChatPayBuild.WECHAT_PAY_URL,
                requestBodyJson
        );
        String authorization = new StringBuilder().append(schema).append(" ").append(token).toString();
        headers.set("Authorization", authorization);

        // 执行请求
        RemoteRequestObj<Map<String, Object>> remoteRequestObj = RemoteRequestObj.build(WeChatPayBuild.WECHAT_PAY_URL, requestData);
        String result = remoteApi.postRequest(headers, remoteRequestObj, String.class);
        return Optional.ofNullable(result).filter(Objects::nonNull).orElseThrow(() -> new LemonException(Responsecode.REMOTE_FAIL));
    }




    /**
     * @author Jomkie
     * @since 2021-05-22 21:48:17
     * @param certificateSerialNo 商户证书序列号
     * @param nonceStr 随机字符串
     * @param date 时间戳，会被处理成秒作为签名条件
     * @param method 方法类型：例如：POST, GET
     * @param requestUrl 请求地址
     * @param body 请求体的 JSON 字符串
     */
    String getToken(String certificateSerialNo, String nonceStr, Date date, String method, String requestUrl, String body) {
        HttpUrl httpUrl = HttpUrl.parse(requestUrl);
        long timestamp = date.getTime() / 1000;
        String message = buildMessage(method, httpUrl, timestamp, nonceStr, body);
        String signature = sign(message.getBytes(StandardCharsets.UTF_8));

        return "mchid=\"" + merchantId + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + certificateSerialNo + "\","
                + "signature=\"" + signature + "\"";
    }

    String sign(byte[] message) {
        Signature sign;
        try {
            sign = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            throw new LemonException("SHA256withRSA签名失败", e);
        }

        PrivateKey privateKey = getPrivateKey(privateKeyPath);
        try {
            sign.initSign(privateKey);
        } catch (InvalidKeyException e) {
            throw new LemonException("微信签名初始化私钥失败", e);
        }
        try {
            sign.update(message);
        } catch (SignatureException e) {
            throw new LemonException("微信签名处理失败", e);
        }

        byte[] signature;
        try {
            signature = sign.sign();
        } catch (SignatureException e) {
            throw new LemonException("微信签名处理失败", e);
        }
        return Base64.getEncoder().encodeToString(signature);
    }

    String buildMessage(String method, HttpUrl httpUrl, long timestamp, String nonceStr, String body) {
        String canonicalUrl = httpUrl.encodedPath();
        if (httpUrl.encodedQuery() != null) {
            canonicalUrl += "?" + httpUrl.encodedQuery();
        }

        if (Strings.isEmpty(body)) { body = ""; } // GET请求或没有请求体时，微信文档未表明清楚：是 空格 还是 空字符串，此处暂时表示空字符串
        return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

    PrivateKey getPrivateKey(String privateKeyPath) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(privateKeyPath);
        } catch (FileNotFoundException e) {
            throw new LemonException("获取商户私钥失败", e);
        }

        PrivateKey privateKey = PemUtil.loadPrivateKey(inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            log.error("关闭流失败", e);
        }
        return privateKey;
    }

}
