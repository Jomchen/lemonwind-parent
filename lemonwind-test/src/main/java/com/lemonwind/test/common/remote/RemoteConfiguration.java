package com.lemonwind.test.common.remote;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ObjectToStringHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lemonwind
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
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        simpleClientHttpRequestFactory.setReadTimeout(30000);
        return simpleClientHttpRequestFactory;
    }


    private List<HttpMessageConverter<?>> getConverts() {
        // 因为不作这个处理会产生 401 Unauthorized: [no body] 异常，解决方案： https://www.cnblogs.com/gqymy/p/13362579.html
        // https://www.bianchengquan.com/article/218114.html
        // https://blog.csdn.net/qq_17798343/article/details/108052312

        List<MediaType> mediaTypes = Arrays.asList(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.APPLICATION_JSON);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();


        // fastjson 转换器
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);

        FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
        fastConvert.setFastJsonConfig(fastJsonConfig);
        fastConvert.setSupportedMediaTypes(mediaTypes);


        // String转换器
        messageConverters.add(fastConvert);
        return messageConverters;
    }



}
