package com.ecommerce.product.service;


import com.ecommerce.core.domain.Category;
import com.ecommerce.product.model.request.CategoryCreateRequest;
import com.ecommerce.product.model.response.CategoryCreateResponse;

public interface CategoryService {

    Category getCategoryById(Long categoryId);

    CategoryCreateResponse createCategory(CategoryCreateRequest categoryCreateRequest);

    boolean existsById(Long id);
}
