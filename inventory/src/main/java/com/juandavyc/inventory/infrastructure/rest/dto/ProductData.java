package com.juandavyc.inventory.infrastructure.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProductData", description = "Product data object")
public class ProductData {

    @Schema(description = "Type of the resource", example = "products")
    private String type;

    @Schema(description = "Unique identifier of the product", example = "d963a80d-e161-46cd-92ba-c245d75a1978")
    private UUID id;

    @Schema(description = "Attributes of the product")
    private ProductAttributes attributes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "ProductAttributes", description = "Details about the product")
    public static class ProductAttributes {

        @Schema(description = "Product ID", example = "d963a80d-e161-46cd-92ba-c245d75a1978")
        private UUID id;

        @Schema(description = "Product name", example = "Laptop")
        private String name;

        @Schema(description = "Product price", example = "132.3")
        private Double price;

        @Schema(description = "Indicates if the product is deleted", example = "false")
        private Boolean deleted;
    }
}
