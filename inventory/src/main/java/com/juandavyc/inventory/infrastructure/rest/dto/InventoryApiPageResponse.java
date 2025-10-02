package com.juandavyc.product.infrastructure.rest.dto;


import java.util.List;

public record ProductApiPageResponse(
        List<ProductApiData> data,
        PaginationLinks links,
        PaginationMeta meta
) {

    public record PaginationLinks(String self, String first, String last, String prev, String next) {}
    public record PaginationMeta(long totalElements, int totalPages, int pageSize, int currentPage) {}
}