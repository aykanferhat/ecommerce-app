package com.ecommerce.product.service;

import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.model.request.ProductCreateRequest;
import com.ecommerce.product.model.response.ProductCreateResponse;
import com.ecommerce.core.domain.Product;
import com.ecommerce.core.entity.ProductEntity;
import com.ecommerce.core.exception.BusinessException;
import com.ecommerce.product.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void successCreateProductTest() {
        //given
        String title = "TestProduct";
        Long categoryId = 1L;
        BigDecimal price = BigDecimal.valueOf(10);

        ProductEntity productEntity = new ProductEntity();

        ProductCreateRequest productCreateRequest = new ProductCreateRequest(title, price, categoryId);

        when(categoryService.existsById(categoryId)).thenReturn(Boolean.TRUE);
        when(productMapper.mapToEntity(productCreateRequest)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productMapper.mapToResponse(productEntity)).thenReturn(any(ProductCreateResponse.class));

        //when
        productService.createProduct(productCreateRequest);

        //then
        verify(productMapper, times(1)).mapToResponse(productEntity);
    }

    @Test(expected = BusinessException.class)
    public void errorProductCreateTest() {
        // Category not found.

        //given
        String title = "TestProduct";
        Long categoryId = 1L;
        BigDecimal price = BigDecimal.valueOf(10);

        ProductCreateRequest productCreateRequest = new ProductCreateRequest(title, price, categoryId);

        //when
        when(categoryService.existsById(categoryId)).thenReturn(Boolean.FALSE);

        //then
        productService.createProduct(productCreateRequest);
    }

    @Test
    public void succuesGetProductById() {

        //given
        Long productId = 1L;
        ProductEntity productEntity = new ProductEntity();

        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        when(productMapper.mapToDTO(productEntity)).thenReturn(any(Product.class));

        //when
        productService.getProductById(productId);

        //then
        verify(productMapper, times(1)).mapToDTO(productEntity);
    }

    @Test(expected = BusinessException.class)
    public void errorGetProductById() {

        //given
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        //when
        productService.getProductById(productId);
    }

    @Test
    public void successExistsById() {

        //given
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(any(Boolean.class));

        //when
        productRepository.existsById(productId);

        //then
        verify(productRepository, times(1)).existsById(productId);
    }

}


