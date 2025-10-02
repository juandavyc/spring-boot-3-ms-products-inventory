package com.juandavyc.inventory.application.exceptions;

public class InventoryAlreadyExistsException extends RuntimeException {
    private final String value;

    public InventoryAlreadyExistsException(String field, String value) {
        super(String.format("Inventory with %s '%s' already exists", field, value));
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
