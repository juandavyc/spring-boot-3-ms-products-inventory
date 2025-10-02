package com.juandavyc.inventory.infrastructure.health;

import com.juandavyc.inventory.infrastructure.feign.ProductFeignClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceHealthIndicator implements HealthIndicator {

    private final ProductFeignClient productFeignClient;

    public ProductServiceHealthIndicator(ProductFeignClient productFeignClient) {
        this.productFeignClient = productFeignClient;
    }

    @Override
    public Health health() {
        try {
            productFeignClient.getHealth();
            return Health.up().build();
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}