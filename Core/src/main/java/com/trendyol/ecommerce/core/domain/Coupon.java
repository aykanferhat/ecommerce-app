package com.trendyol.ecommerce.core.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Coupon {

    private Long id;
    private BigDecimal threshold;
    private DiscountType discountType;
    private BigDecimal discount;
}
