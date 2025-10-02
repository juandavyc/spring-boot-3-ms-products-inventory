package com.juandavyc.inventory.infrastructure.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JSON API response for single product")
public record InventoryApiResponse(
        @Schema(description = "Product data")
        InventoryApiData data,
        @Schema(description = "HATEOAS links")
        Links links
) {
    @Schema(description = "HATEOAS links structure")
    public record Links(
            @Schema(description = "Self link", example = "/api/products/123e4567-e89b-12d3-a456-426614174000")
            String self
    ) {
    }
}