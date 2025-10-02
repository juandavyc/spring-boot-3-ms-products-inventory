package com.juandavyc.inventory.application.exceptions;

import java.util.UUID;

public class InventoryNotFoundException extends RuntimeException {
    private final String value;

    public InventoryNotFoundException(String field, String value) {
        super(String.format("Inventory not found for field %s, value: '%s'", field, value));
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
