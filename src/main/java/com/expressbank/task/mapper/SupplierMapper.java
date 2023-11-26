package com.expressbank.task.mapper;

import com.expressbank.task.dao.entity.Supplier;
import com.expressbank.task.model.dto.request.SupplierRequest;
import com.expressbank.task.model.dto.response.SupplierResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class SupplierMapper {

    public static final SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    public abstract SupplierResponse toResponse(Supplier supplier);

    public abstract Supplier toEntity(SupplierRequest request);
}
