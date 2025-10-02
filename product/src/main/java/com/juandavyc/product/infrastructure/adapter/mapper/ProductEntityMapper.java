package com.juandavyc.product.infrastructure.adapter.mapper;

import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.infrastructure.adapter.entity.ProductEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

    ProductEntity toEntity(Product product);

    @InheritInverseConfiguration
    Product toDomain(ProductEntity productEntity);

}
