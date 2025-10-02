package com.juandavyc.inventory.domain.model.dto;

import java.util.UUID;

public record ProductDto(
        UUID id,
        String name,
        Double price,
        Boolean deleted
) {
}
