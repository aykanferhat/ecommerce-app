package com.ecommerce.shoppingcart.mapper;

import com.ecommerce.shoppingcart.model.request.ShoppingCartItemCreateRequest;
import com.ecommerce.shoppingcart.model.response.ShoppingCartItemCreateResponse;
import com.google.common.collect.Lists;
import com.ecommerce.core.domain.Product;
import com.ecommerce.core.domain.ShoppingCartItem;
import com.ecommerce.core.entity.ProductEntity;
import com.ecommerce.core.entity.ShoppingCartItemEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartItemMapper {

    public ShoppingCartItemEntity mapToEntity(ShoppingCartItemCreateRequest shoppingCartItemCreateRequest) {

        ShoppingCartItemEntity shoppingCartItemEntity = new ShoppingCartItemEntity();
        shoppingCartItemEntity.setProductId(shoppingCartItemCreateRequest.getProductId());
        shoppingCartItemEntity.setQuantity(shoppingCartItemCreateRequest.getQuantity());
        shoppingCartItemEntity.setShoppingCartId(shoppingCartItemCreateRequest.getShoppingCartId());
        return shoppingCartItemEntity;
    }

    public ShoppingCartItemCreateResponse mapToCreateResponse(ShoppingCartItemEntity shoppingCartItemEntity) {

        return ShoppingCartItemCreateResponse.builder()
                .shoppingCartId(shoppingCartItemEntity.getShoppingCartId())
                .shoppingCartItemId(shoppingCartItemEntity.getId())
                .build();
    }

    public ShoppingCartItem mapToDTO(ShoppingCartItemEntity shoppingCartItemEntity) {
        ProductEntity entityProduct = shoppingCartItemEntity.getProduct();
        return ShoppingCartItem.builder()
                .id(shoppingCartItemEntity.getId())
                .quantity(shoppingCartItemEntity.getQuantity())
                .salePrice(shoppingCartItemEntity.getSalePrice())
                .discount(shoppingCartItemEntity.getDiscount())
                .price(shoppingCartItemEntity.getPrice())
                .campaigns(Lists.newArrayList())
                .product(Product.builder()
                        .productId(entityProduct.getId())
                        .title(entityProduct.getTitle())
                        .categoryId(entityProduct.getCategoryId())
                        .price(entityProduct.getPrice())
                        .build())
                .build();
    }

    public List<ShoppingCartItem> mapToDTOs(List<ShoppingCartItemEntity> shoppingCartItemEntities) {

        return shoppingCartItemEntities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

}
