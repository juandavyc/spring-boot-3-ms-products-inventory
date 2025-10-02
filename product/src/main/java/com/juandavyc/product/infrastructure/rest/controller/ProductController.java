package com.juandavyc.product.infrastructure.rest.controller;

import com.juandavyc.product.application.exceptions.InvalidRequestException;
import com.juandavyc.product.application.usecases.ProductService;

import com.juandavyc.product.infrastructure.rest.dto.ProductApiPageResponse;
import com.juandavyc.product.infrastructure.rest.dto.ProductApiRequest;
import com.juandavyc.product.infrastructure.rest.dto.ProductApiResponse;
import com.juandavyc.product.infrastructure.rest.dto.request.ProductRequestDto;
import com.juandavyc.product.infrastructure.rest.helper.JsonResponseBuilder;

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
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductApiResponse> create(
           @Validated(ProductRequestDto.Create.class) @RequestBody ProductApiRequest productRequest
    ) {
        var product = productService.create(extractAttributes(productRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonResponseBuilder.buildProduct(product));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductApiResponse> getById(
            @PathVariable UUID id
    ) {
        var product = productService.getById(id);
        return ResponseEntity.ok(JsonResponseBuilder.buildProduct(product));
    }

    @GetMapping
    public ResponseEntity<ProductApiPageResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        var productsPage = productService.getAll(page, size);
        var response = JsonResponseBuilder.buildProductPage(productsPage);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductApiResponse> update(
            @PathVariable UUID id,
            @Validated(ProductRequestDto.Update.class) @RequestBody ProductApiRequest productRequest
    ) {
        var product = productService.update(id, extractAttributes(productRequest));
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponseBuilder.buildProduct(product));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ProductApiResponse> update(
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
