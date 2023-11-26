package com.expressbank.task.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.expressbank.task.dao.entity.Category;
import com.expressbank.task.dao.repository.CategoryRepository;
import com.expressbank.task.model.dto.request.CategoryRequest;
import com.expressbank.task.model.dto.response.CategoryResponse;
import com.expressbank.task.model.exception.CategoryAlreadyExistException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

  @Mock
  CategoryRepository categoryRepository;

  @InjectMocks
  CategoryServiceImpl categoryService;

  @Test
  void getCategories_shouldReturnResponse() {
    Category category1 = new Category(1L, "Category 1", LocalDateTime.now(), LocalDateTime.now());
    Category category2 = new Category(2L, "Category 2", LocalDateTime.now(), LocalDateTime.now());
    List<Category> categories = Arrays.asList(category1, category2);

    when(categoryRepository.findAll()).thenReturn(categories);

    List<CategoryResponse> categoryResponses = categoryService.getCategories();

    assertEquals(2, categoryResponses.size());
    assertEquals("Category 1", categoryResponses.get(0).getName());
    assertEquals("Category 2", categoryResponses.get(1).getName());

    verify(categoryRepository, Mockito.times(1)).findAll();
  }

  @Test
  void createCategory_shouldThrowCategoryAlreadyExistException() {
    CategoryRequest categoryRequest = new CategoryRequest("New Category");
    Category existingCategory = new Category(1L, "New Category", LocalDateTime.now(), LocalDateTime.now());

    when(categoryRepository.findByName("New Category")).thenReturn(Optional.of(existingCategory));

    assertThrows(CategoryAlreadyExistException.class, () ->  categoryService.creatCategory(categoryRequest));

    verify(categoryRepository, Mockito.times(1)).findByName("New Category");

    verify(categoryRepository, Mockito.never()).save(any());
  }
}

