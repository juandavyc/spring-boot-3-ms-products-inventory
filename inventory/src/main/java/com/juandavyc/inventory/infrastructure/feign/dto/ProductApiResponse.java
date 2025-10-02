package com.juandavyc.inventory.infrastructure.feign.dto;



import com.juandavyc.inventory.infrastructure.rest.dto.ProductData;
import lombok.Data;

@Data
public class ProductApiResponse {
    private ProductData data;
}