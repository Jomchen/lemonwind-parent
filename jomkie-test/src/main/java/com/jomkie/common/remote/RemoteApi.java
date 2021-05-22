package com.jomkie.common.remote;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.common.LemonException;
import com.jomkie.common.Responsecode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Jomkie
 * @since 2021-05-20 14:42:36
 * 远程请求工具
 */
@Component
@Slf4j
public class RemoteApi {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @author Jomkie
     * @since 2021-05-23 2:50:58
     * @param requestUrl 请求地址
     * @param requestMethod 请求方法
     * @param requestHeaders 请求头
     * @param requestBody 请求体
     * @param responseClass 响应实体类
     */
    public <T, R> RemoteRequestObj<R> postRequest(String requestUrl, HttpMethod requestMethod, HttpHeaders requestHeaders, T requestBody, Class<R> responseClass) {
        String dataJsonStr = requestBody instanceof JSONObject ? ((JSONObject) requestBody).toJSONString() : JSONObject.toJSONString(requestBody);
        log.info("The request parameter is：{}", dataJsonStr);

        // 封装请求体
        HttpEntity<T> httpEntity;
        if (requestMethod == HttpMethod.GET) {
            httpEntity = new HttpEntity<>(requestHeaders);
        } else {
            httpEntity = new HttpEntity<>(requestBody, requestHeaders);
        }

        // 远程请求
        ResponseEntity<R> responseEntity;
        try {
            responseEntity = restTemplate.exchange(requestUrl, requestMethod, httpEntity, responseClass);
        } catch (Exception e) {
            throw new LemonException(Responsecode.REMOTE_ERROR, e);
        }
        if (Objects.isNull(responseEntity)) { throw new LemonException(Responsecode.REMOTE_NO_RESPONSE); }
        HttpStatus responseStatus = verifySuccessful(responseEntity.getStatusCodeValue(), null);
        if (Objects.isNull(responseStatus)) {
            throw new LemonException(Responsecode.REMOTE_FAIL);
        }
        HttpHeaders responseHeaders = responseEntity.getHeaders();

        // 请求结果
        log.warn("The statusCode of remote  code and message is: {} <--> {}", responseEntity.getStatusCodeValue(), Responsecode.REMOTE_FAIL.getMsg());
        R responseData = responseEntity.getBody();
        return RemoteRequestObj.build(requestUrl, requestMethod, responseHeaders, responseData);
    }

    /**
     * @author Jomkie
     * @since 2021-05-22 11:40:38
     * @param statusCode 返回的状态码
     * @param statusList 状态码所属枚举
     * 获取成功的状态，如果有指定状态集则在状态集中寻找
     */
    private HttpStatus verifySuccessful(int statusCode, List<HttpStatus> statusList) {
        if ( ! CollectionUtils.isEmpty(statusList)) {
            return statusList.stream().filter(e -> Objects.equals(statusCode, e.value())).findAny().orElse(null);
        }

        return Arrays.asList(
                HttpStatus.OK, HttpStatus.CREATED,
                HttpStatus.ACCEPTED, HttpStatus.NON_AUTHORITATIVE_INFORMATION,
                HttpStatus.NO_CONTENT, HttpStatus.RESET_CONTENT, HttpStatus.PARTIAL_CONTENT,
                HttpStatus.MULTI_STATUS, HttpStatus.ALREADY_REPORTED, HttpStatus.IM_USED
        ).stream().filter(e -> Objects.equals(statusCode, e.value())).findAny().orElse(null);
    }

}
