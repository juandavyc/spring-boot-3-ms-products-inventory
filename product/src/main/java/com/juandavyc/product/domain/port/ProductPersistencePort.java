package com.juandavyc.product.domain.port;

import com.juandavyc.product.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductPersistencePort {

    Product save(Product request);
    Optional<Product> findById(UUID id);
    List<Product> findAll(int offset, int limit);
    Long count();

    boolean existsByNameAndDeletedIsFalse(String name);
}
