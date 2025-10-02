package com.juandavyc.inventory.infrastructure.adapter.mapper;

import com.juandavyc.inventory.domain.model.Inventory;
import com.juandavyc.inventory.infrastructure.adapter.entity.InventoryEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface InventoryEntityMapper {

    InventoryEntity toEntity(Inventory product);

    @InheritInverseConfiguration
    Inventory toDomain(InventoryEntity entity);

}
