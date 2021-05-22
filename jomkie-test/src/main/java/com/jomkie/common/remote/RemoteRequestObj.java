package com.jomkie.common.remote;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 * @author Jomkie
 * @since 2021-05-20 14:46:48
 * 远程请求封装
 */
@Data
@NoArgsConstructor
public class RemoteRequestObj <T> {

    private String url;
    private HttpMethod httpMethod;
    private HttpHeaders httpHeaders;
    private T data;

    public RemoteRequestObj(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, T data) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.httpHeaders = httpHeaders;
        this.data = data;
    }


    public static <T> RemoteRequestObj<T> build(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, T data) {
        return new RemoteRequestObj<>(url, httpMethod, httpHeaders, data);
    }

}
