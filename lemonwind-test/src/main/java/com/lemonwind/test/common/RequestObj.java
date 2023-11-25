package com.lemonwind.test.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lemonwind
 * @since 2021-04-15 22:32:29
 * 请求实体
 */
@Data
@NoArgsConstructor
public class RequestObj  <T> implements Serializable {

    private String requestId;

    private T data;

    public RequestObj (T data) {
        this.requestId = IdGenerator.getRequestId();
        this.data = data;
    }

    public RequestObj (String requestId, T data) {
        this.requestId = requestId;
        this.data = data;
    }

    public static <T> RequestObj<T> build(T data) {
        return new RequestObj<>(data);
    }

    public static <T> RequestObj<T> build(String requestId, T data) {
        return new RequestObj<>(requestId, data);
    }

}
