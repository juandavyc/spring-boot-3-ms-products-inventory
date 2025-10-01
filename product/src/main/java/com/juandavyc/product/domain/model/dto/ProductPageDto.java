package com.juandavyc.product.domain.model.dto;

import java.util.List;

public record ProductPageDto(
        List<ProductDto> products,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize
) {}