package com.lemonwind.common.remote;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Jomkie
 * @since 2021-05-20 14:46:48
 * 远程请求封装
 */
@Data
@NoArgsConstructor
public class RemoteRequestObj <T> {

    /** 请求地址 */
    private String url;
    /** 请求方法 */
    private HttpMethod httpMethod;
    /** 请求头/响应头 */
    private HttpHeaders httpHeaders;
    /** 请求体/响应体 */
    private T data;

    /**
     * @author Jomkie
     * @since 2021-05-23 12:50:8
     * @param obj 将对象输出为 json 字符串
     */
    public static String dataToJsonStr(Object obj) {
        return Objects.isNull(obj) ? null : obj instanceof JSONObject ? ((JSONObject) obj).toJSONString() : JSONObject.toJSONString(obj);
    }

    public RemoteRequestObj(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, T data) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.httpHeaders = httpHeaders;
        this.data = data;
    }

    public static <T> RemoteRequestObj<T> build(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, T data) {
        return new RemoteRequestObj<>(url, httpMethod, httpHeaders, data);
    }

    @Override
    public String toString() {
        final String NO_DATA = "<no data>";

        String toStringUrl = Objects.isNull(url) ? NO_DATA : url;

        String methodName = Objects.isNull(httpMethod) ? NO_DATA : httpMethod.name();

        String httpHeadersStr = Optional.ofNullable(httpHeaders)
                .filter(mapObj -> mapObj.size() > 0)
                .map(header -> {
                    StringBuilder temporaryBuilder = new StringBuilder();
                    Map<String, String> headersMap = header.toSingleValueMap();
                    headersMap.entrySet().stream().forEach(
                            entry -> temporaryBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n")
                    );
                    return temporaryBuilder.toString();
                }).orElse(NO_DATA);

        String dataJsonStr = Optional.ofNullable(data).map(RemoteRequestObj::dataToJsonStr).orElse(NO_DATA);

        return new StringBuilder()
                .append("url -> ").append(toStringUrl).append("\n")
                .append("methodName -> ").append(methodName).append("\n")
                .append("httpHeadersStr -> ").append(httpHeadersStr).append("\n")
                .append("data ->").append(dataJsonStr).append("\n")
                .toString();
    }

}
