package com.juandavyc.product.domain.model.dto.request;

import java.math.BigDecimal;

public class ProductRequest {


    private String name;
    private BigDecimal price;


    public ProductRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public ProductRequest() {
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}


