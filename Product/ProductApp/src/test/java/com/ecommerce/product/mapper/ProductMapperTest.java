package com.ecommerce.product.mapper;

import com.ecommerce.product.model.request.ProductCreateRequest;
import com.ecommerce.product.model.response.ProductCreateResponse;
import com.ecommerce.core.domain.Product;
import com.ecommerce.core.entity.ProductEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ProductMapperTest {

    private ProductMapper productMapper;

    @Before
    public void setup(){
        productMapper = new ProductMapper();
    }

    @Test
    public void mapToEntityTest() {
        String title = "TestProduct";
        BigDecimal price = BigDecimal.ZERO;
        Long categoryId = 1L;

        ProductCreateRequest productCreateRequest = new ProductCreateRequest(title, price, categoryId);

        ProductEntity productEntity = productMapper.mapToEntity(productCreateRequest);

        assertNotNull(productEntity);
        assertEquals(productCreateRequest.getTitle(), productEntity.getTitle());
        assertEquals(productCreateRequest.getPrice(), productEntity.getPrice());
        assertEquals(productCreateRequest.getCategoryId(), productEntity.getCategoryId());
    }

    @Test
    public void mapToResponseTest() {

        Long productId = 1L;
        Long categoryId = 1L;

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productId);
        productEntity.setCategoryId(categoryId);

        ProductCreateResponse productCreateResponse = productMapper.mapToResponse(productEntity);

        assertNotNull(productCreateResponse);
        assertEquals(productCreateResponse.getCategoryId(), productEntity.getCategoryId());
        assertEquals(productCreateResponse.getProductId(), productEntity.getId());
    }

    @Test
    public void mapToDTOTest() {

        Long id = 1L;
        String title = "TestProduct";
        BigDecimal price = BigDecimal.ZERO;
        Long categoryId = 1L;

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        productEntity.setTitle(title);
        productEntity.setPrice(price);
        productEntity.setCategoryId(categoryId);

        Product product = productMapper.mapToDTO(productEntity);

        assertNotNull(product);
        assertEquals(product.getProductId(), productEntity.getId());
        assertEquals(product.getCategoryId(), productEntity.getCategoryId());
        assertEquals(product.getPrice(), productEntity.getPrice());
        assertEquals(product.getTitle(), productEntity.getTitle());
    }

}
