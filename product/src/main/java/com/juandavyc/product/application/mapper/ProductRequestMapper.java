package com.juandavyc.product.application.mapper;

import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.domain.model.dto.request.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper {

//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "price", target = "price")
    Product toDomain(ProductRequest productRequest);
//
//    default UUID map(UUID value) {
//        return value;
//    }
}
