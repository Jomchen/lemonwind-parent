package com.jomkie.common.remote;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.common.RequestObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jomkie
 * @since 2021-05-20 14:42:36
 * 远程请求工具
 */
@Component
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

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(requestDataJsonObj, requestHeaders);
        Object result = restTemplate.postForObject(url, requestEntity, Object.class);
        return JSONObject.toJSONString(result);
    }

}
