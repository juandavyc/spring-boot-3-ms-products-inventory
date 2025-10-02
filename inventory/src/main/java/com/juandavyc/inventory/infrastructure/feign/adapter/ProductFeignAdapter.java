package com.juandavyc.inventory.infrastructure.feign.adapter;

import com.juandavyc.inventory.domain.model.Product;
import com.juandavyc.inventory.domain.port.ProductPersistencePort;
import com.juandavyc.inventory.infrastructure.feign.ProductFeignClient;
import com.juandavyc.inventory.infrastructure.feign.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor

public class ProductFeignAdapter implements ProductPersistencePort {

    private final ProductFeignClient productFeignClient;
    private final ProductMapper productMapper;

    @Override
    public Product getProductById(UUID productId) {
        var productResponse = productFeignClient.getProductById(productId);
        return productMapper.toDomain(productResponse);
    }

    @Override
    public boolean existsById(UUID productId) {
        productFeignClient.getProductById(productId);
        return true;
    }
}
