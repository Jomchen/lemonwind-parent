package com.jomkie.common.remote;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.common.LemonException;
import com.jomkie.common.RequestObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
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
     * @since 2021-05-20 14:47:2
     * @param obj 远程请求封装工具
     */
    public <T> String postRequest(RemoteRequestObj<T> obj) {
        String url = obj.getUrl();
        T data = obj.getData();
        String requestDataJonsStr = JSONObject.toJSONString(data);
        JSONObject requestDataJsonObj = JSONObject.parseObject(requestDataJonsStr);
        log.info("远程请求微信参数为：{}", requestDataJonsStr);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(requestDataJsonObj, requestHeaders);

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, requestEntity, Object.class);
        if (Objects.isNull(responseEntity)) { throw new LemonException("远程请求微信异常"); }
        if ( ! Objects.equals(responseEntity.getStatusCodeValue(), HttpStatus.OK.value())) {
            throw new LemonException("请求不成功，异常码为：{}" + responseEntity.getStatusCodeValue());
        }

        Object result = responseEntity.getBody();
        String resultJsonStr = JSONObject.toJSONString(result);
        log.info("微信请求结果值为：{}", resultJsonStr);

        return JSONObject.toJSONString(resultJsonStr);
    }

}
