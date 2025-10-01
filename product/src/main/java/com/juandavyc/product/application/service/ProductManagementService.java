package com.juandavyc.product.application.service;

import com.juandavyc.product.application.mapper.ProductDtoMapper;
import com.juandavyc.product.application.mapper.ProductRequestMapper;
import com.juandavyc.product.application.usecases.ProductService;
import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.domain.model.dto.ProductDto;
import com.juandavyc.product.domain.model.dto.request.ProductRequest;
import com.juandavyc.product.domain.port.ProductPersistencePort;
import com.juandavyc.product.infrastructure.adapter.mapper.ProductUpdateMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductManagementService implements ProductService {

    private final ProductPersistencePort productPersistencePort;
    private final ProductRequestMapper productRequestMapper;
    private final ProductDtoMapper productDtoMapper;

    @Override
    public ProductDto create(ProductRequest request) {

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
    public Page<ProductDto> getAll(Pageable pageable) {
        List<Product> products = productPersistencePort.getAll(
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
        List<ProductDto> dtos = products.stream()
                .map(productDtoMapper::toDto)
                .toList();
        return new PageImpl<>(dtos, pageable, dtos.size());
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
