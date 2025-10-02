package com.juandavyc.inventory.domain.model.dto;

import java.util.List;

public record InventoryPageDto(
        List<InventoryDto> inventories,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize
) {}