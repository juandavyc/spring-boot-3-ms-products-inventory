package com.juandavyc.product.infrastructure.adapter.repository;

import com.juandavyc.product.infrastructure.adapter.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    Page<ProductEntity> findByDeletedIsFalse(Pageable pageable);
    Optional<ProductEntity> findByIdAndDeletedIsFalse(UUID id);
    long countByDeletedIsFalse();
    boolean existsByNameAndDeletedIsFalse(String name);


}
