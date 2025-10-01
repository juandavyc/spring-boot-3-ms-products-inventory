package com.juandavyc.product.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private BigDecimal price;
//    private boolean deleted;

//    @PrePersist
//    private void prePersist() {
//        deleted = false;
//    }

}
