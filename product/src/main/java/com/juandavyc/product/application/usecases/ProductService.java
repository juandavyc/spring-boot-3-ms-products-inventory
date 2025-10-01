package com.juandavyc.product.application.usecases;


import com.juandavyc.product.domain.model.dto.ProductDto;
import com.juandavyc.product.domain.model.dto.ProductPageDto;
import com.juandavyc.product.domain.model.dto.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService  {

    ProductDto create(ProductRequest request);
    ProductDto getById(UUID id);

    ProductPageDto getAll(int offset, int limit);

    void delete(UUID id);

    ProductDto update(UUID id, ProductRequest productRequest);


}

