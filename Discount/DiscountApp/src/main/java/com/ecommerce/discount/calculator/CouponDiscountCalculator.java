package com.ecommerce.discount.calculator;

import com.ecommerce.common.domain.Coupon;

import java.math.BigDecimal;

public interface CouponDiscountCalculator {

    BigDecimal calculateCouponDiscount(Coupon coupon, BigDecimal price);
}
