package com.juandavyc.inventory.application.mapper;

import com.juandavyc.inventory.domain.model.Inventory;
import com.juandavyc.inventory.infrastructure.rest.dto.request.InventoryRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryRequestMapper {

    Inventory toDomain(InventoryRequestDto request);


}
