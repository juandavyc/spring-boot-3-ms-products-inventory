package com.juandavyc.product.infrastructure.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JSON API response for single product")
public record ProductApiResponse(
        @Schema(description = "Product data")
        ProductApiData data,
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