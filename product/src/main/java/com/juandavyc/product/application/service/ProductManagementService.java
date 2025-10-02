package com.juandavyc.product.application.service;

import com.juandavyc.product.application.exceptions.ProductAlreadyExistsException;
import com.juandavyc.product.application.mapper.ProductDtoMapper;
import com.juandavyc.product.application.mapper.ProductRequestMapper;
import com.juandavyc.product.application.usecases.ProductService;
import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.domain.model.dto.ProductDto;
import com.juandavyc.product.domain.model.dto.ProductPageDto;
import com.juandavyc.product.domain.port.ProductPersistencePort;
import com.juandavyc.product.application.exceptions.ProductNotFoundException;
import com.juandavyc.product.infrastructure.rest.dto.request.ProductRequestDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public ProductDto create(ProductRequestDto request) {
        log.info("Creating product: {}", request.getName());
        validateProductNameAvailable(request.getName());

        var productToCreate = productRequestMapper.toDomain(request);
        productToCreate.setDeleted(false);
        var productCreated = productPersistencePort.save(productToCreate);
        return productDtoMapper.toDto(productCreated);
    }

    @Override
    public ProductDto getById(UUID id) {
        var product = productPersistencePort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("ID", id.toString()));
        return productDtoMapper.toDto(product);
    }

    @Override
    public ProductPageDto getAll(int page, int size) {
        int offset = page * size;
        var products = productPersistencePort.findAll(offset, size);
        long totalElements = productPersistencePort.count();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        var productDtos = products.stream()
                .map(productDtoMapper::toDto)
                .toList();

        return new ProductPageDto(productDtos, totalElements, totalPages, page, size);

    }

    @Override
    public ProductDto update(UUID id, ProductRequestDto request) {

        var existingProduct = productPersistencePort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("ID", id.toString()));

        if (request.getName() != null && !request.getName().equals(existingProduct.getName())) {
            validateProductNameAvailable(request.getName());
        }

        String newName = (request.getName() != null) ? request.getName() : existingProduct.getName();
        BigDecimal newPrice = (request.getPrice() != null) ? request.getPrice() : existingProduct.getPrice();

        var updatedProduct = new Product(
                existingProduct.getId(),
                newName,
                newPrice,
                existingProduct.getDeleted()
        );

        var savedProduct = productPersistencePort.save(updatedProduct);
        return productDtoMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto softDelete(UUID id) {
        var product = productPersistencePort.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("ID", id.toString()));

        product.setDeleted(true);

        var deletedProduct = productPersistencePort.save(product);
        return productDtoMapper.toDto(deletedProduct);
    }


    private void validateProductNameAvailable(String productName) {
        boolean nameExists = productPersistencePort.existsByNameAndDeletedIsFalse(productName);
        if (nameExists) {
            throw new ProductAlreadyExistsException("Name", productName);
        }
    }
}
