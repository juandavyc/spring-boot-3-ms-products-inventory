package com.juandavyc.inventory.infrastructure.rest.dto;


import com.juandavyc.inventory.domain.model.dto.InventoryDto;

public record InventoryApiData(
        String type,
        String id,
        InventoryDto attributes
) {

}