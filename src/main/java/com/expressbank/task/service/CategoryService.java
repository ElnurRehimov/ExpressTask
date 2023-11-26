package com.expressbank.task.service;

import com.expressbank.task.model.dto.request.CategoryRequest;
import com.expressbank.task.model.dto.response.CategoryResponse;
import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getCategories();

    Long creatCategory(CategoryRequest request);

}
