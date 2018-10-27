package com.ecommerce.common.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BasketItemDiscountCalculatorModel {

    private Long id;
    private Integer quantity;
    private Product product;
}
