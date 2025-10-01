package com.juandavyc.product.infrastructure.adapter.mapper;

import com.juandavyc.product.domain.model.Product;
import com.juandavyc.product.domain.model.dto.request.ProductRequest;
import com.juandavyc.product.infrastructure.adapter.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductUpdateMapper {
    @Mapping(target = "id", ignore = true)
    void updateEntity(Product request, @MappingTarget ProductEntity entity);

}
