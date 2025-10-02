package com.juandavyc.inventory.infrastructure.feign.mapper;


import com.juandavyc.inventory.domain.model.Product;
import com.juandavyc.inventory.infrastructure.feign.dto.ProductApiResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "data.id", target = "id")
    @Mapping(source = "data.attributes.name", target = "name")
    @Mapping(source = "data.attributes.price", target = "price")
    @Mapping(source = "data.attributes.deleted", target = "deleted")
    Product toDomain(ProductApiResponse response);

}