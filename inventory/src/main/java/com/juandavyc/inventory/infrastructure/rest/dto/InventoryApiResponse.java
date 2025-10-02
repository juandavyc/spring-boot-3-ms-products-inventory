package com.juandavyc.inventory.infrastructure.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record InventoryApiResponse(
        InventoryData data,
        List<ProductData> included
){}
