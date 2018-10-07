package com.trendyol.ecommerce.core.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Product {

    private Long productId;
    private Long categoryId;
    private String title;
    private BigDecimal price;
}
