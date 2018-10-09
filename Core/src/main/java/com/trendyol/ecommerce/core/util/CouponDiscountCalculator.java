package com.trendyol.ecommerce.core.util;

import com.trendyol.ecommerce.core.domain.Coupon;

import java.math.BigDecimal;

public interface CouponDiscountCalculator {

    BigDecimal calculateCouponDiscount(Coupon coupon, BigDecimal price);
}
