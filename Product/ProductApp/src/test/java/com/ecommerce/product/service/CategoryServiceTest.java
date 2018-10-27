package com.ecommerce.product.service;

import com.ecommerce.core.domain.Category;
import com.ecommerce.core.entity.CategoryEntity;
import com.ecommerce.core.exception.BusinessException;
import com.ecommerce.product.mapper.CategoryMapper;
import com.ecommerce.product.model.request.CategoryCreateRequest;
import com.ecommerce.product.model.response.CategoryCreateResponse;
import com.ecommerce.product.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void successCreateCategoryTest() {

        //given
        String title = "TestCategory";
        Long parentCategory = null;

        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest(title, parentCategory);

        CategoryEntity categoryEntity = new CategoryEntity();

        when(categoryMapper.mapToEntity(categoryCreateRequest)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryMapper.mapToResponse(categoryEntity)).thenReturn(any(CategoryCreateResponse.class));

        //when
        categoryService.createCategory(categoryCreateRequest);

        //then
        verify(categoryMapper, times(1)).mapToResponse(categoryEntity);
    }

    @Test
    public void successCreateCategoryWithParentCategoryTest() {

        //given
        String title = "TestCategory";
        Long parentCategory = 1L;

        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest(title, parentCategory);

        CategoryEntity categoryEntity = new CategoryEntity();

        when(categoryRepository.existsById(parentCategory)).thenReturn(Boolean.TRUE);
        when(categoryMapper.mapToEntity(categoryCreateRequest)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryMapper.mapToResponse(categoryEntity)).thenReturn(any(CategoryCreateResponse.class));

        //when
        categoryService.createCategory(categoryCreateRequest);

        //then
        verify(categoryMapper, times(1)).mapToResponse(categoryEntity);
    }


    @Test(expected = BusinessException.class)
    public void errorCategoryCreateTest() {
        //Category not found.

        //given
        String title = "TestCategory";
        Long parentCategory = 1L;

        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest(title, parentCategory);

        when(categoryRepository.existsById(parentCategory)).thenReturn(Boolean.FALSE);

        //when
        categoryService.createCategory(categoryCreateRequest);
    }


    @Test
    public void successGetCategoryById() {

        //given
        Long categoryId = 1L;

        CategoryEntity categoryEntity = new CategoryEntity();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        when(categoryMapper.mapToDTO(categoryEntity)).thenReturn(any(Category.class));

        //when
        categoryService.getCategoryById(categoryId);

        //then
        verify(categoryMapper, times(1)).mapToDTO(categoryEntity);
    }

    @Test(expected = BusinessException.class)
    public void errorGetCategoryById() {

        //given
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        //when
        categoryService.getCategoryById(categoryId);
    }

}
