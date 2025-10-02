package com.juandavyc.inventory.application.mapper;

import com.juandavyc.inventory.domain.model.Inventory;
import com.juandavyc.inventory.domain.model.Product;
import com.juandavyc.inventory.domain.model.dto.InventoryDto;
import com.juandavyc.inventory.domain.model.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryDtoMapper {

    @Mapping(source = "product", target = "included", qualifiedByName = "productToList")
    InventoryDto toDto(Inventory inventory, Product product);

    @Named("productToList")
    default List<ProductDto> productToList(Product product) {
        if (product == null) {
            return List.of();
        }
        return List.of(toProductDto(product));
    }

    ProductDto toProductDto(Product product);

}
