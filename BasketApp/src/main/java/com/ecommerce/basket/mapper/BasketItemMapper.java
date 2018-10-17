package com.ecommerce.basket.mapper;

import com.ecommerce.basket.model.request.BasketItemCreateRequest;
import com.ecommerce.basket.model.response.BasketItemCreateResponse;
import com.google.common.collect.Lists;
import com.ecommerce.core.domain.Product;
import com.ecommerce.core.domain.BasketItem;
import com.ecommerce.core.entity.ProductEntity;
import com.ecommerce.core.entity.BasketItemEntity;

import java.util.List;
import java.util.stream.Collectors;

public class BasketItemMapper {

    public BasketItemEntity mapToEntity(BasketItemCreateRequest basketItemCreateRequest) {

        BasketItemEntity basketItemEntity = new BasketItemEntity();
        basketItemEntity.setProductId(basketItemCreateRequest.getProductId());
        basketItemEntity.setQuantity(basketItemCreateRequest.getQuantity());
        basketItemEntity.setBasketId(basketItemCreateRequest.getBasketId());
        return basketItemEntity;
    }

    public BasketItemCreateResponse mapToCreateResponse(BasketItemEntity basketItemEntity) {

        return BasketItemCreateResponse.builder()
                .basketId(basketItemEntity.getBasketId())
                .basketItemId(basketItemEntity.getId())
                .build();
    }

    public BasketItem mapToDTO(BasketItemEntity basketItemEntity) {
        ProductEntity entityProduct = basketItemEntity.getProduct();
        return BasketItem.builder()
                .id(basketItemEntity.getId())
                .quantity(basketItemEntity.getQuantity())
                .salePrice(basketItemEntity.getSalePrice())
                .discount(basketItemEntity.getDiscount())
                .price(basketItemEntity.getPrice())
                .campaigns(Lists.newArrayList())
                .product(Product.builder()
                        .productId(entityProduct.getId())
                        .title(entityProduct.getTitle())
                        .categoryId(entityProduct.getCategoryId())
                        .price(entityProduct.getPrice())
                        .build())
                .build();
    }

    public List<BasketItem> mapToDTOs(List<BasketItemEntity> basketItemEntities) {

        return basketItemEntities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

}
