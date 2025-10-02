package com.juandavyc.inventory.infrastructure.feign.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class FeignConfig {
    @Value("${security.api-key}")
    private String apiKey;

    @Bean
    public RequestInterceptor apiKeyInterceptor() {
        return template -> template.header("X-API-KEY", apiKey);
    }
}
