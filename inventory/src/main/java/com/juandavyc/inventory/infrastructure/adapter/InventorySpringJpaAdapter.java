package com.juandavyc.inventory.infrastructure.adapter;

import com.juandavyc.inventory.domain.model.Inventory;
import com.juandavyc.inventory.domain.port.InventoryPersistencePort;
import com.juandavyc.inventory.infrastructure.adapter.mapper.InventoryEntityMapper;
import com.juandavyc.inventory.infrastructure.adapter.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventorySpringJpaAdapter implements InventoryPersistencePort {

    private final InventoryRepository inventoryRepository;
    private final InventoryEntityMapper inventoryEntityMapper;

    @Override
    public Inventory save(Inventory request) {
        log.debug("Saving - id: {}", request.getProductId());

        var entity = inventoryEntityMapper.toEntity(request);
        var saved = inventoryRepository.save(entity);

        log.debug("Saved - id: {}", saved.getProductId());
        return inventoryEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Inventory> findById(UUID id) {
        log.debug("finding by id: {}", id);
        return inventoryRepository.findByProductId(id)
                .map(inventoryEntityMapper::toDomain);
    }
}
