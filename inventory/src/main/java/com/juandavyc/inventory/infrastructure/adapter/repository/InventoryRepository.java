package com.juandavyc.inventory.infrastructure.adapter.repository;

import com.juandavyc.inventory.infrastructure.adapter.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, UUID> {

    Optional<InventoryEntity> findByProductId(UUID id);
}
