package com.juandavyc.product.infrastructure.adapter;

import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.domain.port.ProductPersistencePort;
import com.juandavyc.product.infrastructure.adapter.entity.ProductEntity;
import com.juandavyc.product.infrastructure.adapter.mapper.ProductEntityMapper;
import com.juandavyc.product.infrastructure.adapter.mapper.ProductUpdateMapper;
import com.juandavyc.product.infrastructure.adapter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductSpringJpaAdapter implements ProductPersistencePort {

    private final ProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;
    private final ProductUpdateMapper productUpdateMapper;

    @Override
    public Product create(Product request) {

        ProductEntity productToSave = productEntityMapper.toEntity(request);
//        productToSave.setDeleted(false);
        var productSaved = productRepository.save(productToSave);
        return productEntityMapper.toDomain(productSaved);

    }

    @Override
    public Product getById(UUID id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productEntityMapper.toDomain(product);
    }

    @Override
    public List<Product> findAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return productRepository.findAll(pageable)
                .stream()
                .map(product -> productEntityMapper.toDomain(product))
                .toList();
    }

    @Override
    public Product update(UUID id, Product request) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productUpdateMapper.updateEntity(request, product);
        var productUpdated = productRepository.save(product);
        return productEntityMapper.toDomain(productUpdated);

    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public Long count() {
        return productRepository.count();
    }

}
