package com.expressbank.task.service;

import com.expressbank.task.model.dto.request.SupplierRequest;
import com.expressbank.task.model.dto.response.SupplierResponse;
import java.util.List;

public interface SupplierService {

    List<SupplierResponse> getSuppliers();

    Long crateSupplier(SupplierRequest request);

}
