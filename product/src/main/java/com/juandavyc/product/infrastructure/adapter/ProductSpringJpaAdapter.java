package com.juandavyc.product.infrastructure.adapter;

import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.domain.port.ProductPersistencePort;
import com.juandavyc.product.infrastructure.adapter.entity.ProductEntity;
import com.juandavyc.product.infrastructure.adapter.mapper.ProductEntityMapper;
import com.juandavyc.product.infrastructure.adapter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductSpringJpaAdapter implements ProductPersistencePort {

    private final ProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public Product save(Product request) {
        var entity = productEntityMapper.toEntity(request);
        var saved = productRepository.save(entity);
        return productEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepository.findByIdAndDeletedIsFalse(id)
                .map(productEntityMapper::toDomain);
    }

    @Override
    public List<Product> findAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return productRepository.findByDeletedIsFalse(pageable)
                .stream()
                .map(productEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Long count() {
        return productRepository.countByDeletedIsFalse();
    }

    @Override
    public boolean existsByNameAndDeletedIsFalse(String name) {
        return productRepository.existsByNameAndDeletedIsFalse(name);
    }

}
