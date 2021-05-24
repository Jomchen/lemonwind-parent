package com.jomkie.common.remote;

import com.jomkie.common.LemonException;
import com.jomkie.common.Responsecode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
     * <T> 你请求的数据类型
     * <R> 你想返回的数据类型
     * @param requestUrl 请求地址
     * @param requestMethod 请求方法
     * @param requestHeaders 请求头
     * @param requestBody 请求体
     * @param responseClass 响应实体类
     */
    public <T, R> RemoteRequestObj<R> postRequest(
            String requestUrl, HttpMethod requestMethod,
            HttpHeaders requestHeaders, T requestBody, Class<R> responseClass) {

        String dataJsonStr = RemoteRequestObj.dataToJsonStr(requestBody);
        log.info("The remote request parameter is：{}", dataJsonStr);

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
        HttpStatus responseStatus = getHttpStatus(responseEntity.getStatusCodeValue());
        if (verifySuccessful(responseStatus.value(), null)) {
            String msg = new StringBuilder().append(Responsecode.REMOTE_FAIL.getMsg())
                    .append("，状态为：")
                    .append(responseStatus.value()).toString();
            throw new LemonException(Responsecode.REMOTE_FAIL, msg);
        }
        HttpHeaders responseHeaders = responseEntity.getHeaders();

        // 请求结果处理
        R responseData = responseEntity.getBody();
        RemoteRequestObj<R> resultDetials = RemoteRequestObj.build(requestUrl, requestMethod, responseHeaders, responseData);
        log.warn(
                "-------------------\n The statusCode and detials of a request is: \nstatusCode: {} \nresultDetials: \n{} \n-------------------\n",
                responseEntity.getStatusCodeValue(),
                resultDetials
        );
        return resultDetials;
    }

    /**
     * @author Jomkie
     * @since 2021-05-22 11:40:38
     * @param statusCode 返回的状态码
     * @param statusList 状态码所属枚举
     * 获取成功的状态，如果有指定状态集则在状态集中寻找
     */
    private boolean verifySuccessful(int statusCode, List<HttpStatus> statusList) {
        if ( ! CollectionUtils.isEmpty(statusList)) {
            return statusList.stream().anyMatch(s -> s.value() == statusCode);
        }

        return Stream.of(
                HttpStatus.OK, HttpStatus.CREATED,
                HttpStatus.ACCEPTED, HttpStatus.NON_AUTHORITATIVE_INFORMATION,
                HttpStatus.NO_CONTENT, HttpStatus.RESET_CONTENT, HttpStatus.PARTIAL_CONTENT,
                HttpStatus.MULTI_STATUS, HttpStatus.ALREADY_REPORTED, HttpStatus.IM_USED
        ).anyMatch(s -> s.value() == statusCode);
    }

    /**
     * @author Jomkie
     * @since 2021-05-24 20:42:21
     * @param statusCode 状态码
     * 获取状态对象
     */
    public HttpStatus getHttpStatus(int statusCode) {
        return Arrays.stream(HttpStatus.values())
                .filter(s -> s.value() == statusCode)
                .findFirst()
                .orElseThrow(() -> new LemonException("Not found the status code of your remote request."));
    }

}
