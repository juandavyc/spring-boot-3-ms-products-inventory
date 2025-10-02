package com.juandavyc.inventory.domain.model;

import java.util.UUID;

public class Inventory {

    private UUID productId;
    private Integer quantity;


    public Inventory(UUID productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Inventory() {
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
