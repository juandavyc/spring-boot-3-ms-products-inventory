package com.juandavyc.inventory.domain.model;

import java.util.UUID;

public class Product {

    private UUID id;
    private String name;
    private Double price;
    private Boolean deleted;

    public Product(UUID id, String name, Double price, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.deleted = deleted;
    }

    public Product() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
