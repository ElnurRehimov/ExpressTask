package com.expressbank.task.controller;

import com.expressbank.task.model.dto.request.SupplierRequest;
import com.expressbank.task.model.dto.response.SupplierResponse;
import com.expressbank.task.service.SupplierService;
import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupplierController {

    SupplierService supplierService;

    @GetMapping
    public List<SupplierResponse> getSuppliers() {
        return supplierService.getSuppliers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long crateSupplier(@Valid @RequestBody SupplierRequest supplierRequest) {
        return supplierService.crateSupplier(supplierRequest);
    }
}
