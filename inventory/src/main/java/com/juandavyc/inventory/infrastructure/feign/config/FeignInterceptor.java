package com.juandavyc.inventory.infrastructure.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeignInterceptor implements RequestInterceptor {
    private static final String API_KEY_HEADER = "x-api-key";

    @Value("${security.api-key}")
    private String apiKeyValue;

    @Override
    public void apply(RequestTemplate template) {
        template.header(API_KEY_HEADER, apiKeyValue);
        template.header("x-external-call", "true");

    }
}
