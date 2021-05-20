package com.jomkie.common.remote;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author Jomkie
 * @since 2021-05-20 13:32:18
 * 远程请求工具配置
 */
@Configuration
public class RemoteConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(5000L))
                .setReadTimeout(Duration.ofMillis(30000L))
                .build();
        return restTemplate;
    }

}
