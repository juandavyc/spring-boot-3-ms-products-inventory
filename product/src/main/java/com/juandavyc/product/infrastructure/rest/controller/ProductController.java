package com.juandavyc.product.infrastructure.rest.controller;

import com.juandavyc.product.application.exceptions.InvalidRequestException;
import com.juandavyc.product.application.usecases.ProductService;

import com.juandavyc.product.infrastructure.rest.dto.ProductApiPageResponse;
import com.juandavyc.product.infrastructure.rest.dto.ProductApiRequest;
import com.juandavyc.product.infrastructure.rest.dto.ProductApiResponse;
import com.juandavyc.product.infrastructure.rest.dto.error.JsonApiErrorResponse;
import com.juandavyc.product.infrastructure.rest.dto.request.ProductRequestDto;
import com.juandavyc.product.infrastructure.rest.helper.JsonResponseBuilder;

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
@RequestMapping("/api/products")
@Validated
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Create a new product",
            description = "Creates a new product with the provided attributes"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = ProductApiResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Product with same name already exists",
                    content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
            )
    })

    @PostMapping
    public ResponseEntity<ProductApiResponse> create(
           @Validated(ProductRequestDto.Create.class)
           @RequestBody
           @Parameter(description = "Product creation data")
           ProductApiRequest productRequest
    ) {
        var product = productService.create(extractAttributes(productRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonResponseBuilder.buildProduct(product));
    }

    @Operation(
            summary = "Get product by ID",
            description = "Get a specific product based in a product id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product found successfully",
                    content = @Content(schema = @Schema(implementation = ProductApiResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
            )
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductApiResponse> getById(
            @Parameter(description = "Product ID", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id
    ) {
        var product = productService.getById(id);
        return ResponseEntity.ok(JsonResponseBuilder.buildProduct(product));
    }


    @Operation(
            summary = "Get all products",
            description = "Get a paginated list of all available products"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Products list",
                    content = @Content(schema = @Schema(implementation = ProductApiPageResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<ProductApiPageResponse> getAll(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "20")
            @RequestParam(defaultValue = "20") int size
    ) {

        var productsPage = productService.getAll(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponseBuilder.buildProductPage(productsPage));
    }

    @Operation(
            summary = "Update a product",
            description = "Updates an existing product with partial or complete data based in id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully",
                    content = @Content(schema = @Schema(implementation = ProductApiResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Product with same name already exists",
                    content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
            )
    })
    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductApiResponse> update(
//            @Parameter(description = "Product ID", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id,
            @Validated(ProductRequestDto.Update.class)
            @RequestBody
            @Parameter(description = "Product update data")
            ProductApiRequest productRequest

//
    ) {
        var product = productService.update(id, extractAttributes(productRequest));
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponseBuilder.buildProduct(product));
    }


    @Operation(
            summary = "Delete a product",
            description = "Soft deletes a product based in id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product deleted successfully",
                    content = @Content(schema = @Schema(implementation = ProductApiResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content(schema = @Schema(implementation = JsonApiErrorResponse.class))
            )
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ProductApiResponse> delete(
//            @Parameter(description = "Product ID", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id
    ) {
        var product = productService.softDelete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponseBuilder.buildProduct(product));
    }

    private ProductRequestDto extractAttributes(
            ProductApiRequest request
    ) {
        return Optional.ofNullable(request.getData())
                .map(ProductApiRequest.Data::getAttributes)
                .orElseThrow(() -> new InvalidRequestException("Attributes are required"));
    }

}
