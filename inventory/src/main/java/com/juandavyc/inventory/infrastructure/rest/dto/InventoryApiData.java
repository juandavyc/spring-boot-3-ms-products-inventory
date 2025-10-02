package com.juandavyc.product.infrastructure.rest.dto;

import com.juandavyc.product.domain.model.dto.ProductDto;

public record ProductApiData(
        String type,
        String id,
        ProductDto attributes
) {

}