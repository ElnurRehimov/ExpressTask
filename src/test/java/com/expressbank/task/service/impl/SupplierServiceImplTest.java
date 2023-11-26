package com.expressbank.task.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.expressbank.task.dao.entity.Supplier;
import com.expressbank.task.dao.repository.SupplierRepository;
import com.expressbank.task.model.dto.request.SupplierRequest;
import com.expressbank.task.model.dto.response.SupplierResponse;
import com.expressbank.task.model.exception.SupplierAlreadyExistException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceImplTest {

  @Mock
  private SupplierRepository supplierRepository;

  @InjectMocks
  private SupplierServiceImpl supplierService;

  @Test
  void getSuppliers_shouldReturnSupplierResponses() {

    Supplier supplier1 = new Supplier(1L, "Supplier 1", "",  LocalDateTime.now(), LocalDateTime.now());
    Supplier supplier2 = new Supplier(2L, "Supplier 2", "",  LocalDateTime.now(), LocalDateTime.now());

    when(supplierRepository.findAll()).thenReturn(Arrays.asList(supplier1, supplier2));

    List<SupplierResponse> supplierResponses = supplierService.getSuppliers();

    assertEquals(2, supplierResponses.size());
    assertEquals("Supplier 1", supplierResponses.get(0).getName());
    assertEquals("Supplier 2", supplierResponses.get(1).getName());
  }

  @Test
  void createSupplier_shouldCreateSupplierAndReturnId() {
    when(supplierRepository.findByNameAndAddress(anyString(), anyString())).thenReturn(java.util.Optional.empty());
    when(supplierRepository.save(any(Supplier.class))).thenAnswer(invocation -> {
      Supplier supplier = invocation.getArgument(0);
      supplier.setId(1L);
      return supplier;
    });

    SupplierRequest request = new SupplierRequest();
    request.setName("Test Supplier");
    request.setAddress("Test Address");

    Long supplierId = supplierService.crateSupplier(request);

    assertNotNull(supplierId);
    assertNotEquals(0L, supplierId);
  }

  @Test
  void createSupplier_shouldThrowExceptionIfSupplierExists() {
    when(supplierRepository.findByNameAndAddress(anyString(), anyString()))
        .thenReturn(java.util.Optional.of(new Supplier()));

    SupplierRequest request = new SupplierRequest();
    request.setName("Existing Supplier");
    request.setAddress("Existing Address");

    SupplierAlreadyExistException exception = assertThrows(SupplierAlreadyExistException.class, () -> {
      supplierService.crateSupplier(request);
    });

    assertEquals("Supplier is already exist", exception.getMessage());
  }
}
