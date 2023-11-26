package com.expressbank.task.service.impl;

import com.expressbank.task.dao.repository.CategoryRepository;
import com.expressbank.task.dao.repository.ProductRepository;
import com.expressbank.task.dao.repository.SupplierRepository;
import com.expressbank.task.mapper.ProductMapper;
import com.expressbank.task.model.dto.request.ProductRequest;
import com.expressbank.task.model.dto.response.ProductResponse;
import com.expressbank.task.model.exception.CategoryNotFoundException;
import com.expressbank.task.model.exception.ProductNotFoundException;
import com.expressbank.task.model.exception.SupplierNotFoundException;
import com.expressbank.task.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static com.expressbank.task.model.enums.ApplicationMessages.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    SupplierRepository supplierRepository;

    @Override
    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper.INSTANCE::toResponse)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
    }

    @Override
    public Long createProduct(ProductRequest request) {
        var category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));

        var supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new SupplierNotFoundException(SUPPLIER_NOT_FOUND));

        var product = ProductMapper.INSTANCE.toEntity(request);
        product.setCategory(category);
        product.setSupplier(supplier);

        return productRepository.save(product).getId();
    }

    @Override
    public void updateProduct(Long id, ProductRequest request) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        var category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));

        var supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new SupplierNotFoundException(SUPPLIER_NOT_FOUND));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        product.setSupplier(supplier);

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        productRepository.delete(product);
    }
}
