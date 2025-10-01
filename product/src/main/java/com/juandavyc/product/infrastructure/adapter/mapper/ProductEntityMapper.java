package com.juandavyc.product.infrastructure.adapter.mapper;


import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.infrastructure.adapter.entity.ProductEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "price", target = "price")
    ProductEntity toEntity(Product product);

    @InheritInverseConfiguration
    Product toDomain(ProductEntity productEntity);

//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "price", target = "price")


//    default UUID map(UUID value) {
//        return value;
//    }
}
