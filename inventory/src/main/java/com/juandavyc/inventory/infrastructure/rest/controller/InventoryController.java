package com.juandavyc.inventory.infrastructure.rest.controller;


import com.juandavyc.inventory.application.exceptions.InvalidRequestException;
import com.juandavyc.inventory.application.usecases.InventoryService;
import com.juandavyc.inventory.infrastructure.rest.dto.InventoryApiRequest;
import com.juandavyc.inventory.infrastructure.rest.dto.InventoryApiResponse;
import com.juandavyc.inventory.infrastructure.rest.dto.request.InventoryRequestDto;
import com.juandavyc.inventory.infrastructure.rest.helper.JsonResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventories")
@Validated
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping(path = "/{id}")
    public ResponseEntity<InventoryApiResponse> create(
            @PathVariable UUID id,
            @RequestBody InventoryApiRequest request
    ) {
        var inventory = inventoryService.create(id, extractAttributes(request));
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponseBuilder.buildInventory(inventory));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<InventoryApiResponse> getById(
            @PathVariable UUID id
    ) {
        var product = inventoryService.getById(id);
        return ResponseEntity.ok(JsonResponseBuilder.buildInventory(product));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<InventoryApiResponse> update(
            @PathVariable UUID id,
            @RequestBody InventoryApiRequest request
    ) {
        var inventory = inventoryService.update(id, extractAttributes(request));
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponseBuilder.buildInventory(inventory));
    }

    private InventoryRequestDto extractAttributes(
            InventoryApiRequest request
    ) {
        return Optional.ofNullable(request.getData())
                .map(InventoryApiRequest.Data::getAttributes)
                .orElseThrow(() -> new InvalidRequestException("Attributes are required"));
    }
}
