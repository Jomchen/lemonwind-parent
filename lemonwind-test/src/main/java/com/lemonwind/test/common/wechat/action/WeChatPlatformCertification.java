package com.lemonwind.test.common.wechat.action;

import com.lemonwind.test.common.remote.RemoteApi;
import com.lemonwind.test.common.remote.RemoteRequestObj;
import com.lemonwind.test.common.wechat.WeChatAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Jomkie
 * @since 2021-05-23 0:7:31
 * 微信平台证书类
 */
@Component
public class WeChatPlatformCertification {

    @Autowired
    private WeChatAuthentication weChatAuthentication;

    @Autowired
    private RemoteApi remoteApi;

    /** 微信平台证书下载请求地址 */
    public static final String WECHAT_REQUEST_URL = "https://api.mch.weixin.qq.com/v3/certificates";

    /**
     * @author Jomkie
     * @since 2021-05-23 0:36:47
     * @param nonceStr 随机字符串
     * @param date 当前日期，作为时间戳作为签名
     *
     * 频率说明：
     *     在启用新的平台证书前，微信支付会提前24小时把新证书加入到平台证书列表中
     *     接口的频率限制: 单个商户号1000 次/s，
     *
     * 如果自行实现验证平台签名逻辑的话，需要注意：
     *     1. 程序实现定期更新平台证书的逻辑
     *     2. 定期调用该接口，间隔时间小于12 小时
     *     3. 加密请求消息中的敏感信息时，使用最新的平台证书（即：证书启用时间较晚的证书）
     *
     * 获取平台证书列表
     */
    public String getPlatformList(String nonceStr, Date date) {
        HttpHeaders headers = weChatAuthentication.getPlatformCertificationsHeaders(nonceStr, date);
        RemoteRequestObj<String> result = remoteApi.execute(WECHAT_REQUEST_URL, HttpMethod.GET, headers, null, String.class);
        postHandler(result);
        return result.getData();
    }

    private void postHandler(RemoteRequestObj<String> resultObj) {
        // TODO 请求结果验证 和 平台证书缓存
    }

}
