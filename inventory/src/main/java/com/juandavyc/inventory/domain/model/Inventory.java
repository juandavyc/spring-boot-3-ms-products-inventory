package com.juandavyc.inventory.domain.model;

import java.util.UUID;

public class Inventory {

    private UUID productId;
    private Integer quantity;
    private Boolean deleted;

    public Inventory(UUID productId, Integer quantity, Boolean deleted) {
        this.productId = productId;
        this.quantity = quantity;
        this.deleted = deleted;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
