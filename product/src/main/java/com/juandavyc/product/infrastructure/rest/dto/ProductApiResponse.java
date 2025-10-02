package com.juandavyc.product.infrastructure.rest.dto;

public record ProductApiResponse(
        ProductApiData data,
        Links links
) {
    public record Links(String self) {}
}
