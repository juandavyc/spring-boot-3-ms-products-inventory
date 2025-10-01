package com.juandavyc.product.domain.port;

import com.juandavyc.product.domain.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductPersistencePort {

    Product create(Product request);
    Product getById(UUID id);
    List<Product> findAll(int offset, int limit);
    Product update(UUID id, Product request);
    void delete(UUID id);

    Long count();
}
