package com.expressbank.task.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.expressbank.task.dao.entity.Category;
import com.expressbank.task.dao.entity.Product;
import com.expressbank.task.dao.entity.Supplier;
import com.expressbank.task.dao.repository.CategoryRepository;
import com.expressbank.task.dao.repository.ProductRepository;
import com.expressbank.task.dao.repository.SupplierRepository;
import com.expressbank.task.model.dto.request.ProductRequest;
import com.expressbank.task.model.dto.response.ProductResponse;
import com.expressbank.task.model.exception.CategoryNotFoundException;
import com.expressbank.task.model.exception.ProductNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getProducts_shouldReturnResponse() {
        Product product = new Product();
        product.setId(1L);
        product.setName("PRODUCT");


        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponse> products = productService.getProducts();

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(products.get(0).getName(), product.getName());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_shouldThrowProductNotFoundException() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void createProduct_shouldThrowCategoryNotFoundException() {
        ProductRequest request = new ProductRequest();
        request.setCategoryId(1L);
        request.setSupplierId(1L);
        when(categoryRepository.findById(request.getCategoryId())).thenReturn(Optional.empty());
        when(supplierRepository.findById(request.getSupplierId())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.createProduct(request));

        verify(categoryRepository, times(1)).findById(request.getCategoryId());

        reset(categoryRepository, supplierRepository);
    }

    @Test
    void updateProduct_shouldThrowCategoryNotFoundException() {
        Long productId = 1L;
        ProductRequest request = new ProductRequest();
        request.setCategoryId(1L);
        request.setSupplierId(1L);

        Product existingProduct = new Product();
        existingProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findById(request.getCategoryId())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.updateProduct(productId, request));

        verify(productRepository, times(1)).findById(productId);
        verify(categoryRepository, times(1)).findById(request.getCategoryId());

        reset(productRepository, categoryRepository, supplierRepository);
    }

    @Test
    void deleteProduct_shouldThrowProductNotFoundException() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void createProduct_shouldBeSuccessFull() {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(BigDecimal.valueOf(100.00));
        request.setDescription("Description");
        request.setCategoryId(1L);
        request.setSupplierId(1L);

        Category category = new Category();
        category.setId(1L);
        when(categoryRepository.findById(request.getCategoryId())).thenReturn(Optional.of(category));

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        when(supplierRepository.findById(request.getSupplierId())).thenReturn(Optional.of(supplier));

        Product createdProduct = new Product();
        createdProduct.setId(1L);
        when(productRepository.save(any())).thenReturn(createdProduct);

        Long productId = productService.createProduct(request);

        assertNotNull(productId);
        assertEquals(createdProduct.getId(), productId);

        verify(productRepository, times(1)).save(any());
    }

    @Test
    void updateProduct_shouldBeSuccessFull() {
        Long productId = 1L;
        ProductRequest request = new ProductRequest();
        request.setName("Updated Product");
        request.setPrice(BigDecimal.valueOf(150.00));
        request.setDescription("Updated Description");
        request.setCategoryId(2L);
        request.setSupplierId(2L);

        Product existingProduct = new Product();
        existingProduct.setId(productId);

        Category newCategory = new Category();
        newCategory.setId(2L);
        when(categoryRepository.findById(request.getCategoryId())).thenReturn(Optional.of(newCategory));

        Supplier newSupplier = new Supplier();
        newSupplier.setId(2L);
        when(supplierRepository.findById(request.getSupplierId())).thenReturn(Optional.of(newSupplier));

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        productService.updateProduct(productId, request);

        assertEquals(request.getName(), existingProduct.getName());
        assertEquals(request.getPrice(), existingProduct.getPrice());
        assertEquals(request.getDescription(), existingProduct.getDescription());
        assertEquals(newCategory, existingProduct.getCategory());
        assertEquals(newSupplier, existingProduct.getSupplier());

        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void deleteProduct_shouldBeSuccessFull() {
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).delete(existingProduct);
    }
}
