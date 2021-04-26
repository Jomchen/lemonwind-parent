package com.jomkie.common;

/**
 * @author Jomkie
 * @date 2021/3/27 23:12:39
 * URL 地址
 */
public interface UrlContent {

    String NET_USER_GET_ALL = "/net//user/get/all";
    String NET_USER_GET_ONE = "/net/user/getOne/{id}";
    String NET_USER_ADD = "/net/user/add";
    String NET_USER_UPDATE = "/net/user/update";
    String NET_USER_DEL = "/net/user/del/{id}";

    String NET_TEST_REMOTE = "/net/test/remote";
    String NET_TEST_CUSTOMER_VALID = "/net/test/customer/valid";

}
