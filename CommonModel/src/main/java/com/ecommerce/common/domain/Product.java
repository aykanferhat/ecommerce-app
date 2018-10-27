package com.ecommerce.common.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Product {

    private Long productId;
    private String title;
    private BigDecimal price;
    private Long categoryId;
}
