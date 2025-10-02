package com.juandavyc.product.application.usecases;


import com.juandavyc.product.domain.model.dto.ProductDto;
import com.juandavyc.product.domain.model.dto.ProductPageDto;
import com.juandavyc.product.infrastructure.rest.dto.request.ProductRequestDto;

import java.util.UUID;

public interface ProductService  {

    ProductDto create(ProductRequestDto request);

    ProductDto getById(UUID id);

    ProductPageDto getAll(int offset, int limit);

    ProductDto update(UUID id, ProductRequestDto request);

    ProductDto softDelete(UUID id);

}

