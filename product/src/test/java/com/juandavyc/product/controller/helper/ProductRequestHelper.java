package com.juandavyc.product.controller.helper;

import com.juandavyc.product.infrastructure.rest.dto.ProductApiRequest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class ProductRequestHelper {

    public static <T> ResponseEntity<T> createProduct(
            TestRestTemplate testRestTemplate,
            ProductApiRequest request,
            ParameterizedTypeReference<T> type
    ) {
        return testRestTemplate.exchange(
                "/api/products",
                HttpMethod.POST,
                new HttpEntity<>(request),
                type
        );
    }

    public static <T> ResponseEntity<T> updateProduct(
            TestRestTemplate restTemplate,
            String url,
            ProductApiRequest request,
            Class<T> responseType
    ) {
        return restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(request),
                responseType
        );
    }

    public static <T> ResponseEntity<T> getProductById(
            TestRestTemplate restTemplate,
            String url,
            Class<T> responseType
    ) {
        return restTemplate.exchange(
                "/api/products" + url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                responseType
        );
    }
}
