package com.lemonwind.common.wechat;

/**
 * @author Jomkie
 * @since 2021-05-24 22:4:21
 * 微信相关缓存类
 */
public class WeChatCacheKeyGenerator {

    /**
     * @author Jomkie
     * @since 2021-05-24 22:3:59
     * @param cacheKeyEnum 枚举
     * @param seed 种子，注意此参数和相应枚举的组合需保持唯一，否则会覆盖其它缓存
     * 生成微信相关的缓存
     */
    public static String qrcodeMsg(WeChatCacheKeyEnum cacheKeyEnum, String seed) {
        return new StringBuilder().append(cacheKeyEnum.getCode()).append(seed).toString();
    }

}
