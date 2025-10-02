package com.juandavyc.inventory.infrastructure.adapter;

import com.juandavyc.inventory.domain.port.InventoryPersistencePort;
import com.juandavyc.inventory.infrastructure.adapter.mapper.InventoryEntityMapper;
import com.juandavyc.inventory.infrastructure.adapter.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventorySpringJpaAdapter implements InventoryPersistencePort {

    private final InventoryRepository inventoryRepository;
    private final InventoryEntityMapper inventoryEntityMapper;


}
