package com.jomkie.service;

public interface TestService {

    /**
     * @author Jomkie
     * @since 2021-05-24 23:30:21
     * 测试普通远程请求
     */
    String testNormal();

    /**
     * @author Jomkie
     * @since 2021-05-24 23:30:10
     * 测试微信支付
     */
    String testWechatPay();

    /**
     * @author Jomkie
     * @since 2021-05-24 23:29:58
     * 获取微信支付平台证书
     */
    String getWechatPlatform();

}
