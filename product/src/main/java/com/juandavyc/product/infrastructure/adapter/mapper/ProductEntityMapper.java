package com.juandavyc.product.infrastructure.adapter.mapper;

import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.infrastructure.adapter.entity.ProductEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "deleted", constant = "false")
    ProductEntity toEntity(Product product);

    @InheritInverseConfiguration
    Product toDomain(ProductEntity productEntity);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "createdBy", ignore = true)
//    void updateEntity(Product source, @MappingTarget ProductEntity target);
}
