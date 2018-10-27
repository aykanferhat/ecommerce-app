package com.ecommerce.product.mapper;

import com.ecommerce.common.domain.Product;
import com.ecommerce.common.domain.ProductCreateRequest;
import com.ecommerce.common.domain.ProductCreateResponse;
import com.ecommerce.core.entity.ProductEntity;

public class ProductMapper {

    public ProductEntity mapToEntity(ProductCreateRequest productCreateRequest) {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setCategoryId(productCreateRequest.getCategoryId());
        productEntity.setPrice(productCreateRequest.getPrice());
        productEntity.setTitle(productCreateRequest.getTitle());
        return productEntity;
    }

    public ProductCreateResponse mapToResponse(ProductEntity productEntity) {

        return ProductCreateResponse.builder()
                .categoryId(productEntity.getCategoryId())
                .productId(productEntity.getId())
                .build();
    }

    public Product mapToDTO(ProductEntity productEntity) {

        return Product.builder()
                .productId(productEntity.getId())
                .categoryId(productEntity.getCategoryId())
                .price(productEntity.getPrice())
                .title(productEntity.getTitle())
                .build();
    }
}
