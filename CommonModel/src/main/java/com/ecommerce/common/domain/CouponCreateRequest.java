package com.ecommerce.common.domain;

import com.ecommerce.core.domain.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponCreateRequest {

    private BigDecimal threshold;
    private DiscountType discountType;
    private BigDecimal discount;
}
