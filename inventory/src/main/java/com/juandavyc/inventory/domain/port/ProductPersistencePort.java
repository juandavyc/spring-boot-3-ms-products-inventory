package com.juandavyc.inventory.domain.port;

import com.juandavyc.inventory.domain.model.Product;

import java.util.UUID;

public interface ProductPersistencePort {

    Product getProductById(UUID productId);
    boolean existsById(UUID productId);


}
