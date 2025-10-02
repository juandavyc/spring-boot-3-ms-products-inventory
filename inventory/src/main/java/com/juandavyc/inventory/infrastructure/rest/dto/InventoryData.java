package com.juandavyc.inventory.infrastructure.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "InventoryData", description = "Inventory data object")
public class InventoryData {

    @Schema(description = "Type of the resource", example = "inventory")
    private String type;

    @Schema(description = "Unique identifier of the inventory record", example = "d963a80d-e161-46cd-92ba-c245d75a1978")
    private UUID id;

    @Schema(description = "Inventory attributes")
    private InventoryAttributes attributes;

    @Schema(description = "Inventory relationships")
    private InventoryRelationships relationships;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "InventoryAttributes", description = "Attributes of the inventory")
    public static class InventoryAttributes {
        @Schema(description = "Quantity in inventory", example = "10")
        private Integer quantity;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "InventoryRelationships", description = "Relationships of the inventory record")
    public static class InventoryRelationships {
        private ProductRelationship product;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "ProductRelationship", description = "Relationship to the product")
    public static class ProductRelationship {
        private RelationshipData data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "RelationshipData", description = "Data for related resource")
    public static class RelationshipData {
        @Schema(description = "Type of related resource", example = "products")
        private String type;

        @Schema(description = "ID of related resource", example = "d963a80d-e161-46cd-92ba-c245d75a1978")
        private UUID id;
    }
}