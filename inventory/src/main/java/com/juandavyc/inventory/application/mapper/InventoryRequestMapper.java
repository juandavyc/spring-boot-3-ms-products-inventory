package com.juandavyc.inventory.application.mapper;

import com.juandavyc.inventory.domain.model.Inventory;
import com.juandavyc.inventory.domain.model.dto.request.InventoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryRequestMapper {

    @Mapping(target = "deleted", constant = "false")
    Inventory toDomain(InventoryRequest request);
}
