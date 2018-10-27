package com.ecommerce.product.service;

import com.ecommerce.common.domain.Category;
import com.ecommerce.common.domain.CategoryCreateRequest;
import com.ecommerce.common.domain.CategoryCreateResponse;

public interface CategoryService {

    Category getCategoryById(Long categoryId);

    CategoryCreateResponse createCategory(CategoryCreateRequest categoryCreateRequest);

    boolean existsById(Long id);
}
