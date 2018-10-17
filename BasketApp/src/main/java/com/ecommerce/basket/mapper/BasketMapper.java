package com.ecommerce.basket.mapper;

import com.ecommerce.basket.model.request.BasketCreateRequest;
import com.ecommerce.basket.model.response.BasketCreateResponse;
import com.ecommerce.core.domain.Basket;
import com.ecommerce.core.entity.BasketEntity;

import java.math.BigDecimal;
import java.util.Date;

public class BasketMapper {

    public BasketEntity mapToEntity(BasketCreateRequest basketCreateRequest) {

        BasketEntity basketEntity = new BasketEntity();
        basketEntity.setUserId(basketCreateRequest.getUserId());
        basketEntity.setCreationDate(new Date());
        return basketEntity;
    }

    public BasketCreateResponse mapToResponse(BasketEntity basketEntity) {

        return BasketCreateResponse.builder()
                .creationDate(basketEntity.getCreationDate())
                .basketId(basketEntity.getId())
                .userId(basketEntity.getUserId())
                .build();
    }

    public Basket mapToDTO(BasketEntity basketEntity) {

        return Basket.builder()
                .id(basketEntity.getId())
                .userId(basketEntity.getUserId())
                .creationDate(basketEntity.getCreationDate())
                .discount(BigDecimal.ZERO)
                .totalQuantity(0)
                .build();
    }

}
