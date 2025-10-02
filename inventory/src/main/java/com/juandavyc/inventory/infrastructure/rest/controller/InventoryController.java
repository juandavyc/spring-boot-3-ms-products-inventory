package com.juandavyc.inventory.infrastructure.rest.controller;


import com.juandavyc.inventory.application.exceptions.InvalidRequestException;
import com.juandavyc.inventory.application.usecases.InventoryService;
import com.juandavyc.inventory.infrastructure.rest.dto.api.InventoryApiRequest;
import com.juandavyc.inventory.infrastructure.rest.dto.api.InventoryApiResponse;
import com.juandavyc.inventory.infrastructure.rest.dto.error.JsonApiErrorResponse;
import com.juandavyc.inventory.infrastructure.rest.dto.request.InventoryRequestDto;
import com.juandavyc.inventory.infrastructure.rest.helper.JsonResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
    @Operation(summary = "Create inventory", description = "Create a new inventory record for a product")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventory created successfully",
                    content = @Content(schema = @Schema(implementation = InventoryApiResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
            )
    })
    public ResponseEntity<InventoryApiResponse> create(
            @Parameter(description = "Product ID")
            @PathVariable UUID id,
            @Parameter(description = "Inventory creation data")
            @Valid @RequestBody InventoryApiRequest request
    ) {
        var inventory = inventoryService.create(id, extractAttributes(request));
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponseBuilder.buildInventory(inventory));
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get inventory by ID", description = "Retrieve inventory information by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventory found",
                    content = @Content(schema = @Schema(implementation = InventoryApiResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Inventory not found",
                    content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
            )
    })
    public ResponseEntity<InventoryApiResponse> getById(
            @PathVariable UUID id
    ) {
        var product = inventoryService.getById(id);
        return ResponseEntity.ok(JsonResponseBuilder.buildInventory(product));
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Update inventory", description = "Update inventory information by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventory updated successfully",
                    content = @Content(schema = @Schema(implementation = InventoryApiResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = InventoryApiResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Inventory not found",
                    content = @Content(schema = @Schema(implementation = InventoryApiResponse.class))
            )
    })
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
