package com.jomkie.common.remote;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.common.LemonException;
import com.jomkie.common.Responsecode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
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
     * @since 2021-05-20 21:52:27
     * @param obj 请求封装对象
     * @param responseClass 返回对象类
     */
    public <T, R> R postRequest(RemoteRequestObj<T> obj, Class<R> responseClass) {
        String url = obj.getUrl();
        T data = obj.getData();
        String dataJsonStr = data instanceof JSONObject ? ((JSONObject) data).toJSONString() : JSONObject.toJSONString(data);
        log.info("remote parameter is：{}", dataJsonStr);

        // 封装请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));

        // 封装请求体
        HttpEntity<String> httpEntity = new HttpEntity<>(dataJsonStr, headers);
        ResponseEntity<R> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseClass);
        } catch (Exception e) {
            throw new LemonException(Responsecode.REMOTE_ERROR, e);
        }
        if (Objects.isNull(responseEntity)) { throw new LemonException(Responsecode.REMOTE_NO_RESPONSE); }
        if ( ! Objects.equals(responseEntity.getStatusCodeValue(), HttpStatus.OK.value())) {
            log.warn("remote failed code and message is: {} <--> {}", responseEntity.getStatusCodeValue(), Responsecode.REMOTE_FAIL.getMsg());
            throw new LemonException(Responsecode.REMOTE_FAIL);
        }

        R result = responseEntity.getBody();
        return result;
    }

}
