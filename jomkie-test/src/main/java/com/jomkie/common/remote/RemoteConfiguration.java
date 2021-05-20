package com.jomkie.common.remote;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jomkie
 * @since 2021-05-20 13:32:18
 * 远程请求工具配置
 */
@Configuration
public class RemoteConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        /*RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(5000L))
                .setReadTimeout(Duration.ofMillis(30000L))
                .build();*/

        RestTemplate restTemplate = new RestTemplate(getFactory());
        restTemplate.setMessageConverters(getConverts());
        return restTemplate;
    }

    private SimpleClientHttpRequestFactory getFactory() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(5000);
        simpleClientHttpRequestFactory.setReadTimeout(30000);
        return simpleClientHttpRequestFactory;
    }

    private List<HttpMessageConverter<?>> getConverts() {
        // 因为不作这个处理会产生 401 Unauthorized: [no body] 异常，解决方案： https://www.cnblogs.com/gqymy/p/13362579.html

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        // String转换器
        StringHttpMessageConverter stringConvert = new StringHttpMessageConverter();
        //添加响应数据格式，不匹配会报401
        List<MediaType> stringMediaTypes = Arrays.asList(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.APPLICATION_JSON);
        stringConvert.setSupportedMediaTypes(stringMediaTypes);
        messageConverters.add(stringConvert);
        return messageConverters;
    }

}
