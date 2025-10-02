package com.juandavyc.inventory.application.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private final String value;

    public ResourceNotFoundException(String resource, String field, String value) {
        super(String.format("%s not found for field %s, value: '%s'", resource, field, value));
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
