package com.trendyol.ecommerce.product.service;


import com.trendyol.ecommerce.core.domain.Category;
import com.trendyol.ecommerce.product.model.request.CategoryCreateRequest;
import com.trendyol.ecommerce.product.model.response.CategoryCreateResponse;

public interface CategoryService {

    Category getCategoryById(Long categoryId);

    CategoryCreateResponse createCategory(CategoryCreateRequest categoryCreateRequest);

    boolean existsById(Long id);
}
