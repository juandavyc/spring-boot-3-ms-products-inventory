package com.juandavyc.inventory.domain.model.dto;


import java.util.UUID;

public record InventoryDto(
        UUID productId,
        Integer quantity,
        Boolean deleted
) {
}