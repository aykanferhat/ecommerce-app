package com.trendyol.ecommerce.product.mapper;

import com.trendyol.ecommerce.core.domain.Category;
import com.trendyol.ecommerce.core.entity.CategoryEntity;
import com.trendyol.ecommerce.product.model.request.CategoryCreateRequest;
import com.trendyol.ecommerce.product.model.response.CategoryCreateResponse;

public class CategoryMapper {

    public CategoryEntity mapToEntity(CategoryCreateRequest categoryCreateRequest) {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle(categoryCreateRequest.getTitle());
        categoryEntity.setParentCategoryId(categoryCreateRequest.getParentCategoryId());
        return categoryEntity;
    }

    public CategoryCreateResponse mapToResponse(CategoryEntity categoryEntity) {

        return CategoryCreateResponse.builder()
                .categoryId(categoryEntity.getId())
                .parentCategoryId(categoryEntity.getParentCategoryId())
                .build();
    }

    public Category mapToDTO(CategoryEntity categoryEntity) {

        return Category.builder()
                .categoryId(categoryEntity.getId())
                .parentCategoryId(categoryEntity.getParentCategoryId())
                .title(categoryEntity.getTitle())
                .build();
    }
}
