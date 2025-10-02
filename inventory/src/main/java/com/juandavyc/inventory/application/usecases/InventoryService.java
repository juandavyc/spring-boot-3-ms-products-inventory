package com.juandavyc.inventory.application.usecases;

import com.juandavyc.inventory.domain.model.dto.InventoryDto;
import com.juandavyc.inventory.infrastructure.rest.dto.request.InventoryRequestDto;

import java.util.UUID;

public interface InventoryService {

    InventoryDto create(UUID productId, InventoryRequestDto request);

    InventoryDto getById(UUID id);

    InventoryDto update(UUID id, InventoryRequestDto inventoryRequestDto);

}
