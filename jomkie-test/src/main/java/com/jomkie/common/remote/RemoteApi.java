package com.jomkie.common.remote;

import com.alibaba.fastjson.JSONObject;
import com.jomkie.common.LemonException;
import com.jomkie.common.RequestObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.FormSubmitEvent;
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
        String dataJsonStr = JSONObject.toJSONString(data);
        JSONObject dataJsonObj = JSONObject.parseObject(dataJsonStr);
        log.info("远程请求微信参数为：{}", dataJsonStr);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(data, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (Objects.isNull(responseEntity)) { throw new LemonException("远程请求微信异常"); }
        if ( ! Objects.equals(responseEntity.getStatusCodeValue(), HttpStatus.OK.value())) {
            throw new LemonException("请求不成功，异常码为：{}" + responseEntity.getStatusCodeValue());
        }

        Object result = responseEntity.getBody();
        if (Objects.isNull(result)) { throw new LemonException("远程请求没有返回值"); }
        log.info("微信请求未反序列化结果值为：{}", result);

        return result.toString();
    }

}
