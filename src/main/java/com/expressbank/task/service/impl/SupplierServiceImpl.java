package com.expressbank.task.service.impl;

import com.expressbank.task.dao.repository.SupplierRepository;
import com.expressbank.task.mapper.SupplierMapper;
import com.expressbank.task.model.dto.request.SupplierRequest;
import com.expressbank.task.model.dto.response.SupplierResponse;
import com.expressbank.task.model.exception.SupplierAlreadyExistException;
import com.expressbank.task.service.SupplierService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static com.expressbank.task.model.enums.ApplicationMessages.SUPPLIER_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierServiceImpl implements SupplierService {

    SupplierRepository supplierRepository;

    @Override
    public List<SupplierResponse> getSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(SupplierMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Long crateSupplier(SupplierRequest request) {
        supplierRepository.findByNameAndAddress(request.getName(), request.getAddress())
                .ifPresent(supplier -> {
                    throw new SupplierAlreadyExistException(SUPPLIER_ALREADY_EXIST);
                });

        var supplier = SupplierMapper.INSTANCE.toEntity(request);

        return supplierRepository.save(supplier).getId();
    }
}
