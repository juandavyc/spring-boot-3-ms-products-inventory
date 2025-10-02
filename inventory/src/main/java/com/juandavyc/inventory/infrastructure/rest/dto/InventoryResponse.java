package com.juandavyc.inventory.infrastructure.rest.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class InventoryResponse {

    private InventoryData data;
    private List<ProductData> included;
}