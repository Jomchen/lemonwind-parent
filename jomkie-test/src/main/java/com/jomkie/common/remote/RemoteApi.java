package com.jomkie.common.remote;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.common.LemonException;
import com.jomkie.common.Responsecode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
     * @since 2021-05-20 14:47:2
     * @param obj 远程请求封装工具
     */
    public <T> String postRequest(RemoteRequestObj<T> obj) {
        String url = obj.getUrl();
        T data = obj.getData();
        String dataJsonStr = JSONObject.toJSONString(data);
        JSONObject dataJsonObj = JSONObject.parseObject(dataJsonStr);
        log.info("远程请求微信参数为：{}", dataJsonStr);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data, headers);

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            throw new LemonException(Responsecode.REMOTE_ERROR, e);
        }
        if (Objects.isNull(responseEntity)) { throw new LemonException(Responsecode.REMOTE_NO_RESPONSE); }
        if ( ! Objects.equals(responseEntity.getStatusCodeValue(), HttpStatus.OK.value())) {
            log.warn(Responsecode.REMOTE_FAIL.getMsg() + "，异常码为：{}", responseEntity.getStatusCodeValue());
            throw new LemonException(Responsecode.REMOTE_FAIL);
        }

        Object result = responseEntity.getBody();
        return result.toString();
    }

}
