package com.juandavyc.product.infrastructure.adapter;

import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.domain.port.ProductPersistencePort;
import com.juandavyc.product.infrastructure.adapter.entity.ProductEntity;
import com.juandavyc.product.infrastructure.adapter.mapper.ProductEntityMapper;
import com.juandavyc.product.infrastructure.adapter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSpringJpaAdapter implements ProductPersistencePort {

    private final ProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public Product save(Product request) {
        log.debug("Saving product - id: {}", request.getId());

        var entity = productEntityMapper.toEntity(request);
        var saved = productRepository.save(entity);

        log.debug("Product saved - id: {}", saved.getId());
        return productEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        log.debug("finding product by id: {}", id);
        return productRepository.findByIdAndDeletedIsFalse(id)
                .map(productEntityMapper::toDomain);
    }

    @Override
    public List<Product> findAll(int offset, int limit) {

        log.debug("Finding all products - offset: {}, limit: {}", offset, limit);

        Pageable pageable = PageRequest.of(offset / limit, limit);
        var products = productRepository.findByDeletedIsFalse(pageable)
                .stream()
                .map(productEntityMapper::toDomain)
                .toList();
        log.debug("found {} products", products.size());
        return products;
    }

    @Override
    public Long count() {

        var count = productRepository.countByDeletedIsFalse();
        log.debug("Total active products: {}", count);

        return count;
    }

    @Override
    public boolean existsByNameAndDeletedIsFalse(String name) {

        log.debug("checking if product exists with name: {}", name);

        return productRepository.existsByNameAndDeletedIsFalse(name);
    }

}
