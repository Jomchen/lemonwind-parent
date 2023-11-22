package com.lemonwind.common;

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
    String NET_USER_HANDLE_PAGE_JOUSER = "/net/user/handle/page/jouser";

    String NET_TEST_HOT_SWAP = "/net/test/hot/swap";
    String NET_GENERATE_QRCODE_IMAGE = "/net/test/generate/qrcode/image/{redisKey}";
    String NET_TEST_REDIS_SAVE = "/net/test/redis/save";
    String NET_TEST_REDIS_GET = "/net/test/redis/get/{redisKey}";
    String NET_TEST_REDIS_DELETE = "/net/test/redis/delete/{redisKey}";
    String NET_TEST_REMOTE_POST = "/net/test/remote/post";
    String NET_TEST_REMOTE_GET = "/net/test/remote/get/{data}";
    String NET_TEST_NORMAL = "/net/test/normal";
    String NET_TEST_WECHAT_PAY = "/net/test/wechat/pay";
    String NET_TEST_GET_WECHAT_PLATFORM = "/net/test/get/wechat/platform";
    String NET_TEST_PUT_LIST_FOR_REDIS = "/net/test/put/list/for/redis/{redisKey}";
    String NET_TEST_GET_LIST_FOR_REDIS = "/net/test/get/list/for/redis/{redisKey}";
    String NET_TEST_LOOP_GET_LIST_FOR_REDIS = "/net/loop/get/list/for/redis/{redisKey}";
    String NET_TEST_TRIM_REDIS = "/net/trim/redis/{redisKey}";
    String NET_TEST_ANY = "/net/test/any/{anyData}";

    String NET_AOP_TEST_AOP_SERVER_AND_LOG = "/net/aop/test/aop/server/and/log";

}
