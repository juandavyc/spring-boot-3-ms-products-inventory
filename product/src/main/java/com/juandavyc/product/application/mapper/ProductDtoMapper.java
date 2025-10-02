package com.juandavyc.product.application.mapper;

import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.domain.model.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    ProductDto toDto(Product domain);

}
