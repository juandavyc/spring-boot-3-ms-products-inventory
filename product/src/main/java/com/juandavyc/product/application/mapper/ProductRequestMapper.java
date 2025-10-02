package com.juandavyc.product.application.mapper;

import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.infrastructure.rest.dto.request.ProductRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper {

    @Mapping(target = "deleted", constant = "false")
    Product toDomain(ProductRequestDto productRequest);

}
