package com.ecommerce.product.mapper;

import com.ecommerce.common.domain.Category;
import com.ecommerce.common.domain.CategoryCreateRequest;
import com.ecommerce.common.domain.CategoryCreateResponse;
import com.ecommerce.core.entity.CategoryEntity;

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
