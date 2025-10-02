package com.juandavyc.product.application.exceptions;

public class ProductAlreadyExistsException extends RuntimeException {
    private final String value;

    public ProductAlreadyExistsException(String field, String value) {
        super(String.format("Product with %s '%s' already exists", field, value));
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
