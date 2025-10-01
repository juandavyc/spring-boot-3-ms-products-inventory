package com.juandavyc.product.application.mapper;


import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.domain.model.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

//    @Mapping(source = "id", target = "id", dependsOn = "map")
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "price", target = "price")
    ProductDto toDto(Product domain);


//    default UUID map(UUID value) {
//        return value;
//    }
}
