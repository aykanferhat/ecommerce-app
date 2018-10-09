package com.trendyol.ecommerce.core.util;

import com.trendyol.ecommerce.core.domain.Coupon;
import com.trendyol.ecommerce.core.domain.DiscountType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CouponDiscountCalculatorImpl implements CouponDiscountCalculator {

    @Override
    public BigDecimal calculateCouponDiscount(Coupon coupon, BigDecimal price) {
        BigDecimal discountAmount = BigDecimal.ZERO;
        DiscountType discountType = coupon.getDiscountType();
        if (discountType.isRate()) {
            discountAmount = price.multiply(coupon.getDiscount()).divide(BigDecimal.valueOf(100));
        } else if (discountType.isAmount()) {
            discountAmount = coupon.getDiscount();
        }
        return discountAmount;
    }
}
