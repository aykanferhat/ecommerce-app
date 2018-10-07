package com.trendyol.ecommerce.product.mapper;


import com.trendyol.ecommerce.core.domain.Category;
import com.trendyol.ecommerce.core.entity.CategoryEntity;
import com.trendyol.ecommerce.product.model.request.CategoryCreateRequest;
import com.trendyol.ecommerce.product.model.response.CategoryCreateResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
public class CategoryMapperTest {

    private CategoryMapper categoryMapper;

    @Before
    public void setup(){
        categoryMapper = new CategoryMapper();
    }

    @Test
    public void mapToEntityTest() {
        String title = "TestCategory";
        Long parentCategoryId = 1L;

        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest(title, parentCategoryId);

        CategoryEntity categoryEntity = categoryMapper.mapToEntity(categoryCreateRequest);

        assertNotNull(categoryEntity);
        assertEquals(categoryEntity.getTitle(), categoryCreateRequest.getTitle());
        assertEquals(categoryEntity.getParentCategoryId(), categoryCreateRequest.getParentCategoryId());
    }

    @Test
    public void mapToResponseTest() {

        Long categoryId = 1L;
        Long parentCategoryId = 1L;

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryId);
        categoryEntity.setParentCategoryId(parentCategoryId);

        CategoryCreateResponse categoryCreateResponse = categoryMapper.mapToResponse(categoryEntity);

        assertNotNull(categoryCreateResponse);
        assertEquals(categoryEntity.getId(), categoryCreateResponse.getCategoryId());
        assertEquals(categoryEntity.getParentCategoryId(), categoryCreateResponse.getParentCategoryId());
    }

    @Test
    public void mapToDTOTest() {

        Long id = 1L;
        String title = "TestCategory";
        Long parentCategoryId = 1L;

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(id);
        categoryEntity.setTitle(title);
        categoryEntity.setParentCategoryId(parentCategoryId);

        Category category = categoryMapper.mapToDTO(categoryEntity);

        assertNotNull(category);
        assertEquals(categoryEntity.getId(), category.getCategoryId());
        assertEquals(categoryEntity.getParentCategoryId(), category.getParentCategoryId());
        assertEquals(categoryEntity.getTitle(), category.getTitle());

    }

}
