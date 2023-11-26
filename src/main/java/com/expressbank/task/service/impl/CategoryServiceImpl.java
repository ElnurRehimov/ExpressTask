package com.expressbank.task.service.impl;

import com.expressbank.task.dao.entity.Category;
import com.expressbank.task.dao.repository.CategoryRepository;
import com.expressbank.task.model.dto.request.CategoryRequest;
import com.expressbank.task.model.dto.response.CategoryResponse;
import com.expressbank.task.model.exception.CategoryAlreadyExistException;
import com.expressbank.task.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static com.expressbank.task.model.enums.ApplicationMessages.CATEGORY_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> CategoryResponse.of(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Long creatCategory(CategoryRequest request) {
        categoryRepository.findByName(request.getName())
                .ifPresent(c -> {
                    throw new CategoryAlreadyExistException(CATEGORY_ALREADY_EXIST);
                });

        var category = new Category();
        category.setName(request.getName());
        return categoryRepository.save(category).getId();
    }
}
