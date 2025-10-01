package com.juandavyc.product.infrastructure.rest.controller;

import com.juandavyc.product.application.usecases.ProductService;

import com.juandavyc.product.domain.model.dto.ProductDto;
import com.juandavyc.product.domain.model.dto.request.ProductRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductDto create(
            @RequestBody ProductRequest productRequest
    ) {
        return productService.create(productRequest);
    }

    @GetMapping(path = "/{id}")
    public ProductDto getById(
            @PathVariable UUID id
    ) {
        return productService.getById(id);
    }

    @GetMapping
    public Page<ProductDto> getAll(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return productService.getAll(pageable);
    }

    @PutMapping(path = "/{id}")
    public ProductDto update(
            @PathVariable UUID id,
            @RequestBody ProductRequest productRequest
    ) {
        return productService.update(id, productRequest);
    }

}
