package com.juandavyc.inventory.application.service;

import com.juandavyc.inventory.application.exceptions.InventoryNotFoundException;
import com.juandavyc.inventory.application.mapper.InventoryDtoMapper;
import com.juandavyc.inventory.application.mapper.InventoryRequestMapper;
import com.juandavyc.inventory.application.usecases.InventoryService;
import com.juandavyc.inventory.domain.model.Inventory;
import com.juandavyc.inventory.domain.model.dto.InventoryDto;
import com.juandavyc.inventory.domain.port.InventoryPersistencePort;
import com.juandavyc.inventory.domain.port.ProductPersistencePort;
import com.juandavyc.inventory.infrastructure.rest.dto.request.InventoryRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryManagementService implements InventoryService {

    private final InventoryPersistencePort inventoryPersistencePort;
    private final InventoryRequestMapper inventoryRequestMapper;
    private final InventoryDtoMapper inventoryDtoMapper;

    //

    private final ProductPersistencePort productPersistencePort;


    @Override
    public InventoryDto create(UUID productId, InventoryRequestDto request) {

        var product = productPersistencePort.getProductById(productId);

        System.out.println(product.getDeleted());
        var toCreate = inventoryRequestMapper.toDomain(request);

        toCreate.setProductId(productId);

        var productCreated = inventoryPersistencePort.save(toCreate);

        return inventoryDtoMapper.toDto(productCreated, product);
    }

    @Override
    public InventoryDto getById(UUID id) {
        var inventory = inventoryPersistencePort.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("ID", id.toString()));
        var product = productPersistencePort.getProductById(id);

        return inventoryDtoMapper.toDto(inventory, product);
    }

    @Override
    public InventoryDto update(UUID id, InventoryRequestDto inventoryRequestDto) {

        log.info("updating - id: {}", id);

        var product = productPersistencePort.getProductById(id);

        var existingProduct = inventoryPersistencePort.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("ID", id.toString()));

        var updatedProduct = new Inventory(
                existingProduct.getProductId(),
                inventoryRequestDto.getQuantity()
        );
        var saved = inventoryPersistencePort.save(updatedProduct);

        log.info("updated successfully - id: {}", saved.getProductId());

        return inventoryDtoMapper.toDto(saved, product);
    }

}
