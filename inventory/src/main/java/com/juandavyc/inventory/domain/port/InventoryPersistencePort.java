package com.juandavyc.inventory.domain.port;

import com.juandavyc.inventory.domain.model.Inventory;

import java.util.Optional;
import java.util.UUID;

public interface InventoryPersistencePort {

    Inventory save(Inventory inventory);

    Optional<Inventory> findById(UUID id);
}
