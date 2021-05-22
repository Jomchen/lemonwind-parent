package com.jomkie.common.wechat;

import com.jomkie.common.remote.RemoteApi;
import com.jomkie.common.remote.RemoteRequestObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/**
 * @author Jomkie
 * @since 2021-05-23 0:7:31
 * 微信平台证书类
 */
@Component
public class WeChatPlatform {

    @Autowired
    private WeChatAuthentication weChatAuthentication;

    @Autowired
    private RemoteApi remoteApi;

    /** 微信平台证书下载请求地址 */
    public static final String WECHAT_REQUEST_URL = "https://api.mch.weixin.qq.com/v3/certificates";

    /**
     * @author Jomkie
     * @since 2021-05-23 0:24:34
     * 获取平台证书
     */
    /**
     * @author Jomkie
     * @since 2021-05-23 0:36:47
     * @param nonceStr 随机字符串
     * @param date 当前日期，作为时间戳作为签名
     */
    public String getPlatform(String nonceStr, Date date) {
        HttpMethod httpMethod = HttpMethod.GET;
        String authorization = weChatAuthentication.getAuthorization(nonceStr, date, httpMethod, WECHAT_REQUEST_URL, null);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        /*headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));*/
        headers.set("User-Agent", "https://zh.wikipedia.org/wiki/User_agent"); // TODO 这里应该填什么
        headers.set("Authorization", authorization);

        RemoteRequestObj<Void> remoteRequestObj = new RemoteRequestObj<>(WECHAT_REQUEST_URL, null);
        String result = remoteApi.postRequest(headers, httpMethod, remoteRequestObj, String.class);
        return result;
    }

}
