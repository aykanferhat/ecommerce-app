package com.ecommerce.core.util;

import com.ecommerce.core.domain.Coupon;

import java.math.BigDecimal;

public interface CouponDiscountCalculator {

    BigDecimal calculateCouponDiscount(Coupon coupon, BigDecimal price);
}
