package com.expressbank.task.service;

import com.expressbank.task.model.dto.request.ProductRequest;
import com.expressbank.task.model.dto.response.ProductResponse;
import java.util.List;

public interface ProductService {

    List<ProductResponse> getProducts();

    ProductResponse getProductById(Long id);

    Long createProduct(ProductRequest request);

    void updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

}
