package com.jomkie.common;

import java.util.UUID;

/**
 * @author Jomkie
 * @since 2021-04-15 22:37:46
 * id 生成器
 */
public class IdGenerator {

    /**
     * @author Jomkie
     * @since 2021-04-15 22:38:36
     * 请求id生成
     */
    public static String getRequestId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
