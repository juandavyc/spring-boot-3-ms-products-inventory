package com.juandavyc.inventory.infrastructure.feign;


import com.juandavyc.inventory.infrastructure.feign.config.FeignConfig;
import com.juandavyc.inventory.infrastructure.feign.dto.ProductApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "product-service",
        configuration = FeignConfig.class
)
public interface ProductFeignClient {

    @GetMapping("/api/products/{productId}")
    ProductApiResponse getProductById(@PathVariable("productId") UUID productId);

}
