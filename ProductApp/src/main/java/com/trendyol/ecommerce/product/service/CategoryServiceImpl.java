package com.trendyol.ecommerce.product.service;

import com.trendyol.ecommerce.core.domain.Category;
import com.trendyol.ecommerce.core.entity.CategoryEntity;
import com.trendyol.ecommerce.core.exception.BusinessException;
import com.trendyol.ecommerce.product.mapper.CategoryMapper;
import com.trendyol.ecommerce.product.model.request.CategoryCreateRequest;
import com.trendyol.ecommerce.product.model.response.CategoryCreateResponse;
import com.trendyol.ecommerce.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.trendyol.ecommerce.core.exception.faultCode.CategoryFaultCode.CATEGORY_NOT_FOUND;


@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryCreateResponse createCategory(CategoryCreateRequest categoryCreateRequest) {
        Long parentCategoryId = categoryCreateRequest.getParentCategoryId();
        if (parentCategoryId != null) {
            boolean existsCategory = existsById(parentCategoryId);
            if (!existsCategory) {
                throw new BusinessException(CATEGORY_NOT_FOUND, parentCategoryId);
            }
        }
        CategoryEntity categoryEntity = categoryRepository.save(categoryMapper.mapToEntity(categoryCreateRequest));
        return categoryMapper.mapToResponse(categoryEntity);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(categoryId);
        if (!optionalCategoryEntity.isPresent()) {
            throw new BusinessException(CATEGORY_NOT_FOUND, categoryId);
        }
        CategoryEntity categoryEntity = optionalCategoryEntity.get();
        return categoryMapper.mapToDTO(categoryEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

}
