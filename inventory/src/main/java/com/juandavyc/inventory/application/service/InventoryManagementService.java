package com.juandavyc.inventory.application.service;

import com.juandavyc.inventory.application.mapper.InventoryDtoMapper;
import com.juandavyc.inventory.application.mapper.InventoryRequestMapper;
import com.juandavyc.inventory.application.usecases.InventoryService;
import com.juandavyc.inventory.domain.port.InventoryPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryManagementService implements InventoryService {

    private final InventoryPersistencePort inventoryPersistencePort;
    private final InventoryRequestMapper inventoryRequestMapper;
    private final InventoryDtoMapper inventoryDtoMapper;


}
