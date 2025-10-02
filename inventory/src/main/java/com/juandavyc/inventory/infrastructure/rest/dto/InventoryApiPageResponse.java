package com.juandavyc.inventory.infrastructure.rest.dto;


import java.util.List;

public record InventoryApiPageResponse(
        List<InventoryApiData> data,
        PaginationLinks links,
        PaginationMeta meta
) {

    public record PaginationLinks(String self, String first, String last, String prev, String next) {}
    public record PaginationMeta(long totalElements, int totalPages, int pageSize, int currentPage) {}
}