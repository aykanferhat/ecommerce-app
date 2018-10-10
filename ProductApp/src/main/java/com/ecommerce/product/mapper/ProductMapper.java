package com.ecommerce.product.mapper;

import com.ecommerce.core.domain.Product;
import com.ecommerce.core.entity.ProductEntity;
import com.ecommerce.product.model.request.ProductCreateRequest;
import com.ecommerce.product.model.response.ProductCreateResponse;

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
