package com.juandavyc.inventory.application.mapper;

import com.juandavyc.inventory.domain.model.Inventory;
import com.juandavyc.inventory.domain.model.Product;
import com.juandavyc.inventory.domain.model.dto.ProductDto;
import com.juandavyc.inventory.infrastructure.rest.dto.request.InventoryRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface InventoryRequestMapper {

    @Mapping(source = "productId", target = "productId", qualifiedByName = "getProductId")
    Inventory toDomain(InventoryRequestDto request, UUID productId);

    @Named("getProductId")
    default UUID getProductId(UUID productId) {
        return productId;
    }
}
