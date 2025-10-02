package com.juandavyc.inventory.infrastructure.rest.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class InventoryData {

    private String type;
    private UUID id;
    private InventoryAttributes attributes;
    private InventoryRelationships relationships;

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @ToString
    public static class InventoryAttributes {
        private Integer quantity;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class InventoryRelationships {
        private ProductRelationship product;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ProductRelationship {
        private RelationshipData data;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class RelationshipData {
        private String type; // "products"
        private UUID id;
    }
}
