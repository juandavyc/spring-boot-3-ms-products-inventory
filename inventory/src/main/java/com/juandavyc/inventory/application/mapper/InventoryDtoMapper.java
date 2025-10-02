package com.juandavyc.inventory.application.mapper;

import com.juandavyc.inventory.domain.model.Inventory;
import com.juandavyc.inventory.domain.model.dto.InventoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryDtoMapper {

    InventoryDto toDto(Inventory domain);


}
