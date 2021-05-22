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

import java.nio.charset.StandardCharsets;
import java.util.*;

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
     * @since 2021-05-20 21:52:27
     * @param obj 请求封装对象
     * @param headers 请求头
     * @param responseClass 返回对象类
     */
    public <T, R> R postRequest(HttpHeaders headers, RemoteRequestObj<T> obj, Class<R> responseClass) {
        String url = obj.getUrl();
        T data = obj.getData();
        String dataJsonStr = data instanceof JSONObject ? ((JSONObject) data).toJSONString() : JSONObject.toJSONString(data);
        log.info("The request parameter is：{}", dataJsonStr);

        // 封装请求体
        HttpEntity<T> httpEntity = new HttpEntity<>(data, headers);
        ResponseEntity<R> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseClass);
        } catch (Exception e) {
            throw new LemonException(Responsecode.REMOTE_ERROR, e);
        }
        if (Objects.isNull(responseEntity)) { throw new LemonException(Responsecode.REMOTE_NO_RESPONSE); }
        HttpStatus resultStatus = verifySuccessful(responseEntity.getStatusCodeValue(), null);
        if (Objects.isNull(resultStatus)) {
            throw new LemonException(Responsecode.REMOTE_FAIL);
        }

        log.warn("The statusCode of remote  code and message is: {} <--> {}", responseEntity.getStatusCodeValue(), Responsecode.REMOTE_FAIL.getMsg());
        R result = responseEntity.getBody();
        return result;
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
