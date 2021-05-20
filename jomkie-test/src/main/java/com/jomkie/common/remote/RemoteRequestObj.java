package com.jomkie.common.remote;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jomkie
 * @since 2021-05-20 14:46:48
 * 远程请求封装
 */
@Data
@NoArgsConstructor
public class RemoteRequestObj <T> {

    private String url;
    private T data;

    public RemoteRequestObj(String url, T data) {
        this.url = url;
        this.data = data;
    }

    public static <T> RemoteRequestObj<T> build(String url, T data) {
        return new RemoteRequestObj<>(url, data);
    }

}
