package com.juandavyc.inventory.infrastructure.rest.dto;

import com.juandavyc.inventory.infrastructure.feign.dto.ProductApiResponse;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ProductData {
    private String type;
    private UUID id;
    private ProductAttributes attributes;

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @ToString
    public static class ProductAttributes {
        private UUID id;
        private String name;
        private Double price;
        private Boolean deleted;
    }
}
