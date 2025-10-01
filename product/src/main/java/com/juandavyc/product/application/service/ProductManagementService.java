package com.juandavyc.product.application.service;

import com.juandavyc.product.application.mapper.ProductDtoMapper;
import com.juandavyc.product.application.mapper.ProductRequestMapper;
import com.juandavyc.product.application.usecases.ProductService;
import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.domain.model.dto.ProductDto;
import com.juandavyc.product.domain.model.dto.ProductPageDto;
import com.juandavyc.product.domain.model.dto.request.ProductRequest;
import com.juandavyc.product.domain.port.ProductPersistencePort;
import com.juandavyc.product.infrastructure.adapter.mapper.ProductUpdateMapper;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductManagementService implements ProductService {

    private final ProductPersistencePort productPersistencePort;
    private final ProductRequestMapper productRequestMapper;
    private final ProductDtoMapper productDtoMapper;

    @Override
    public ProductDto create(ProductRequest request) {
        log.info("Creating product: {}", request.getName());
        var productToCreate = productRequestMapper.toDomain(request);

        var productCreated = productPersistencePort.create(productToCreate);

        return productDtoMapper.toDto(productCreated);

    }

    @Override
    public ProductDto getById(UUID id) {
        var product = productPersistencePort.getById(id);
        return productDtoMapper.toDto(product);
    }

    @Override
    public ProductPageDto getAll(int page, int size) {


        int offset = page * size;

        List<Product> products = productPersistencePort.findAll(offset, size);
        long totalElements = productPersistencePort.count();

        int totalPages = (int) Math.ceil((double) totalElements / size);

        List<ProductDto> productDtos = products.stream()
                .map(product -> productDtoMapper.toDto(product))
                .toList();

        return new ProductPageDto(productDtos, totalElements, totalPages, page, size);

    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public ProductDto update(UUID id, ProductRequest request) {

        Product productToUpdate = productRequestMapper.toDomain(request);
        Product updatedProduct = productPersistencePort.update(id, productToUpdate);

        return productDtoMapper.toDto(updatedProduct);
    }
}
