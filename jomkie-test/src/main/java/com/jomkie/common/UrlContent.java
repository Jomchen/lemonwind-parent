package com.jomkie.common;

/**
 * @author Jomkie
 * @since 2021/3/27 23:12:39
 * URL 地址
 */
public interface UrlContent {

    String NET_USER_GET_ALL = "/net//user/get/all";
    String NET_USER_GET_ONE = "/net/user/getOne/{id}";
    String NET_USER_ADD = "/net/user/add";
    String NET_USER_UPDATE = "/net/user/update";
    String NET_USER_DEL = "/net/user/del/{id}";
    String NET_USER_CHECK_BUILD_PARAM = "/net/user/check/build/param";
    String NET_USER_TEST_JOUSER = "/net/user/test/jouser";

    String GENERATE_QRCODE_IMAGE = "/net/test/generate/qrcode/image";
    String NET_TEST_REMOTE_POST = "/net/test/remote/post";
    String NET_TEST_REMOTE_GET = "/net/test/remote/get/{data}";
    String NET_TEST_NORMAL = "/net/test/normal";
    String NET_TEST_WECHAT_PAY = "/net/test/wechat/pay";

    String NET_AOP_TEST_AOP_SERVER_AND_LOG = "/net/aop/test/aop/server/and/log";

}
