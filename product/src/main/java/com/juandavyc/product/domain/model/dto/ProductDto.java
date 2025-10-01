package com.juandavyc.product.domain.model.dto;


import java.math.BigDecimal;
import java.util.UUID;

public record ProductDto(
        UUID id,
        String name,
        BigDecimal price
) {}