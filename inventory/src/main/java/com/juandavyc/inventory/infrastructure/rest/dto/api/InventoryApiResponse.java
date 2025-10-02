package com.juandavyc.inventory.infrastructure.rest.dto.api;

import com.juandavyc.inventory.infrastructure.rest.dto.InventoryData;
import com.juandavyc.inventory.infrastructure.rest.dto.ProductData;

import java.util.List;

public record InventoryApiResponse(
        InventoryData data,
        List<ProductData> included
){}
