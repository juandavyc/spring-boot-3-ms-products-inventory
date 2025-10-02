package com.juandavyc.inventory.application.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
    private final String value;

    public ResourceAlreadyExistsException(String resource, String field, String value) {
        super(String.format("%s with %s '%s' already exists", resource, field, value));
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
