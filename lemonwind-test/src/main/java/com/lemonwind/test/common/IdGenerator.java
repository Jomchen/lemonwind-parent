package com.lemonwind.test.common;

import java.util.UUID;

/**
 * @author lemonwind
 * @since 2021-04-15 22:37:46
 * id 生成器
 */
public class IdGenerator {

    /**
     * @author lemonwind
     * @since 2021-04-15 22:38:36
     * 请求id生成
     */
    public static String getRequestId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
