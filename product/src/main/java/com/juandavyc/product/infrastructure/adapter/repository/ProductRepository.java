package com.juandavyc.product.infrastructure.adapter.repository;

import com.juandavyc.product.infrastructure.adapter.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {


    Page<ProductEntity> findAll(Pageable pageable);
}
