package com.trendyol.ecommerce.shoppingcart.mapper;

import com.trendyol.ecommerce.core.domain.ShoppingCart;
import com.trendyol.ecommerce.core.entity.ShoppingCartEntity;
import com.trendyol.ecommerce.shoppingcart.model.request.ShoppingCartCreateRequest;
import com.trendyol.ecommerce.shoppingcart.model.response.ShoppingCartCreateResponse;

import java.math.BigDecimal;
import java.util.Date;

public class ShoppingCartMapper {

    public ShoppingCartEntity mapToEntity(ShoppingCartCreateRequest shoppingCartCreateRequest) {

        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        shoppingCartEntity.setUserId(shoppingCartCreateRequest.getUserId());
        shoppingCartEntity.setCreationDate(new Date());
        return shoppingCartEntity;
    }

    public ShoppingCartCreateResponse mapToResponse(ShoppingCartEntity shoppingCartEntity) {

        return ShoppingCartCreateResponse.builder()
                .creationDate(shoppingCartEntity.getCreationDate())
                .shoppingCartId(shoppingCartEntity.getId())
                .userId(shoppingCartEntity.getUserId())
                .build();
    }

    public ShoppingCart mapToDTO(ShoppingCartEntity shoppingCartEntity) {

        return ShoppingCart.builder()
                .id(shoppingCartEntity.getId())
                .userId(shoppingCartEntity.getUserId())
                .creationDate(shoppingCartEntity.getCreationDate())
                .discount(BigDecimal.ZERO)
                .totalQuantity(0)
                .build();
    }

}
