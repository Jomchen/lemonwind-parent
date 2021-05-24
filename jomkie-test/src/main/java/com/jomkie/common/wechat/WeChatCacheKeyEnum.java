package com.jomkie.common.wechat;

import lombok.Getter;

/**
 * @author Jomkie
 * @since 2021-05-24 22:5:51
 * 微信缓存相关枚举
 */
@Getter
public enum WeChatCacheKeyEnum {

    QRCODE_CONTENT("com:jomkie:wechat:native:qrcode:", "二维码的扫码链接"),

    ;

    private String code;
    private String description;

    WeChatCacheKeyEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
