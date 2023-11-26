package com.expressbank.task.mapper;

import com.expressbank.task.dao.entity.Product;
import com.expressbank.task.model.dto.request.ProductRequest;
import com.expressbank.task.model.dto.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ProductMapper {
    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    public abstract Product toEntity(ProductRequest request);

    public abstract ProductResponse toResponse(Product product);
}
