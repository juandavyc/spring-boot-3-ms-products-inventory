package com.juandavyc.product.application.exceptions;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    private final String value;

    public ProductNotFoundException(String field, String value) {
        super(String.format("Product not found for field %s, value: '%s'", field, value));
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
