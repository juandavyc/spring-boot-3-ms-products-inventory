package com.juandavyc.product.infrastructure.rest.controller;

import com.juandavyc.product.application.usecases.ProductService;

import com.juandavyc.product.domain.model.dto.request.ProductRequest;
import com.juandavyc.product.infrastructure.rest.dto.ProductApiPageResponse;
import com.juandavyc.product.infrastructure.rest.dto.request.ProductApiRequest;
import com.juandavyc.product.infrastructure.rest.dto.ProductApiResponse;
import com.juandavyc.product.infrastructure.rest.helper.JsonResponseBuilder;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductApiResponse> create(
            @RequestBody ProductApiRequest<ProductRequest> productRequest
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
            @RequestParam(defaultValue = "20") int size) {

        var productsPage = productService.getAll(page, size);
        var response = JsonResponseBuilder.buildProductPage(productsPage);
        return ResponseEntity.ok(response);
    }

//    @PutMapping(path = "/{id}")
//    public ResponseEntity<ProductApiResponse> update(
//            @PathVariable UUID id,
//            @RequestBody ProductRequest productRequest
//    ) {
//        return productService.update(id, productRequest);
//    }

    private ProductRequest extractAttributes(ProductApiRequest<ProductRequest> request) {
        return request.getData().getAttributes();
    }

}
