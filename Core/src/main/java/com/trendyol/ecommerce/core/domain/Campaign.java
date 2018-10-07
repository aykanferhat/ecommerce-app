package com.trendyol.ecommerce.core.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Campaign {

    private Long id;
    private Long categoryId;
    private Integer threshold;
    private DiscountType discountType;
    private BigDecimal discount;
}
